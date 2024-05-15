package com.soulcode.projetofinal.service;

import com.soulcode.projetofinal.constants.Constants;
import com.soulcode.projetofinal.models.Administrato;
import com.soulcode.projetofinal.models.Department;
import com.soulcode.projetofinal.repositories.AdministratoRepository;
import com.soulcode.projetofinal.repositories.DepartmentRepository;
import com.soulcode.projetofinal.services.AdministratoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AdministratoServiceTest {

    @InjectMocks
    private AdministratoService administratoService;

    @Mock
    private AdministratoRepository administratoRepository;

    @Mock
    private DepartmentRepository departmentRepository;


    @Test
    void getAdministratorByIdTest(){
        when(administratoRepository.findById(1L)).thenReturn(Optional.of(Constants.ADMINISTRATO));
        Administrato getAdministratorById = administratoService.getAdministratorById(1L);
        assertEquals(Constants.ADMINISTRATO,getAdministratorById);
    }

    @Test
    void createAdministratorTest(){
        Administrato administratorToSave = new Administrato("John Doe", "john@example.com", "password", null);
        Administrato savedAdministrator = new Administrato("John Doe", "john@example.com", "password", null);
        when(administratoRepository.save(administratorToSave)).thenReturn(savedAdministrator);
        Administrato createdAdministrator = administratoService.createAdministrator(administratorToSave);
        assertEquals(savedAdministrator, createdAdministrator);
    }
    @Test
    void updateAdministratorTest() {
        Administrato existingAdministrator = new Administrato("John Doe", "john@example.com", "password", null);
        Administrato updatedAdministrator = new Administrato("Updated Name", "updated@example.com", "updatedPassword", null);
        when(administratoRepository.findById(1L)).thenReturn(Optional.of(existingAdministrator));
        when(administratoRepository.save(updatedAdministrator)).thenReturn(updatedAdministrator);
        Administrato updateAdministrator = administratoService.updateAdministrator(1L, updatedAdministrator);
        assertEquals(updatedAdministrator, updateAdministrator);
    }

    @Test
    void findAdministratoByNameTest() {
        String name = "John Doe";
        when(administratoRepository.findByEmailContainingIgnoreCase(name)).thenReturn(Optional.of(Constants.ADMINISTRATO));
        Optional<Administrato> findAdministratoByName = administratoService.findAdministratoByName(name);
        assertTrue(findAdministratoByName.isPresent());
        assertEquals(name, findAdministratoByName.get().getName());
    }

    @Test
   void deleteAdministratorTest() {
        administratoService.deleteAdministrator(1L);
        verify(administratoRepository).deleteById(1L);
    }

    //Department
    @Test
   void getAllDepartmentsTest() {
        List<Department> departmentsList = Arrays.asList(Constants.DEPARTMENT, Constants.DEPARTMENT2);
        when(departmentRepository.findAll()).thenReturn(departmentsList);
        List<Department> getAllDepartments = administratoService.getAllDepartments();
        assertEquals(departmentsList.size(), getAllDepartments.size());
        assertTrue(getAllDepartments.containsAll(departmentsList));
    }

}
