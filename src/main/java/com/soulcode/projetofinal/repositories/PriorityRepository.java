package com.soulcode.projetofinal.repositories;

import com.soulcode.projetofinal.models.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityRepository
        extends JpaRepository<Priority, Integer> {
    Priority findByName(String priorityName);
}
