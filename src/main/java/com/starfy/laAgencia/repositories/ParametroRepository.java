package com.starfy.laAgencia.repositories;

import com.starfy.laAgencia.models.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Parametro.Id> {

    Optional<Parametro> findById_Clave(String clave);
}
