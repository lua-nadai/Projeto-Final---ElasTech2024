package com.soulcode.projetofinal.repositories;

import com.soulcode.projetofinal.models.SupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupportRequestRepository extends JpaRepository<SupportRequest, Integer> {
    List<SupportRequest> findByStatusId(int statusId);

    @Query("SELECT r FROM SupportRequest r WHERE r.user.id = ?1")
    List<SupportRequest> findByUserId(int userId);

    @Query("SELECT r FROM SupportRequest r WHERE r.technician.id = ?1")
    List<SupportRequest> findByTechId(int techId);

    @Query("SELECT COUNT(*) FROM SupportRequest r WHERE r.status.name = 'Aguardando Técnico'")
    int countOpenRequests();

    @Query("SELECT COUNT(*) FROM SupportRequest r WHERE r.status.name = 'Em atendimento'")
    int countInProgressRequests();

    @Query("SELECT COUNT(*) FROM SupportRequest r WHERE r.status.name = 'Escalado para outro setor'")
    int countAnotherDepartmentRequests();

    @Query("SELECT COUNT(*) FROM SupportRequest r WHERE r.status.name = 'Finalizado'")
    int countCompletedRequests();

}
