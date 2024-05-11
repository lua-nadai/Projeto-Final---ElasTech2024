package com.soulcode.projetofinal.controllers;

import com.soulcode.projetofinal.models.SupportRequest;
import com.soulcode.projetofinal.models.Department;
import com.soulcode.projetofinal.models.Person;
import com.soulcode.projetofinal.models.Status;
import com.soulcode.projetofinal.repositories.SupportRequestRepository;
import com.soulcode.projetofinal.repositories.PersonRepository;
import com.soulcode.projetofinal.services.SupportRequestService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private final SupportRequestRepository supportRequestRepository;

    @Autowired
    SupportRequestService supportRequestService;

    @Autowired
    PersonRepository personRepository;

    SupportRequest request = new SupportRequest();
    Department department = new Department();
    Status initialStatus = new Status();

    public UserController(SupportRequestRepository supportRequestRepository) {
        this.supportRequestRepository = supportRequestRepository;
    }

    @GetMapping("/login-user")
    public String userLoginPage() {
        return "login-user";
    }

    @GetMapping("/open-request")
    public String openRequestPage() {
        return "open-request";
    }

    @GetMapping("/register-user")
    public String createUser() {
        return "register-user";
    }

    @GetMapping("/user-request-details/{Id}")
    public String userRequestDetails(@PathVariable("Id") int id, Model model, HttpSession session) {
        SupportRequest request = supportRequestService.getRequestById(id);
        Person loggedUser = (Person) session.getAttribute("loggedUser");
        model.addAttribute("request", request);
        model.addAttribute("name", loggedUser.getName());

        return "user-request-details";
    }

    @GetMapping("/user-request-details")
    public String getRequestDetailsPage(@RequestParam("Id") int id, Model model, HttpSession session) {
        SupportRequest request = supportRequestService.getRequestById(id);
        Person loggedUser = (Person) session.getAttribute("loggedInUser");
        model.addAttribute("request", request);
        model.addAttribute("name", loggedUser.getName());
        return "user-request-details";
    }

    @RequestMapping(value = "/user-request-details", method = RequestMethod.POST)
    public String saveRequest(@RequestParam("priority") int priority,
                              @RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @RequestParam("department") Department department,
                              HttpSession session) {

        Person loggedUser = (Person) session.getAttribute("loggedUser");

        request = new SupportRequest();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime currentDateTime = LocalDateTime.now();
        String formattedDate = currentDateTime.format(formatter);
        LocalDateTime convertedDate = LocalDateTime.parse(formattedDate, formatter);

        initialStatus.setId(1);

        request.setDescription(description);
        request.setTitle(title);
        request.setDepartment(department);
        request.setPriority(priority);
        request.setStartDate(convertedDate);
        request.setUser(loggedUser);
        request.setStatus(initialStatus);
        supportRequestRepository.save(request);

        return "redirect:/user-page?name=" + loggedUser.getName();
    }

    @GetMapping("/user-page")
    public String userPage(Model model, HttpSession httpSession) {

        Person loggedUser = (Person) httpSession.getAttribute("loggedUser");

        if (loggedUser==null){
            return "redirect:login";
        }

        List<SupportRequest> userRequests = supportRequestRepository.findByUserId(loggedUser.getId());

        model.addAttribute("userRequests", userRequests);
        model.addAttribute("name", loggedUser.getName());

        return "user-page";
    }
}

