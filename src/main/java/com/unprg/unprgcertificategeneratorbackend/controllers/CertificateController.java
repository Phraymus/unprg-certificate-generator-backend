package com.unprg.unprgcertificategeneratorbackend.controllers;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbParticipanteDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbParticipanteIdDto;
import com.unprg.unprgcertificategeneratorbackend.services.CertificateGenerationService;
import com.unprg.unprgcertificategeneratorbackend.services.TbParticipanteService;
import com.unprg.unprgcertificategeneratorbackend.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@Slf4j
public class CertificateController {

    private final CertificateGenerationService certificateGenerationService;
    private final TbParticipanteService tbParticipanteService;

    @PostMapping(path = "/generate", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public ResponseEntity<?> generateCertificate(@RequestBody TbParticipanteIdDto participanteId) {
        try {
            log.info("Generando certificado para participante: evento={}, persona={}",
                    participanteId.getIdtbEvento(), participanteId.getIdtbPersona());

            // Buscar el participante completo
            TbParticipanteDto participante = tbParticipanteService.findById(participanteId);

            if (participante == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Participante no encontrado"));
            }

            // Generar el certificado Word
            byte[] wordBytes = certificateGenerationService.generateCertificateWord(participante);

            // Crear nombre de archivo
            String fileName = String.format("certificado_%s_%s_%s.docx",
                    participante.getTbEvento().getCodigo(),
                    participante.getTbPersona().getDni(),
                    java.time.LocalDate.now().toString());

            // Configurar headers para descarga
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentLength(wordBytes.length);

            log.info("Certificado Word generado exitosamente: {} bytes", wordBytes.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(wordBytes);

        } catch (Exception e) {
            log.error("Error al generar certificado", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al generar certificado: " + e.getMessage()));
        }
    }

    @GetMapping(path = "/generate/{eventoId}/{personaId}", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public ResponseEntity<?> generateCertificateByIds(
            @PathVariable("eventoId") Integer eventoId,
            @PathVariable("personaId") Integer personaId) {

        try {
            log.info("Generando certificado para evento={}, persona={}", eventoId, personaId);

            // Crear ID compuesto
            TbParticipanteIdDto participanteId = TbParticipanteIdDto.builder()
                    .idtbEvento(eventoId)
                    .idtbPersona(personaId)
                    .build();

            // Buscar el participante completo
            TbParticipanteDto participante = tbParticipanteService.findById(participanteId);

            if (participante == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Participante no encontrado"));
            }

            // Generar el certificado Word
            byte[] wordBytes = certificateGenerationService.generateCertificateWord(participante);

            // Crear nombre de archivo
            String fileName = String.format("certificado_%s_%s_%s.docx",
                    participante.getTbEvento().getCodigo(),
                    participante.getTbPersona().getDni(),
                    java.time.LocalDate.now().toString());

            // Configurar headers para descarga
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentLength(wordBytes.length);

            log.info("Certificado Word generado exitosamente: {} bytes", wordBytes.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(wordBytes);

        } catch (Exception e) {
            log.error("Error al generar certificado", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al generar certificado: " + e.getMessage()));
        }
    }

    @GetMapping(path = "/preview/{eventoId}/{personaId}")
    public ResponseEntity<?> previewCertificateData(
            @PathVariable("eventoId") Integer eventoId,
            @PathVariable("personaId") Integer personaId) {

        try {
            // Crear ID compuesto
            TbParticipanteIdDto participanteId = TbParticipanteIdDto.builder()
                    .idtbEvento(eventoId)
                    .idtbPersona(personaId)
                    .build();

            // Buscar el participante completo
            TbParticipanteDto participante = tbParticipanteService.findById(participanteId);

            if (participante == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Participante no encontrado"));
            }

            // Retornar los datos que se usarían en el certificado
            return ResponseEntity.ok(ApiResponse.ok("Datos del certificado", participante));

        } catch (Exception e) {
            log.error("Error al obtener datos del certificado", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error al obtener datos del certificado: " + e.getMessage()));
        }
    }
}