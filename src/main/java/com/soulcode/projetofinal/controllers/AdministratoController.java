package com.soulcode.projetofinal.controllers;

import com.soulcode.projetofinal.models.Administrato;
import com.soulcode.projetofinal.models.Department;
import com.soulcode.projetofinal.models.SupportRequest;
import com.soulcode.projetofinal.services.AdministratoService;
import com.soulcode.projetofinal.services.SupportRequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdministratoController {

    @Autowired
    private AdministratoService administratoService;

    @Autowired
    private TechnicianController technicianController;

    @Autowired
    private UserController userController;

    @Autowired
    private SupportRequestService supportRequestService;


    @GetMapping("/{id}")
    public Administrato getAdministratorById(@PathVariable Long id) {
        return administratoService.getAdministratorById(id);
    }

    @GetMapping("/searchByName")
    public ResponseEntity<Administrato> getAdministratoByName(@RequestParam String name) {
        Optional<Administrato> administrato = administratoService.findAdministratoByName(name);
        if (administrato.isPresent()) {
            return ResponseEntity.ok(administrato.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Administrato createAdministrator(@RequestBody Administrato administrator) {
        return administratoService.createAdministrator(administrator);
    }

    @PutMapping("/{id}")
    public Administrato updateAdministrator(@PathVariable Long id, @RequestBody Administrato administrator) {
        return administratoService.updateAdministrator(id, administrator);
    }

    @DeleteMapping("/{id}")
    public void deleteAdministrator(@PathVariable Long id) {
        administratoService.deleteAdministrator(id);
    }

    @GetMapping("/technician-page")
    public String technicianPage(@RequestParam(required = false) String name, Model model, HttpServletRequest request) {
        return technicianController.technicianPage(name, model, request);
    }

    @GetMapping("/request-details/{id}")
    public String requestDetails(@PathVariable("id") int id, Model model) {
        return technicianController.requestDetails(id, model);
    }

    @PostMapping("/change-status")
    public String changeRequestStatus(@RequestParam int id, @RequestParam int status, HttpSession session) {
        return technicianController.changeRequestStatus(id, status, session);
    }

    @GetMapping("/login-user")
    public String userLoginPage() {
        return userController.userLoginPage();
    }

    @GetMapping("/open-request")
    public String openRequestPage() {
        return userController.openRequestPage();
    }

    @GetMapping("/register-user")
    public String createUser() {
        return userController.createUser();
    }

    @GetMapping("/user-request-details/{Id}")
    public String userRequestDetails(@PathVariable("Id") int id, Model model, HttpSession session) {
        return userController.userRequestDetails(id, model, session);
    }

    @PostMapping("/user-request-details")
    public String saveRequest(@RequestParam("priority") int priority,
                              @RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @RequestParam("department") Department department,
                              HttpSession session) {
        return userController.saveRequest(priority, title, description, department, session);
    }

    @GetMapping("/user-page")
    public String userPage(Model model, HttpSession httpSession) {
        return userController.userPage(model, httpSession);
    }

    @PostMapping("/add-department")
    public String addDepartment(@RequestParam String departmentName) {
        return "redirect:/admin/dashboard";
    }


    @GetMapping("/dashboard")
    public String administratorDashboard(Model model) {
        String openRequestsCount = String.valueOf(administratoService.getOpenRequestsCount());
        String inProgressRequestsCount = String.valueOf(administratoService.getInProgressRequestsCount());
        String anotherDepartRequestsCount = String.valueOf(administratoService.getAnotherDepartmentRequestsCount());
        String completedRequestsCount = String.valueOf(administratoService.getCompletedRequestsCount());

        model.addAttribute("openRequestsCount", openRequestsCount);
        model.addAttribute("inProgressRequestsCount", inProgressRequestsCount);
        model.addAttribute("anotherDepartRequestsCount", anotherDepartRequestsCount);
        model.addAttribute("completedRequestsCount", completedRequestsCount);

        return "admin-dashboard";
    }


    @GetMapping("/admin-page")
    public String adminPage(@RequestParam(required = false) String name, Model model, HttpServletRequest request) {
        List<SupportRequest> availableRequests = supportRequestService.findAvaibleRequests();
        List<SupportRequest> requestsInProgess = supportRequestService.findRequestsInProgress();

        model.addAttribute("availableRequests", availableRequests);
        model.addAttribute("requestsInProgess", requestsInProgess);
        model.addAttribute("name", name);

        return "admin-page";
    }

    public List<SupportRequest> findRequestsInProgress(){
        List<SupportRequest> requestsInProgress = supportRequestService.findRequestsInProgress();
        return requestsInProgress;
    }

}