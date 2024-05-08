package com.soulcode.projetofinal.repositories;

import com.soulcode.projetofinal.models.SupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupportRequestRepository extends JpaRepository<SupportRequest, Integer> {
    List<SupportRequest> findByStatusId(int statusId);

    @Query("SELECT COUNT(r) FROM SupportRequest r JOIN r.status s WHERE r.status.statusName = 'Open'")
    int countOpenRequests();

    @Query("SELECT COUNT(r) FROM SupportRequest r JOIN r.status s WHERE r.status.statusName = 'InProgress'")
    int countInProgressRequests();

    @Query("SELECT COUNT(r) FROM SupportRequest r JOIN r.status s WHERE r.status.statusName = 'Waiting'")
    int countWaitingRequests();
}
