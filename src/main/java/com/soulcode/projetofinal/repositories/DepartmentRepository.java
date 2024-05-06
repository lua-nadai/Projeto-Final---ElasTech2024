package com.soulcode.projetofinal.repositories;

import com.soulcode.projetofinal.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
