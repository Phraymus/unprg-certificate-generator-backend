package com.unprg.unprgcertificategeneratorbackend.repositories;

import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbFormatoCertificadoFirma;
import com.unprg.unprgcertificategeneratorbackend.objects.entity.TbFormatoCertificadoFirmaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TbFormatoCertificadoFirmaRepository extends JpaRepository<TbFormatoCertificadoFirma, TbFormatoCertificadoFirmaId> {
    List<TbFormatoCertificadoFirma> findAllByTbFormatoCertificadoId(Integer idTbFormatoCertificado);

//    void deleteAllByTbEventoFormatoCertificadoIdtbEvento(TbEventoFormatoCertificado tbEventoFormatoCertificadoIdtbEvento);
}