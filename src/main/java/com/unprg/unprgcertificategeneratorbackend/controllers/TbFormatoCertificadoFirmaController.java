package com.unprg.unprgcertificategeneratorbackend.controllers;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbFormatoCertificadoFirmaDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbFormatoCertificadoFirmaIdDto;
import com.unprg.unprgcertificategeneratorbackend.services.TbFormatoCertificadoFirmaService;
import com.unprg.unprgcertificategeneratorbackend.utils.ApiResponse;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericController;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tbformatocertificadofirmas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
class TbFormatoCertificadoFirmaController extends GenericController<TbFormatoCertificadoFirmaDto, TbFormatoCertificadoFirmaIdDto> {

    private final TbFormatoCertificadoFirmaService tbFormatoCertificadoFirmaService;

    @Override
    public GenericCrud<TbFormatoCertificadoFirmaDto, TbFormatoCertificadoFirmaIdDto> getCrud() {
        return tbFormatoCertificadoFirmaService;
    }

    @Override
    protected TbFormatoCertificadoFirmaIdDto getPK(TbFormatoCertificadoFirmaDto d) {
        return d.getId();
    }

    @GetMapping(path = "/{idFirma}/{idEventoFormato}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findById(@PathVariable("idFirma") Integer idFirma, 
                                               @PathVariable("idEventoFormato") Integer idEventoFormato) {
        TbFormatoCertificadoFirmaIdDto id = TbFormatoCertificadoFirmaIdDto.builder()
                .idtbFirma(idFirma)
                .idtbFormatoCertificado(idEventoFormato)
                .build();
        TbFormatoCertificadoFirmaDto dto = getCrud().findById(id);
        return ResponseEntity.ok(ApiResponse.ok("obtener Formato Certificado Firma por id", dto));
    }

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        List<TbFormatoCertificadoFirmaDto> list = tbFormatoCertificadoFirmaService.findAll();
        return ResponseEntity.ok(ApiResponse.ok("obtener listado de Formato Certificado Firmas", list));
    }

    @GetMapping(path = "/findAllByIdFormatoCertificado/{idFormatoCertificado}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAllByIdTbEvento(
            @PathVariable("idFormatoCertificado") Integer idFormatoCertificado) {
        List<TbFormatoCertificadoFirmaDto> list =
                tbFormatoCertificadoFirmaService.findAllByIdFormatoCertificado(idFormatoCertificado);
        return ResponseEntity.ok(ApiResponse.ok("obtener Eventos Formato Certificado Firmas por idFormatoCertificado", list));
    }
}