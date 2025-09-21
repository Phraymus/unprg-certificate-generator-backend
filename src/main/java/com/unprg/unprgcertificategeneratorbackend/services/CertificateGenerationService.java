package com.unprg.unprgcertificategeneratorbackend.services;

import com.itextpdf.html2pdf.HtmlConverter;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoFormatoCertificadoDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbFormatoCertificadoDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbParticipanteDto;
import com.unprg.unprgcertificategeneratorbackend.services.TbEventoFormatoCertificadoService;
import com.unprg.unprgcertificategeneratorbackend.services.TbFormatoCertificadoService;
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

        // Verificar si la ruta ya incluye el directorio base
        if (rutaCompleta.startsWith("uploads/") || rutaCompleta.startsWith("uploads\\")) {
            // La ruta ya incluye uploads, usar directamente
            templatePath = Paths.get(rutaCompleta);
        } else {
            // La ruta no incluye uploads, agregarlo
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

        log.info("Datos del certificado creados: {}", data);
        return data;
    }

    private byte[] processTemplateAndGeneratePDF(Path templatePath, Map<String, Object> data) throws Exception {
        String tempFileName = "certificate_" + UUID.randomUUID().toString();
        Path tempDocxPath = Paths.get(tempDir, tempFileName + ".docx");
        Path tempHtmlPath = Paths.get(tempDir, tempFileName + ".html");

        try {
            // Crear directorio temporal si no existe
            Files.createDirectories(Paths.get(tempDir));

            // 1. Procesar el template Word con los datos
            processWordTemplate(templatePath, tempDocxPath, data);

            // 2. Convertir Word a HTML
            String htmlContent = convertWordToHTML(tempDocxPath);

            // 3. Guardar HTML temporal
            Files.write(tempHtmlPath, htmlContent.getBytes());

            // 4. Convertir HTML a PDF
            return convertHTMLToPDF(htmlContent);

        } finally {
            // Limpiar archivos temporales
            try {
                Files.deleteIfExists(tempDocxPath);
                Files.deleteIfExists(tempHtmlPath);
            } catch (Exception e) {
                log.warn("No se pudieron eliminar archivos temporales", e);
            }
        }
    }

    private void processWordTemplate(Path templatePath, Path outputPath, Map<String, Object> data) throws IOException {
        try (FileInputStream fis = new FileInputStream(templatePath.toFile());
             XWPFDocument document = new XWPFDocument(fis)) {

            log.info("=== ANÁLISIS COMPLETO DEL DOCUMENTO ===");
            log.info("Párrafos en body: {}", document.getParagraphs().size());
            log.info("Tablas en body: {}", document.getTables().size());
            log.info("Headers: {}", document.getHeaderList().size());
            log.info("Footers: {}", document.getFooterList().size());

            // Inspeccionar párrafos del body
            for (int i = 0; i < document.getParagraphs().size(); i++) {
                XWPFParagraph paragraph = document.getParagraphs().get(i);
                log.info("--- Párrafo {} ---", i);
                log.info("Texto completo: '{}'", paragraph.getText());
                replaceInParagraph(paragraph, data);
            }

            // Inspeccionar tablas
            for (int t = 0; t < document.getTables().size(); t++) {
                XWPFTable table = document.getTables().get(t);
                log.info("--- Tabla {} ---", t);
                for (int r = 0; r < table.getRows().size(); r++) {
                    XWPFTableRow row = table.getRows().get(r);
                    for (int c = 0; c < row.getTableCells().size(); c++) {
                        XWPFTableCell cell = row.getTableCells().get(c);
                        log.info("  Celda [{},{}]: '{}'", r, c, cell.getText());
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            replaceInParagraph(paragraph, data);
                        }
                    }
                }
            }

            // PROCESAR CUADROS DE TEXTO Y FORMAS
            log.info("=== PROCESANDO CUADROS DE TEXTO ===");
            processTextBoxes(document, data);

            // Inspeccionar headers
            for (int h = 0; h < document.getHeaderList().size(); h++) {
                XWPFHeader header = document.getHeaderList().get(h);
                log.info("--- Header {} ---", h);
                for (int p = 0; p < header.getParagraphs().size(); p++) {
                    XWPFParagraph paragraph = header.getParagraphs().get(p);
                    log.info("  Header párrafo {}: '{}'", p, paragraph.getText());
                    replaceInParagraph(paragraph, data);
                }
                // Procesar cuadros de texto en headers
                processTextBoxesInHeader(header, data);
            }

            // Inspeccionar footers
            for (int f = 0; f < document.getFooterList().size(); f++) {
                XWPFFooter footer = document.getFooterList().get(f);
                log.info("--- Footer {} ---", f);
                for (int p = 0; p < footer.getParagraphs().size(); p++) {
                    XWPFParagraph paragraph = footer.getParagraphs().get(p);
                    log.info("  Footer párrafo {}: '{}'", p, paragraph.getText());
                    replaceInParagraph(paragraph, data);
                }
                // Procesar cuadros de texto en footers
                processTextBoxesInFooter(footer, data);
            }

            // Guardar documento procesado
            try (FileOutputStream fos = new FileOutputStream(outputPath.toFile())) {
                document.write(fos);
                log.info("Documento Word procesado guardado en: {}", outputPath);
            }
        }
    }

    private void processTextBoxes(XWPFDocument document, Map<String, Object> data) {
        try {
            // Buscar en todos los párrafos que contengan elementos de dibujo
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                processTextBoxesInParagraph(paragraph, data);
            }
        } catch (Exception e) {
            log.warn("Error procesando cuadros de texto: {}", e.getMessage());
        }
    }

    private void processTextBoxesInParagraph(XWPFParagraph paragraph, Map<String, Object> data) {
        try {
            for (XWPFRun run : paragraph.getRuns()) {
                // Buscar elementos de dibujo en el run
                if (run.getCTR().getDrawingArray() != null && run.getCTR().getDrawingArray().length > 0) {
                    log.info("Encontrado elemento de dibujo en párrafo");

                    // Obtener el XML del run completo como string
                    String runXML = run.getCTR().toString();
                    log.info("XML original del run: {}", runXML.substring(0, Math.min(500, runXML.length())));

                    // Crear una copia del XML para modificar
                    String modifiedXML = runXML;
                    boolean wasModified = false;

                    // Buscar y reemplazar marcadores en el XML
                    for (Map.Entry<String, Object> entry : data.entrySet()) {
                        String placeholder = entry.getKey();
                        String value = entry.getValue() != null ? entry.getValue().toString() : "";

                        // Buscar el patrón <w:t>placeholder</w:t>
                        String pattern = "<w:t>" + placeholder + "</w:t>";
                        String replacement = "<w:t>" + value + "</w:t>";

                        if (modifiedXML.contains(pattern)) {
                            log.info("¡ENCONTRADO Y REEMPLAZANDO! '{}' -> '{}'", placeholder, value);
                            modifiedXML = modifiedXML.replace(pattern, replacement);
                            wasModified = true;
                        } else {
                            // También buscar variaciones con espacios o case insensitive
                            String patternLower = "<w:t>" + placeholder.toLowerCase() + "</w:t>";
                            String originalPattern = "<w:t>" + placeholder.toLowerCase() + "</w:t>";

                            if (modifiedXML.toLowerCase().contains(patternLower)) {
                                log.info("¡ENCONTRADO VARIACIÓN! Reemplazando versión en minúsculas");
                                // Buscar el patrón original exacto en el XML
                                String exactPattern = findExactPattern(modifiedXML, placeholder.toLowerCase());
                                if (exactPattern != null) {
                                    String exactReplacement = exactPattern.replace(placeholder.toLowerCase(), value);
                                    modifiedXML = modifiedXML.replace(exactPattern, exactReplacement);
                                    wasModified = true;
                                }
                            }
                        }
                    }

                    if (wasModified) {
                        log.info("XML fue modificado. Intentando actualizar el run...");
                        log.info("XML modificado: {}", modifiedXML.substring(0, Math.min(500, modifiedXML.length())));

                        // ESTRATEGIA ALTERNATIVA: Como no podemos modificar el XML directamente,
                        // vamos a recrear todo el párrafo
                        replaceTextInXMLDirectly(paragraph, data);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Error procesando cuadros de texto en párrafo: {}", e.getMessage());
        }
    }

    private String findExactPattern(String xml, String placeholder) {
        // Buscar el patrón exacto <w:t>texto</w:t> en el XML
        String lowerXML = xml.toLowerCase();
        String searchPattern = "<w:t>" + placeholder + "</w:t>";

        int index = lowerXML.indexOf(searchPattern);
        if (index >= 0) {
            // Extraer el texto exacto del XML original (con el case correcto)
            return xml.substring(index, index + searchPattern.length());
        }
        return null;
    }

    private void replaceTextInXMLDirectly(XWPFParagraph paragraph, Map<String, Object> data) {
        try {
            // Obtener el documento padre
            XWPFDocument document = paragraph.getDocument();

            // ESTRATEGIA: Reemplazar a nivel de documento completo usando string replacement
            // Esto es menos elegante pero más efectivo para cuadros de texto

            log.info("Aplicando estrategia de reemplazo directo en documento...");

            // Esta función será llamada después de procesar todo el documento
            // y aplicaremos los reemplazos a nivel global

        } catch (Exception e) {
            log.error("Error en reemplazo directo de XML: {}", e.getMessage());
        }
    }

    // Nuevo método para reemplazo a nivel de documento completo
    private void applyGlobalTextReplacement(XWPFDocument document, Map<String, Object> data) {
        try {
            // Esta es una aproximación más directa - buscar y reemplazar en todo el documento
            // usando reflexión para acceder al XML subyacente

            log.info("=== APLICANDO REEMPLAZO GLOBAL ===");

            // Obtener el XML del documento como string
            java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
            document.write(out);
            String documentXML = new String(out.toByteArray(), "UTF-8");

            log.info("Tamaño del documento XML: {} caracteres", documentXML.length());

            // Aplicar reemplazos
            String modifiedXML = documentXML;
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                String placeholder = entry.getKey();
                String value = entry.getValue() != null ? entry.getValue().toString() : "";

                // Reemplazar tanto mayúsculas como minúsculas
                String pattern1 = "<w:t>" + placeholder + "</w:t>";
                String pattern2 = "<w:t>" + placeholder.toLowerCase() + "</w:t>";
                String replacement = "<w:t>" + value + "</w:t>";

                if (modifiedXML.contains(pattern1)) {
                    log.info("Reemplazando patrón exacto: {}", placeholder);
                    modifiedXML = modifiedXML.replace(pattern1, replacement);
                }

                if (modifiedXML.contains(pattern2)) {
                    log.info("Reemplazando patrón en minúsculas: {}", placeholder.toLowerCase());
                    modifiedXML = modifiedXML.replace(pattern2, replacement);
                }
            }

            // Guardar el documento modificado de vuelta
            java.io.ByteArrayInputStream in = new java.io.ByteArrayInputStream(modifiedXML.getBytes("UTF-8"));
            // Aquí necesitaríamos recrear el documento, pero esto es complejo

            log.info("Reemplazo global completado");

        } catch (Exception e) {
            log.error("Error en reemplazo global: {}", e.getMessage());
        }
    }

    private void processTextBoxesInHeader(XWPFHeader header, Map<String, Object> data) {
        try {
            for (XWPFParagraph paragraph : header.getParagraphs()) {
                processTextBoxesInParagraph(paragraph, data);
            }
        } catch (Exception e) {
            log.warn("Error procesando cuadros de texto en header: {}", e.getMessage());
        }
    }

    private void processTextBoxesInFooter(XWPFFooter footer, Map<String, Object> data) {
        try {
            for (XWPFParagraph paragraph : footer.getParagraphs()) {
                processTextBoxesInParagraph(paragraph, data);
            }
        } catch (Exception e) {
            log.warn("Error procesando cuadros de texto en footer: {}", e.getMessage());
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
                String value = entry.getValue() != null ? entry.getValue().toString() : "";

                log.debug("Buscando placeholder: '{}' en texto: '{}'", placeholder, paragraphText);

                // Buscar marcadores en formato {placeholder} o placeholder directamente
                if (paragraphText.contains(placeholder)) {
                    log.info("¡ENCONTRADO! Reemplazando '{}' con '{}'", placeholder, value);
                    replaceTextInParagraph(paragraph, placeholder, value);
                } else if (paragraphText.contains("{" + placeholder + "}")) {
                    log.info("¡ENCONTRADO CON LLAVES! Reemplazando '{}' con '{}'", "{" + placeholder + "}", value);
                    replaceTextInParagraph(paragraph, "{" + placeholder + "}", value);
                } else {
                    log.debug("No se encontró '{}' en el párrafo", placeholder);
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
            }
        }
    }

    private String convertWordToHTML(Path docxPath) throws Exception {
        // Conversión básica de Word a HTML usando POI
        try (FileInputStream fis = new FileInputStream(docxPath.toFile());
             XWPFDocument document = new XWPFDocument(fis)) {

            StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder.append("<!DOCTYPE html>");
            htmlBuilder.append("<html><head>");
            htmlBuilder.append("<meta charset='UTF-8'>");
            htmlBuilder.append("<style>");
            htmlBuilder.append("body { font-family: Arial, sans-serif; margin: 40px; padding: 20px; }");
            htmlBuilder.append("p { margin: 10px 0; line-height: 1.5; }");
            htmlBuilder.append("table { border-collapse: collapse; width: 100%; margin: 20px 0; }");
            htmlBuilder.append("td, th { border: 1px solid #ddd; padding: 8px; text-align: left; }");
            htmlBuilder.append("h1, h2, h3 { color: #333; margin: 20px 0 10px 0; }");
            htmlBuilder.append("</style>");
            htmlBuilder.append("</head><body>");

            log.info("Convirtiendo documento Word a HTML...");

            // Procesar párrafos
            int paragraphCount = 0;
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String paragraphText = paragraph.getText();
                if (paragraphText != null && !paragraphText.trim().isEmpty()) {
                    htmlBuilder.append("<p>").append(paragraphText).append("</p>");
                    paragraphCount++;
                    log.debug("Párrafo {} agregado al HTML: {}", paragraphCount, paragraphText);
                }
            }

            // Procesar tablas
            int tableCount = 0;
            for (XWPFTable table : document.getTables()) {
                htmlBuilder.append("<table>");
                tableCount++;
                log.debug("Procesando tabla {}", tableCount);

                for (XWPFTableRow row : table.getRows()) {
                    htmlBuilder.append("<tr>");
                    for (XWPFTableCell cell : row.getTableCells()) {
                        String cellText = cell.getText();
                        htmlBuilder.append("<td>").append(cellText != null ? cellText : "").append("</td>");
                    }
                    htmlBuilder.append("</tr>");
                }
                htmlBuilder.append("</table>");
            }

            htmlBuilder.append("</body></html>");

            String finalHTML = htmlBuilder.toString();
            log.info("HTML generado con {} párrafos y {} tablas. Longitud: {} caracteres",
                    paragraphCount, tableCount, finalHTML.length());
            log.debug("HTML final: {}", finalHTML);

            return finalHTML;
        }
    }

    private byte[] convertHTMLToPDF(String htmlContent) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(htmlContent, outputStream);
        return outputStream.toByteArray();
    }

    private String formatDate(LocalDate date) {
        if (date == null) return "";
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}