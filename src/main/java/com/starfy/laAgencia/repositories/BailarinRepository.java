package com.starfy.laAgencia.repositories;

import com.starfy.laAgencia.models.Bailarin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BailarinRepository extends JpaRepository<Bailarin, Integer> {

    Optional<Bailarin> findByCodigo(String codigo);
    List<Bailarin> findByEsDestacado(Boolean esDetacado);
}
