package com.soulcode.projetofinal.services;

import com.soulcode.projetofinal.controllers.TechnicianController;
import com.soulcode.projetofinal.controllers.UserController;
import com.soulcode.projetofinal.models.Administrato;
import com.soulcode.projetofinal.models.Department;
import com.soulcode.projetofinal.repositories.AdministratoRepository;
import com.soulcode.projetofinal.repositories.DepartmentRepository;
import com.soulcode.projetofinal.repositories.PersonRepository;
import com.soulcode.projetofinal.repositories.TypeRepository;
import org.apache.tomcat.util.http.parser.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratoService {

    @Autowired
    private AdministratoRepository administratoRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SupportRequestService supportRequestService;

    public List<Administrato> getAllAdministrators() {
        return administratoRepository.findAll();
    }

    public Administrato getAdministratorById(Long id) {
        Optional<Administrato> administrator = administratoRepository.findById(id);
        return administrator.orElse(null); // or throw an exception if not found
    }

    public Administrato createAdministrator(Administrato administrator) {
        return administratoRepository.save(administrator);
    }

    public Administrato updateAdministrator(Long id, Administrato updatedAdministrator) {
        Optional<Administrato> administratorOptional = administratoRepository.findById(id);
        if (administratorOptional.isPresent()) {
            updatedAdministrator.setId(id);
            return administratoRepository.save(updatedAdministrator);
        } else {
            return null; // or throw an exception if not found
        }
    }

    public Optional<Administrato> findAdministratoByName(String name) {
        return administratoRepository.findByEmailContainingIgnoreCase(name);
    }

    public void deleteAdministrator(Long id) {
        administratoRepository.deleteById(id);
    }

    public void addDepartment(String departmentName) {
        Department department = new Department();
        department.setName(departmentName);
        departmentRepository.save(department);

    }

    public int getOpenRequestsCount() {
        return authenticationService.getOpenRequestsCount();
    }

    public int getInProgressRequestsCount() {
        return authenticationService.getInProgressRequestsCount();
    }

    public int getWaitingRequestsCount() {
        return authenticationService.getWaitingRequestsCount();
    }
}