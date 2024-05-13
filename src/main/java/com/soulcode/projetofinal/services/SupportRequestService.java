package com.soulcode.projetofinal.services;

import com.soulcode.projetofinal.models.*;
import com.soulcode.projetofinal.repositories.SupportRequestRepository;
import com.soulcode.projetofinal.repositories.PersonRepository;
import com.soulcode.projetofinal.repositories.StatusRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupportRequestService {

    @Autowired
    private SupportRequestRepository supportRequestRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private StatusRepository statusRepository;

    public Person getLoggedTechnician(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Person) session.getAttribute("loggedUser");
    }

    public SupportRequest registerRequest(String title, String description, int priority, LocalDateTime startDate,
                                       Department department, Status status, Person technician, Person user) {
        SupportRequest request = new SupportRequest();
        request.setTitle(title);
        request.setDescription(description);
        request.setPriority(priority);
        request.setStartDate(startDate);
        request.setDepartment(department);
        request.setStatus(status);
        request.setTechnician(technician);
        request.setUser(user);

        return supportRequestRepository.save(request);
    }

    public void registerFakeRequests(HttpServletRequest request) {
        Person technicianLogged = getLoggedTechnician(request);

        Department adminDepartment = new Department();
        adminDepartment.setId(1);

        Department itDepartment = new Department();
        itDepartment.setId(6);

        Status awaitingStatus = new Status();
        awaitingStatus.setId(1);

        Type clientType = new Type();
        clientType.setId(3);

        Person user1 = personRepository.findByEmail("jonhlenon@user.com");
        if (user1 == null) {
            user1 = new Person();
            user1.setName("Jonh Lenon");
            user1.setEmail("jonhlenon@user.com");
            user1.setPassword("jonhlenon567");
            user1.setType(clientType);
            personRepository.save(user1);

            registerRequest("Problema no monitor", "O monitor não liga", 2, LocalDateTime.now(), adminDepartment, awaitingStatus, null, user1);
            registerRequest("Problema na impressora", "A impressora não está imprimindo", 1, LocalDateTime.now(), itDepartment, awaitingStatus, null, user1);
        }

        Person user2 = personRepository.findByEmail("marialemos@user.com");
        if (user2 == null) {
            user2 = new Person();
            user2.setName("Maria Lemos");
            user2.setEmail("marialemos@user.com");
            user2.setPassword("marialemos85412");
            user2.setType(clientType);
            personRepository.save(user2);

            registerRequest("Problema no teclado", "Teclado não funciona", 3, LocalDateTime.now(), adminDepartment, awaitingStatus, null, user2);
            registerRequest("Problema na conexão de rede", "A conexão caiu", 4, LocalDateTime.now(), itDepartment, awaitingStatus, null, user2);
            registerRequest("Problema no mouse", "O lado direito do mouse não funciona", 1, LocalDateTime.now(), itDepartment, awaitingStatus, null, user2);
        }
    }

    public SupportRequest getRequestById(int id) {
        Optional<SupportRequest> optionalSupportRequest = supportRequestRepository.findById(id);
        return optionalSupportRequest.orElse(null);
    }

    public void saveRequest(SupportRequest request) {
        supportRequestRepository.save(request);
    }

    public List<SupportRequest> getRequestsWithStatus(int status) {
        return supportRequestRepository.findByStatusId(status);
    }


    public List<SupportRequest> findAvaibleRequests(){
        List<SupportRequest> supportRequests = supportRequestRepository.findAll();
        List<SupportRequest> supportRequestsAvaible = new ArrayList<>();

        for (SupportRequest request : supportRequests) {
            if (request.getStatus().getName().equals("Aguardando Técnico")) {
                supportRequestsAvaible.add(request);
            }
        }
        return supportRequestsAvaible;

    }

    public List<SupportRequest> findRequestsInProgress(){
        List<SupportRequest> supportRequests = supportRequestRepository.findAll();
        List<SupportRequest> supportRequestsInProgress = new ArrayList<>();

        for (SupportRequest request : supportRequests) {
            if (!request.getStatus().getName().equals("Aguardando Técnico")) {
                supportRequestsInProgress.add(request);
            }
        }

        return supportRequestsInProgress;

    }

    public List<SupportRequest> findRequestsInProgressByTech(int techId){
        List<SupportRequest> supportRequestsInProgress = findRequestsInProgress();
        List<SupportRequest> supportRequestsInProgressByTech = new ArrayList<>();

        for (SupportRequest request : supportRequestsInProgress) {
            if (request.getTechnician() != null && request.getTechnician().getId() == techId) {
                supportRequestsInProgressByTech.add(request);
            }
        }
        return supportRequestsInProgressByTech;
    }
}

