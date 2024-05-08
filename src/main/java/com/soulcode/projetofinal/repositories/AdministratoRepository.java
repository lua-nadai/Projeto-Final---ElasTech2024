package com.soulcode.projetofinal.repositories;

import com.soulcode.projetofinal.models.Administrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratoRepository extends JpaRepository<Administrato, Long> {

    Optional<Administrato> findByEmailContainingIgnoreCase(String name);
}