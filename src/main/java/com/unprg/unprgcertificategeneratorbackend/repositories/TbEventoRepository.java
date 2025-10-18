package com.unprg.unprgcertificategeneratorbackend.repositories;

import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbEvento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TbEventoRepository extends JpaRepository<TbEvento, Integer> {
}