package com.unprg.unprgcertificategeneratorbackend.controllers;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbFormatoCertificadoDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbUsuarioDto;
import com.unprg.unprgcertificategeneratorbackend.services.TbFormatoCertificadoService;
import com.unprg.unprgcertificategeneratorbackend.utils.ApiResponse;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericController;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tbformatocertificados")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
class TbFormatoCertificadoController extends GenericController<TbFormatoCertificadoDto, Integer> {

    private final TbFormatoCertificadoService tbFormatoCertificadoService;

    // Directorio donde se guardarán los archivos
    private final String UPLOAD_DIR = "uploads/formatos-certificado/";

    @Override
    public GenericCrud<TbFormatoCertificadoDto, Integer> getCrud() {
        return tbFormatoCertificadoService;
    }

    @Override
    protected Integer getPK(TbFormatoCertificadoDto d) {
        return d.getId();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findById(@PathVariable("id") Integer k) {
        TbFormatoCertificadoDto dto = getCrud().findById(k);
        return ResponseEntity.ok(ApiResponse.ok("obtener Formato Certificado por id", dto));
    }

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        List<TbFormatoCertificadoDto> list = tbFormatoCertificadoService.findAll();
        return ResponseEntity.ok(ApiResponse.ok("obtener Formatos Certificado", list));
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> uploadFormat(
            @RequestParam("codigo") String codigo,
            @RequestParam("nombreFormato") String nombreFormato,
            @RequestParam("idtbUsuario") Integer idtbUsuario,
            @RequestParam("archivo") MultipartFile archivo) {

        try {
            // Validar archivo
            if (archivo.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("El archivo es requerido"));
            }

            // Validar tipo de archivo
            String contentType = archivo.getContentType();
            if (contentType == null || (!contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                    && !contentType.equals("application/msword"))) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Solo se permiten archivos Word (.doc, .docx)"));
            }

            // Crear directorio si no existe
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generar nombre único para el archivo
            String originalFilename = archivo.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // Guardar archivo
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(archivo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Crear DTO con la información
            TbFormatoCertificadoDto formatoDto = TbFormatoCertificadoDto.builder()
                    .codigo(codigo)
                    .nombreFormato(nombreFormato)
                    .rutaFormato(UPLOAD_DIR + uniqueFilename)
                    .idtbUsuario(TbUsuarioDto.builder().id(idtbUsuario).build())
                    .build();

            // Guardar en base de datos
            TbFormatoCertificadoDto savedDto = tbFormatoCertificadoService.insert(formatoDto);

            return ResponseEntity.ok(ApiResponse.ok("Formato creado exitosamente", savedDto));

        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error al guardar el archivo: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error interno del servidor: " + e.getMessage()));
        }
    }

    @PutMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> updateFormatWithFile(
            @RequestParam("id") Integer id,
            @RequestParam("codigo") String codigo,
            @RequestParam("nombreFormato") String nombreFormato,
            @RequestParam("idtbUsuario") Integer idtbUsuario,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo) {

        try {
            // Buscar formato existente
            TbFormatoCertificadoDto existingDto = tbFormatoCertificadoService.findById(id);
            if (existingDto == null) {
                return ResponseEntity.notFound().build();
            }

            String rutaFormato = existingDto.getRutaFormato();

            // Si se proporciona un nuevo archivo
            if (archivo != null && !archivo.isEmpty()) {
                // Validar tipo de archivo
                String contentType = archivo.getContentType();
                if (contentType == null || (!contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                        && !contentType.equals("application/msword"))) {
                    return ResponseEntity.badRequest()
                            .body(ApiResponse.error("Solo se permiten archivos Word (.doc, .docx)"));
                }

                // Eliminar archivo anterior si existe
                if (rutaFormato != null) {
                    Path oldFilePath = Paths.get(rutaFormato);
                    if (Files.exists(oldFilePath)) {
                        Files.delete(oldFilePath);
                    }
                }

                // Crear directorio si no existe
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Generar nombre único para el nuevo archivo
                String originalFilename = archivo.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

                // Guardar nuevo archivo
                Path filePath = uploadPath.resolve(uniqueFilename);
                Files.copy(archivo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                rutaFormato = UPLOAD_DIR + uniqueFilename;
            }

            // Actualizar DTO
            TbFormatoCertificadoDto formatoDto = TbFormatoCertificadoDto.builder()
                    .id(id)
                    .codigo(codigo)
                    .nombreFormato(nombreFormato)
                    .rutaFormato(rutaFormato)
                    .idtbUsuario(TbUsuarioDto.builder().id(idtbUsuario).build())
                    .build();

            // Actualizar en base de datos
            TbFormatoCertificadoDto updatedDto = tbFormatoCertificadoService.update(formatoDto);

            return ResponseEntity.ok(ApiResponse.ok("Formato actualizado exitosamente", updatedDto));

        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error al procesar el archivo: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error interno del servidor: " + e.getMessage()));
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) {
        try {
            TbFormatoCertificadoDto formatoDto = tbFormatoCertificadoService.findById(id);
            if (formatoDto == null || formatoDto.getRutaFormato() == null) {
                return ResponseEntity.notFound().build();
            }

            Path filePath = Paths.get(formatoDto.getRutaFormato());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                String filename = filePath.getFileName().toString();
                // Intentar obtener el nombre original del formato
                String downloadFilename = formatoDto.getCodigo() + "_" + formatoDto.getNombreFormato() + ".docx";

                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadFilename + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/preview/{id}")
    public ResponseEntity<Resource> previewFile(@PathVariable Integer id) {
        try {
            TbFormatoCertificadoDto formatoDto = tbFormatoCertificadoService.findById(id);
            if (formatoDto == null || formatoDto.getRutaFormato() == null) {
                return ResponseEntity.notFound().build();
            }

            Path filePath = Paths.get(formatoDto.getRutaFormato());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    @DeleteMapping
    public ResponseEntity<ApiResponse> delete(@RequestBody TbFormatoCertificadoDto dto) {
        try {
            // Eliminar archivo físico si existe
            if (dto.getRutaFormato() != null) {
                Path filePath = Paths.get(dto.getRutaFormato());
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            }

            // Eliminar registro de base de datos
            getCrud().delete(dto);
            return ResponseEntity.ok(ApiResponse.ok("Formato eliminado exitosamente", null));

        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error al eliminar archivo: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error al eliminar formato: " + e.getMessage()));
        }
    }
}