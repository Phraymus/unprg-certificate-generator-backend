package com.unprg.unprgcertificategeneratorbackend.controllers;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbPersonaDto;
import com.unprg.unprgcertificategeneratorbackend.services.TbPersonaService;
import com.unprg.unprgcertificategeneratorbackend.utils.ApiResponse;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericController;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tbpersonas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
class TbPersonaController extends GenericController<TbPersonaDto, Integer> {

    private final TbPersonaService tbPersonaService;

    @Override
    public GenericCrud<TbPersonaDto, Integer> getCrud() {
        return tbPersonaService;
    }

    @Override
    protected Integer getPK(TbPersonaDto d) {
        return d.getId();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findById(@PathVariable("id") Integer k) {
        TbPersonaDto dto = getCrud().findById(k);
        return ResponseEntity.ok(ApiResponse.ok("obtener Persona por id", dto));
    }

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        List<TbPersonaDto> list = tbPersonaService.findAll();
        return ResponseEntity.ok(ApiResponse.ok("obtener Personas", list));
    }

    @GetMapping(path = "/findAllByNombreOrDni/{search}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAllByNombreOrDni(@PathVariable String search) {
        List<TbPersonaDto> list = tbPersonaService.findAllByNombreOrDni(search);
        return ResponseEntity.ok(ApiResponse.ok("obtener Personas por nombre o dni", list));
    }
}