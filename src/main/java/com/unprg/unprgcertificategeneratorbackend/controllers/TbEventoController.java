package com.unprg.unprgcertificategeneratorbackend.controllers;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbEventoDto;
import com.unprg.unprgcertificategeneratorbackend.services.TbEventoService;
import com.unprg.unprgcertificategeneratorbackend.utils.ApiResponse;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericController;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tbeventos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
class TbEventoController extends GenericController<TbEventoDto, Integer> {

    private final TbEventoService tbEventoService;

    @Override
    public GenericCrud<TbEventoDto, Integer> getCrud() {
        return tbEventoService;
    }

    @Override
    protected Integer getPK(TbEventoDto d) {
        return d.getId();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findById(@PathVariable("id") Integer k) {
        TbEventoDto dto = getCrud().findById(k);
        return ResponseEntity.ok(ApiResponse.ok("obtener Evento por id", dto));
    }

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        List<TbEventoDto> list = tbEventoService.findAll();
        return ResponseEntity.ok(ApiResponse.ok("obtener Eventos", list));
    }
}