package com.soulcode.projetofinal.servicesTest;

import com.soulcode.projetofinal.models.Administrato;
import com.soulcode.projetofinal.repositories.AdministratoRepository;
import com.soulcode.projetofinal.repositories.DepartmentRepository;
import com.soulcode.projetofinal.repositories.PriorityRepository;
import com.soulcode.projetofinal.repositories.SupportRequestRepository;
import com.soulcode.projetofinal.services.AdministratoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
class AdministratoServiceTest {

    @Mock
    private AdministratoRepository administratoRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private PriorityRepository priorityRepository;

    @Mock
    private SupportRequestRepository supportRequestRepository;

    @InjectMocks
    private AdministratoService administratoService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

}
