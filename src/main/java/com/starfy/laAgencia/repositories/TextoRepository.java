package com.starfy.laAgencia.repositories;

import com.starfy.laAgencia.models.Texto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextoRepository extends JpaRepository<Texto,Integer> {
}
