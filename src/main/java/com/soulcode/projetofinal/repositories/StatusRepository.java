package com.soulcode.projetofinal.repositories;

import com.soulcode.projetofinal.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    Status findByName(String statusName);
}
