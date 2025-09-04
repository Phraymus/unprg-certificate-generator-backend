package com.unprg.unprgcertificategeneratorbackend.objects.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.unprg.unprgcertificategeneratorbackend.objects.entity.TbFirma}
 */
@Data
@Builder
@Value
public class TbFirmaDto implements Serializable {
    Integer id;
    String codigo;
    String nombre;
}