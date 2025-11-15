package com.unprg.unprgcertificategeneratorbackend.controllers;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbTipoParticipanteDto;
import com.unprg.unprgcertificategeneratorbackend.services.TbTipoParticipanteService;
import com.unprg.unprgcertificategeneratorbackend.utils.ApiResponse;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericController;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tbtipoparticipantes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
class TbTipoParticipanteController extends GenericController<TbTipoParticipanteDto, Integer> {

    private final TbTipoParticipanteService tbTipoParticipanteService;

    @Override
    public GenericCrud<TbTipoParticipanteDto, Integer> getCrud() {
        return tbTipoParticipanteService;
    }

    @Override
    protected Integer getPK(TbTipoParticipanteDto d) {
        return d.getId();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findById(@PathVariable("id") Integer k) {
        TbTipoParticipanteDto dto = getCrud().findById(k);
        return ResponseEntity.ok(ApiResponse.ok("obtener Tipo Participante por id", dto));
    }

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        List<TbTipoParticipanteDto> list = tbTipoParticipanteService.findAll();
        return ResponseEntity.ok(ApiResponse.ok("obtener listado de tipos de participante", list));
    }
}