package com.unprg.unprgcertificategeneratorbackend.services;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificateGenerationService {

    private final TbEventoFormatoCertificadoService tbEventoFormatoCertificadoService;
    private final TbFormatoCertificadoService tbFormatoCertificadoService;
    private final TbEventoFormatoCertificadoFirmaService tbEventoFormatoCertificadoFirmaService;

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    @Value("${app.temp.dir:./temp}")
    private String tempDir;

    public byte[] generateCertificateWord(TbParticipanteDto participante) throws Exception {
        // 1. Obtener el formato asociado al evento
        List<TbEventoFormatoCertificadoDto> eventoFormatos = tbEventoFormatoCertificadoService.findAll();
        TbEventoFormatoCertificadoDto eventoFormato = eventoFormatos.stream()
                .filter(ef -> ef.getTbEvento() != null && ef.getTbEvento().getId().equals(participante.getTbEvento().getId()))
                .findFirst()
                .orElse(null);

        if (eventoFormato == null || eventoFormato.getIdtbFormatoCertificado() == null) {
            throw new RuntimeException("No hay formato de certificado asignado al evento");
        }

        TbFormatoCertificadoDto formato = tbFormatoCertificadoService.findById(eventoFormato.getIdtbFormatoCertificado().getId());

        if (formato == null || formato.getRutaFormato() == null) {
            throw new RuntimeException("No se encontró el archivo de formato");
        }

        // 2. Leer el archivo Word template
        String rutaCompleta = formato.getRutaFormato();
        Path templatePath;

        if (rutaCompleta.startsWith("uploads/") || rutaCompleta.startsWith("uploads\\")) {
            templatePath = Paths.get(rutaCompleta);
        } else {
            templatePath = Paths.get(uploadDir, rutaCompleta);
        }

        log.info("Buscando archivo de formato en: {}", templatePath.toAbsolutePath());

        if (!Files.exists(templatePath)) {
            throw new RuntimeException("El archivo de formato no existe: " + templatePath.toAbsolutePath());
        }

        // 3. Crear datos para reemplazar los marcadores
        Map<String, Object> data = createCertificateData(participante);

        // 4. Procesar el template y devolver el Word procesado
        return processWordTemplateOnly(templatePath, data);
    }

    /**
     * Genera un certificado en formato PDF usando LibreOffice
     * @param participante Datos del participante
     * @return Array de bytes del PDF generado
     * @throws Exception Si ocurre algún error en el proceso
     */
    public byte[] generateCertificatePdf(TbParticipanteDto participante) throws Exception {
        String tempFileName = "certificate_pdf_" + UUID.randomUUID().toString();
        Path tempDocxPath = Paths.get(tempDir, tempFileName + ".docx");
        Path tempPdfPath = Paths.get(tempDir, tempFileName + ".pdf");

        try {
            // Crear directorio temporal si no existe
            Files.createDirectories(Paths.get(tempDir));

            // 1. Primero generar el Word (reutilizamos el método existente)
            byte[] wordBytes = generateCertificateWord(participante);

            // 2. Guardar temporalmente el Word
            Files.write(tempDocxPath, wordBytes);
            log.info("Word temporal guardado en: {}", tempDocxPath);

            // 3. Convertir Word a PDF usando LibreOffice
            convertWithLibreOffice(tempDocxPath, tempPdfPath);

            // 4. Leer el PDF generado y devolverlo como byte array
            byte[] pdfBytes = Files.readAllBytes(tempPdfPath);
            log.info("PDF generado exitosamente: {} bytes", pdfBytes.length);

            return pdfBytes;

        } finally {
            // Limpiar archivos temporales
            try {
                Files.deleteIfExists(tempDocxPath);
                Files.deleteIfExists(tempPdfPath);
                log.debug("Archivos temporales eliminados");
            } catch (Exception e) {
                log.warn("No se pudieron eliminar archivos temporales", e);
            }
        }
    }

    /**
     * Convierte un documento Word a PDF usando LibreOffice
     * LibreOffice debe estar instalado en el sistema
     */
    private void convertWithLibreOffice(Path docxPath, Path pdfPath) throws Exception {
        log.info("Convirtiendo a PDF con LibreOffice...");

        // Comandos comunes de LibreOffice según el SO
        String[] possibleCommands = {
                "soffice",           // Linux
                "libreoffice",       // Linux alternativo
                "/usr/bin/soffice",  // Linux ruta completa
                "C:\\Program Files\\LibreOffice\\program\\soffice.exe",  // Windows
                "/Applications/LibreOffice.app/Contents/MacOS/soffice"  // macOS
        };

        String command = null;
        for (String cmd : possibleCommands) {
            if (isCommandAvailable(cmd)) {
                command = cmd;
                log.info("LibreOffice encontrado en: {}", cmd);
                break;
            }
        }

        if (command == null) {
            throw new RuntimeException(
                    "LibreOffice no está instalado o no se encuentra en el PATH.\n" +
                            "Por favor instala LibreOffice desde: https://www.libreoffice.org/download/\n" +
                            "O verifica que esté en el PATH del sistema."
            );
        }

        try {
            // Obtener el directorio de salida
            String outputDir = pdfPath.getParent().toString();

            // Construir el comando
            ProcessBuilder processBuilder = new ProcessBuilder(
                    command,
                    "--headless",           // Ejecutar sin interfaz gráfica
                    "--convert-to", "pdf",  // Convertir a PDF
                    "--outdir", outputDir,  // Directorio de salida
                    docxPath.toString()     // Archivo de entrada
            );

            // Redirigir errores a la salida estándar
            processBuilder.redirectErrorStream(true);

            log.info("Ejecutando: {} --headless --convert-to pdf --outdir {} {}",
                    command, outputDir, docxPath.getFileName());

            Process process = processBuilder.start();

            // Leer la salida del proceso
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                    log.debug("LibreOffice output: {}", line);
                }
            }

            // Esperar a que termine el proceso
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                String errorMsg = String.format(
                        "LibreOffice terminó con código de error: %d\nOutput: %s",
                        exitCode, output.toString()
                );
                log.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }

            // LibreOffice genera el PDF con el mismo nombre base que el DOCX
            Path libreOfficePdf = Paths.get(outputDir,
                    docxPath.getFileName().toString().replace(".docx", ".pdf"));

            // Verificar que se creó el PDF
            if (!Files.exists(libreOfficePdf)) {
                throw new RuntimeException(
                        "LibreOffice no generó el PDF esperado en: " + libreOfficePdf
                );
            }

            // Si el nombre no coincide con el esperado, renombrar
            if (!libreOfficePdf.equals(pdfPath)) {
                Files.move(libreOfficePdf, pdfPath);
            }

            log.info("✓ Conversión con LibreOffice completada exitosamente");
            log.info("  Archivo PDF: {} ({} KB)",
                    pdfPath.getFileName(),
                    Files.size(pdfPath) / 1024);

        } catch (IOException | InterruptedException e) {
            log.error("Error en conversión con LibreOffice", e);
            throw new RuntimeException("Error al convertir con LibreOffice: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica si un comando de LibreOffice está disponible en el sistema
     */
    private boolean isCommandAvailable(String command) {
        try {
            // Verificar si es una ruta completa que existe
            File file = new File(command);
            if (file.exists() && file.canExecute()) {
                return true;
            }

            // Si no es ruta completa, intentar ejecutar con --version
            ProcessBuilder pb = new ProcessBuilder(command, "--version");
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Consumir la salida para evitar que se bloquee
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                while (reader.readLine() != null) {
                    // Consumir líneas
                }
            }

            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (Exception e) {
            return false;
        }
    }

    private byte[] processWordTemplateOnly(Path templatePath, Map<String, Object> data) throws Exception {
        String tempFileName = "certificate_" + UUID.randomUUID().toString();
        Path tempDocxPath = Paths.get(tempDir, tempFileName + ".docx");

        try {
            // Crear directorio temporal si no existe
            Files.createDirectories(Paths.get(tempDir));

            // Procesar el template Word con los datos
            processWordTemplate(templatePath, tempDocxPath, data);

            // Leer el archivo Word procesado y devolverlo
            return Files.readAllBytes(tempDocxPath);

        } finally {
            // Limpiar archivo temporal
            try {
                Files.deleteIfExists(tempDocxPath);
            } catch (Exception e) {
                log.warn("No se pudo eliminar archivo temporal: {}", tempDocxPath, e);
            }
        }
    }

    private Map<String, Object> createCertificateData(TbParticipanteDto participante) {
        Map<String, Object> data = new HashMap<>();

        // Datos del participante
        String nombresCompletos = String.format("%s %s %s",
                participante.getTbPersona().getNombres(),
                participante.getTbPersona().getApellidoPaterno(),
                participante.getTbPersona().getApellidoMaterno()).trim();

        data.put("sc_nombres", nombresCompletos);
        data.put("participante_nombres", participante.getTbPersona().getNombres());
        data.put("participante_apellido_paterno", participante.getTbPersona().getApellidoPaterno());
        data.put("participante_apellido_materno", participante.getTbPersona().getApellidoMaterno());
        data.put("participante_dni", participante.getTbPersona().getDni());
        data.put("participante_email", participante.getTbPersona().getEmail());
        data.put("participante_telefono", participante.getTbPersona().getTelefono());
        data.put("participante_nota", participante.getNota() != null ? participante.getNota().toString() : "");

        // Datos del evento
        data.put("evento_codigo", participante.getTbEvento().getCodigo());
        data.put("evento_nombre", participante.getTbEvento().getNombre());
        data.put("evento_fecha_inicio", formatDate(participante.getTbEvento().getFechaInicio()));
        data.put("evento_fecha_fin", formatDate(participante.getTbEvento().getFechaFin()));

        // Datos adicionales
        data.put("fecha_actual", formatDate(LocalDate.now()));
        data.put("fecha_inscripcion", participante.getFechaInscripcion());

        // Obtener firmas relacionadas al evento
        List<TbEventoFormatoCertificadoFirmaDto> firmasEvento =
                tbEventoFormatoCertificadoFirmaService.findAllByIdTbEventoCertificado(participante.getTbEvento().getId());

        List<TbFirmaDto> firmasDto = firmasEvento.stream()
                .map(TbEventoFormatoCertificadoFirmaDto::getIdtbFirma)
                .toList();

        firmasDto.forEach(firmaDto -> {
            String codigoLower = firmaDto.getCodigo().toLowerCase();

            // Guardar el nombre de la firma
            data.put(codigoLower + ".nombre", firmaDto.getNombre());
            data.put(codigoLower + ".cargo", firmaDto.getCargo());
            data.put(codigoLower + ".entidad", firmaDto.getEntidad());

            // Guardar la imagen de la firma (byte array)
            if (firmaDto.getImagen() != null && firmaDto.getImagen().length > 0) {
                data.put(codigoLower + ".imagen", firmaDto.getImagen());
                log.info("Imagen de firma agregada: {} ({} bytes)",
                        firmaDto.getCodigo(), firmaDto.getImagen().length);
            } else {
                log.warn("Firma {} no tiene imagen", firmaDto.getCodigo());
            }
        });

        log.info("Datos del certificado creados: {}", data);
        return data;
    }

    private void processWordTemplate(Path templatePath, Path outputPath, Map<String, Object> data) throws IOException {
        try (FileInputStream fis = new FileInputStream(templatePath.toFile());
             XWPFDocument document = new XWPFDocument(fis)) {

            log.info("=== ANÁLISIS COMPLETO DEL DOCUMENTO ===");
            log.info("Párrafos en body: {}", document.getParagraphs().size());
            log.info("Tablas en body: {}", document.getTables().size());
            log.info("Headers: {}", document.getHeaderList().size());
            log.info("Footers: {}", document.getFooterList().size());

            // Procesar párrafos del body
            for (int p = 0; p < document.getParagraphs().size(); p++) {
                XWPFParagraph paragraph = document.getParagraphs().get(p);
                log.info("Body párrafo {}: '{}'", p, paragraph.getText());
                replaceInParagraph(paragraph, data);
            }

            // Procesar tablas del body
            for (int t = 0; t < document.getTables().size(); t++) {
                XWPFTable table = document.getTables().get(t);
                log.info("--- Body Tabla {} ---", t);
                for (int r = 0; r < table.getRows().size(); r++) {
                    XWPFTableRow row = table.getRows().get(r);
                    for (int c = 0; c < row.getTableCells().size(); c++) {
                        XWPFTableCell cell = row.getTableCells().get(c);
                        log.info("  Tabla {} Celda[{},{}] párrafos: {}", t, r, c, cell.getParagraphs().size());
                        for (int p = 0; p < cell.getParagraphs().size(); p++) {
                            XWPFParagraph paragraph = cell.getParagraphs().get(p);
                            log.info("    Celda[{},{}] párrafo {}: '{}'", r, c, p, paragraph.getText());
                            replaceInParagraph(paragraph, data);
                        }
                    }
                }
            }

            // Procesar headers
            for (int h = 0; h < document.getHeaderList().size(); h++) {
                XWPFHeader header = document.getHeaderList().get(h);
                log.info("--- Header {} ---", h);
                for (int p = 0; p < header.getParagraphs().size(); p++) {
                    XWPFParagraph paragraph = header.getParagraphs().get(p);
                    log.info("  Header párrafo {}: '{}'", p, paragraph.getText());
                    replaceInParagraph(paragraph, data);
                }
                // Procesar tablas en headers
                for (XWPFTable table : header.getTables()) {
                    for (XWPFTableRow row : table.getRows()) {
                        for (XWPFTableCell cell : row.getTableCells()) {
                            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                                replaceInParagraph(paragraph, data);
                            }
                        }
                    }
                }
            }

            // Procesar footers
            for (int f = 0; f < document.getFooterList().size(); f++) {
                XWPFFooter footer = document.getFooterList().get(f);
                log.info("--- Footer {} ---", f);
                for (int p = 0; p < footer.getParagraphs().size(); p++) {
                    XWPFParagraph paragraph = footer.getParagraphs().get(p);
                    log.info("  Footer párrafo {}: '{}'", p, paragraph.getText());
                    replaceInParagraph(paragraph, data);
                }
                // Procesar tablas en footers
                for (XWPFTable table : footer.getTables()) {
                    for (XWPFTableRow row : table.getRows()) {
                        for (XWPFTableCell cell : row.getTableCells()) {
                            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                                replaceInParagraph(paragraph, data);
                            }
                        }
                    }
                }
            }

            // Guardar documento procesado
            try (FileOutputStream fos = new FileOutputStream(outputPath.toFile())) {
                document.write(fos);
                log.info("Documento Word procesado guardado en: {}", outputPath);
            }
        }
    }

    private void replaceInParagraph(XWPFParagraph paragraph, Map<String, Object> data) {
        String paragraphText = paragraph.getText();

        log.debug("=== PROCESANDO PÁRRAFO ===");
        log.debug("Texto del párrafo: '{}'", paragraphText);
        log.debug("Número de runs: {}", paragraph.getRuns().size());

        // Mostrar el contenido de cada run
        for (int i = 0; i < paragraph.getRuns().size(); i++) {
            XWPFRun run = paragraph.getRuns().get(i);
            String runText = run.getText(0);
            log.debug("Run {}: '{}'", i, runText);
        }

        if (paragraphText != null && !paragraphText.trim().isEmpty()) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                String placeholder = entry.getKey();
                Object value = entry.getValue();

                log.debug("Procesando entrada: '{}' = '{}'", placeholder, value);

                // Verificar si es una imagen (placeholder termina en .imagen)
                if (placeholder.endsWith(".imagen") && value instanceof byte[]) {
                    String imagePlaceholder = placeholder;

                    log.debug("Es una imagen. Buscando placeholder: '{}'", imagePlaceholder);

                    if (paragraphText.contains(imagePlaceholder)) {
                        log.info("¡ENCONTRADO PLACEHOLDER DE IMAGEN! Insertando imagen para '{}'", imagePlaceholder);
                        replaceWithImage(paragraph, imagePlaceholder, (byte[]) value);
                    } else if (paragraphText.contains("{" + imagePlaceholder + "}")) {
                        log.info("¡ENCONTRADO PLACEHOLDER DE IMAGEN CON LLAVES! Insertando imagen para '{}'", "{" + imagePlaceholder + "}");
                        replaceWithImage(paragraph, "{" + imagePlaceholder + "}", (byte[]) value);
                    }
                }
                // Manejo de texto normal
                else if (value instanceof String) {
                    String stringValue = (String) value;

                    if (paragraphText.contains(placeholder)) {
                        log.info("¡ENCONTRADO! Reemplazando '{}' con '{}'", placeholder, stringValue);
                        replaceTextInParagraph(paragraph, placeholder, stringValue);
                    } else if (paragraphText.contains("{" + placeholder + "}")) {
                        log.info("¡ENCONTRADO CON LLAVES! Reemplazando '{}' con '{}'", "{" + placeholder + "}", stringValue);
                        replaceTextInParagraph(paragraph, "{" + placeholder + "}", stringValue);
                    } else {
                        log.debug("No se encontró '{}' en el párrafo", placeholder);
                    }
                }
            }

            // Verificar el resultado después del reemplazo
            String newText = paragraph.getText();
            log.debug("Texto después del reemplazo: '{}'", newText);
        } else {
            log.warn("Párrafo vacío o nulo: '{}'", paragraphText);
        }
    }

    private void replaceTextInParagraph(XWPFParagraph paragraph, String placeholder, String replacement) {
        List<XWPFRun> runs = paragraph.getRuns();

        for (int i = 0; i < runs.size(); i++) {
            XWPFRun run = runs.get(i);
            String runText = run.getText(0);

            if (runText != null && runText.contains(placeholder)) {
                runText = runText.replace(placeholder, replacement);
                run.setText(runText, 0);
                log.debug("Texto reemplazado en run {}: '{}'", i, runText);
            }
        }
    }

    private void replaceWithImage(XWPFParagraph paragraph, String placeholder, byte[] imageData) {
        try {
            List<XWPFRun> runs = paragraph.getRuns();

            log.info("Buscando placeholder '{}' en {} runs", placeholder, runs.size());

            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.getText(0);

                log.debug("Run {}: '{}'", i, runText);

                if (runText != null && runText.contains(placeholder)) {
                    log.info("¡Placeholder encontrado en run {}! Reemplazando con imagen", i);

                    // Eliminar el texto del placeholder
                    run.setText("", 0);

                    try (ByteArrayInputStream bis = new ByteArrayInputStream(imageData)) {
                        int pictureType = XWPFDocument.PICTURE_TYPE_PNG;
                        String fileName = "firma_" + System.currentTimeMillis() + ".png";

                        if (imageData.length > 2) {
                            if (imageData[0] == (byte) 0xFF && imageData[1] == (byte) 0xD8) {
                                pictureType = XWPFDocument.PICTURE_TYPE_JPEG;
                                fileName = "firma_" + System.currentTimeMillis() + ".jpg";
                            }
                        }

                        int width = 200;
                        int height = 100;
                        int widthEMU = width * 9525;
                        int heightEMU = height * 9525;

                        // Insertar imagen inline simple
                        run.addPicture(bis, pictureType, fileName, widthEMU, heightEMU);

                        log.info("✓ Imagen inline insertada: {} bytes", imageData.length);

                    } catch (Exception e) {
                        log.error("✗ Error al insertar imagen: {}", e.getMessage(), e);
                    }

                    break;
                }
            }
        } catch (Exception e) {
            log.error("✗ Error general al reemplazar con imagen: {}", e.getMessage(), e);
        }
    }

    private String formatDate(LocalDate date) {
        if (date == null) return "";
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}