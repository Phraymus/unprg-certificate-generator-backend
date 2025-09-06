package com.unprg.unprgcertificategeneratorbackend.controllers;

import com.unprg.unprgcertificategeneratorbackend.objects.dto.TbFirmaDto;
import com.unprg.unprgcertificategeneratorbackend.services.TbFirmaService;
import com.unprg.unprgcertificategeneratorbackend.utils.ApiResponse;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericController;
import com.unprg.unprgcertificategeneratorbackend.utils.GenericCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tbfirmas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
class TbFirmaController extends GenericController<TbFirmaDto, Integer> {

    private final TbFirmaService tbFirmaService;

    @Override
    public GenericCrud<TbFirmaDto, Integer> getCrud() {
        return tbFirmaService;
    }

    @Override
    protected Integer getPK(TbFirmaDto d) {
        return d.getId();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findById(@PathVariable("id") Integer k) {
        TbFirmaDto dto = getCrud().findById(k);
        return ResponseEntity.ok(ApiResponse.ok("obtener Firma por id", dto));
    }

    @GetMapping(path = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        List<TbFirmaDto> list = tbFirmaService.findAll();
        return ResponseEntity.ok(ApiResponse.ok("obtener Firmas", list));
    }
}
