package com.starfy.laAgencia.repositories;

import com.starfy.laAgencia.models.Bailarin;
import com.starfy.laAgencia.models.Maestro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaestroRepository extends JpaRepository<Maestro, Integer> {

    Optional<Maestro> findByCodigo(String codigo);
}
