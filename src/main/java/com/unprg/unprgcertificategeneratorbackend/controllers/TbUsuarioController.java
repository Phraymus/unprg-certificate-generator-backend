package com.unprg.unprgcertificategeneratorbackend.controllers;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbUsuarioDto;
import com.unprg.unprgcertificategeneratorbackend.services.TbUsuarioService;
import com.unprg.unprgcertificategeneratorbackend.utils.ApiResponse;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericController;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tbusuarios")
@RequiredArgsConstructor
class TbUsuarioController extends GenericController<TbUsuarioDto, Integer> {

    private final TbUsuarioService tbUsuarioService;

    @Override
    public GenericCrud<TbUsuarioDto, Integer> getCrud() {
        return tbUsuarioService;
    }

    @Override
    protected Integer getPK(TbUsuarioDto d) {
        return d.getId();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findById(@PathVariable("id") Integer k) {
        TbUsuarioDto dto = getCrud().findById(k);
        return ResponseEntity.ok(ApiResponse.ok("obtener Usuario por id", dto));
    }

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        List<TbUsuarioDto> list = tbUsuarioService.findAll();
        return ResponseEntity.ok(ApiResponse.ok("obtener Usuarios", list));
    }
}