package com.starfy.laAgencia.repositories;

import com.starfy.laAgencia.models.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Integer> {
    Optional<Foto> findByPersona_Id(Integer id);
}
