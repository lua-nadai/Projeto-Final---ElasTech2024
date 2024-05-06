package com.soulcode.projetofinal.repositories;

import com.soulcode.projetofinal.models.SupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportRequestRepository extends JpaRepository<SupportRequest, Integer> {
    List<SupportRequest> findByStatusId(int statusId);
}
