package com.soulcode.projetofinal.controllers;

import com.soulcode.projetofinal.models.SupportRequest;
import com.soulcode.projetofinal.models.Person;
import com.soulcode.projetofinal.models.Status;
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

    private static boolean requestsWereRegistered = false;

    @GetMapping("/technician-page")
    public String technicianPage(@RequestParam(required = false) String name, Model model, HttpServletRequest request) {
        if (!requestsWereRegistered) {
            supportRequestService.registerFakeRequests(request);
            requestsWereRegistered = true;
        }

        List<SupportRequest> availableRequests = new ArrayList<>();
        List<SupportRequest> requestsInProcess = new ArrayList<>();
        List<SupportRequest> allRequests = getFakeRequestsFromDatabase();

        for (SupportRequest supportRequest : allRequests) {
            int statusId = supportRequest.getStatus().getId();
            (statusId == 1 ? availableRequests : requestsInProcess).add(supportRequest);
        }

        List<SupportRequest> requestsFromDatabaseWithInitialStatus = supportRequestService.getRequestsWithStatus(1);

        requestsFromDatabaseWithInitialStatus = requestsFromDatabaseWithInitialStatus.stream()
                .filter(request1 -> !availableRequests.contains(request)).toList();

        availableRequests.addAll(requestsFromDatabaseWithInitialStatus);

        model.addAttribute("availableRequests", availableRequests);
        model.addAttribute("requestsInProcess", requestsInProcess);
        model.addAttribute("name", name);

        return "technician-page";
    }

    @GetMapping("/request-details/{id}")
    public String requestDetails(@PathVariable("id") int id, Model model) {
        SupportRequest request = supportRequestService.getRequestById(id);

        model.addAttribute("request", request);
        model.addAttribute("department", request.getDepartment().toString());

        return "request-details";
    }

    @RequestMapping(value = "/change-status", method = RequestMethod.POST)
    public String changeRequestStatus(@RequestParam int id, @RequestParam int status, HttpSession session) {

        SupportRequest request = supportRequestService.getRequestById(id);
        Person technicianLogged = (Person) session.getAttribute("loggedInUser");
        session.setAttribute("technicianLoggedName", technicianLogged.getName());

        Status updatedStatus = null;

        switch (status) {
            case 1 -> updatedStatus = statusRepository.findById(2).orElse(null); // In database, id 2 = In progress
            case 2 -> updatedStatus = statusRepository.findById(3).orElse(null); // In database, id 3 = Escalated to another department
            case 3 -> updatedStatus = statusRepository.findById(4).orElse(null); // In database, id 4 = Completed
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

