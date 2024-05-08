package com.soulcode.projetofinal.repositories;

import com.soulcode.projetofinal.models.Status;
import com.soulcode.projetofinal.models.SupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    Status findByName(String statusName);
}
