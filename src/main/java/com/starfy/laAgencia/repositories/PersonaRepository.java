package com.starfy.laAgencia.repositories;

import com.starfy.laAgencia.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona,Integer> {
}
