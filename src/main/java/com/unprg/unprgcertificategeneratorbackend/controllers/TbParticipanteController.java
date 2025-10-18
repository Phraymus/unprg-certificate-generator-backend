package com.unprg.unprgcertificategeneratorbackend.controllers;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbParticipanteDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbParticipanteIdDto;
import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbPersonaDto;
import com.unprg.unprgcertificategeneratorbackend.services.TbParticipanteService;
import com.unprg.unprgcertificategeneratorbackend.utils.ApiResponse;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericController;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tbparticipantes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
class TbParticipanteController extends GenericController<TbParticipanteDto, TbParticipanteIdDto> {

    private final TbParticipanteService tbParticipanteService;

    @Override
    public GenericCrud<TbParticipanteDto, TbParticipanteIdDto> getCrud() {
        return tbParticipanteService;
    }

    @Override
    protected TbParticipanteIdDto getPK(TbParticipanteDto d) {
        return d.getId();
    }

    @GetMapping(path = "/{idEvento}/{idPersona}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findById(@PathVariable("idEvento") Integer idEvento, 
                                               @PathVariable("idPersona") Integer idPersona) {
        TbParticipanteIdDto id = TbParticipanteIdDto.builder()
                .idtbEvento(idEvento)
                .idtbPersona(idPersona)
                .build();
        TbParticipanteDto dto = getCrud().findById(id);
        return ResponseEntity.ok(ApiResponse.ok("obtener Participante por id", dto));
    }

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        List<TbParticipanteDto> list = tbParticipanteService.findAll();
        return ResponseEntity.ok(ApiResponse.ok("obtener Participantes", list));
    }

    @GetMapping(path = "/findAllByIdEvento/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAllByIdEvento(@PathVariable("id") Integer k) {
        List<TbParticipanteDto> list = tbParticipanteService.findAllByIdEvento(k);
        return ResponseEntity.ok(ApiResponse.ok("obtener Personas por idEvento", list));
    }
}