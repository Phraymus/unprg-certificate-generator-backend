package com.unprg.unprgcertificategeneratorbackend.repositories;

import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TbUsuarioRepository extends JpaRepository<TbUsuario, Integer> {
}