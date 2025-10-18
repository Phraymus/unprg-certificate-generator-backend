package com.unprg.unprgcertificategeneratorbackend.repositories;

import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbCertificado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TbCertificadoRepository extends JpaRepository<TbCertificado, Integer> {
}