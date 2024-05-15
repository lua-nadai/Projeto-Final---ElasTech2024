package com.soulcode.projetofinal.controllers;

import com.soulcode.projetofinal.models.SupportRequest;
import com.soulcode.projetofinal.models.Person;
import com.soulcode.projetofinal.models.Status;
import com.soulcode.projetofinal.repositories.PersonRepository;
import com.soulcode.projetofinal.repositories.SupportRequestRepository;
import com.soulcode.projetofinal.repositories.StatusRepository;
import com.soulcode.projetofinal.services.SupportRequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TechnicianController {

    @Autowired
    SupportRequestRepository supportRequestRepository;

    @Autowired
    SupportRequestService supportRequestService;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    PersonRepository personRepository;

    private static boolean requestsWereRegistered = false;

    @GetMapping("/technician-page")
    public String technicianPage(@RequestParam(required=false)String name,Model model,HttpServletRequest request,HttpSession httpSession) {
        Person loggedUser = (Person) httpSession.getAttribute("loggedUser");

        if (loggedUser==null){
            return "redirect:login";
        }

        List<SupportRequest> availableRequests = supportRequestService.findAvaibleRequests();
        List<SupportRequest> technicianRequests = supportRequestService.findRequestsInProgressByTech(loggedUser.getId());


        model.addAttribute("availableRequests", availableRequests);
       /* model.addAttribute("requestsInProcess", requestsInProcess);*/
        model.addAttribute("technicianRequests", technicianRequests);
        model.addAttribute("name", loggedUser.getName());

        return "technician-page";
    }

    @GetMapping("/request-details/{id}")
    public String requestDetails(@PathVariable("id") int id, Model model, HttpSession session) {
        SupportRequest request = supportRequestService.getRequestById(id);

        Person loggedUser = (Person) session.getAttribute("loggedUser");

        model.addAttribute("request", request);
        model.addAttribute("department", request.getDepartment().toString());

        return "request-details";
    }

    @RequestMapping(value = "/change-status", method = RequestMethod.POST)
    public String changeRequestStatus(@RequestParam int id, @RequestParam int status, HttpSession session) {

        SupportRequest request = supportRequestService.getRequestById(id);
        Person technicianLogged = (Person) session.getAttribute("loggedUser");
        session.setAttribute("technicianLoggedName", technicianLogged.getName());

        Status updatedStatus = null;

        switch (status) {
            case 1 -> updatedStatus = statusRepository.findById(2).orElse(null); // id 2 = Em atendimento
            case 2 -> updatedStatus = statusRepository.findById(3).orElse(null); // id 3 = Escalado para outro setor
            case 3 -> updatedStatus = statusRepository.findById(4).orElse(null); // id 4 = na base de dados o id 4 = Finalizado
            default -> { return "redirect:/technician-page?name=" + technicianLogged.getName();}
        }

        if (updatedStatus != null) {
            request.setStatus(updatedStatus);
            request.setTechnician(technicianLogged);
            supportRequestService.saveRequest(request);

            return "redirect:/technician-page?name=" + technicianLogged.getName();
        } else {
            return "redirect:/technician-page";
        }
    }

    public List<SupportRequest> getFakeRequestsFromDatabase() {
        return supportRequestRepository.findAll();
    }
}

