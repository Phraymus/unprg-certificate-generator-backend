package com.unprg.unprgcertificategeneratorbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Configuration
class FileUploadConfig implements WebMvcConfigurer {

    // Configuración para servir archivos estáticos (opcional, para vista previa)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/")
                .setCachePeriod(3600); // Cache por 1 hora
    }
}

// Clase utilitaria para validaciones de archivos
@Component
class FileValidationUtil {

    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .docx
            "application/msword" // .doc
    );

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(".doc", ".docx");
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    public boolean isValidWordDocument(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        // Validar tamaño
        if (file.getSize() > MAX_FILE_SIZE) {
            return false;
        }

        // Validar tipo de contenido
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            return false;
        }

        // Validar extensión del archivo
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return false;
        }

        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(extension);
    }

    public String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }

        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }

        return filename.substring(lastDotIndex);
    }

    public String generateUniqueFilename(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        return UUID.randomUUID().toString() + extension;
    }
}

// Excepción personalizada para errores de archivo
class FileProcessingException extends RuntimeException {
    public FileProcessingException(String message) {
        super(message);
    }

    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}