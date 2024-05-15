package com.soulcode.projetofinal.controllersTests;

import com.soulcode.projetofinal.controllers.AdministratoController;
import com.soulcode.projetofinal.controllers.TechnicianController;
import com.soulcode.projetofinal.controllers.UserController;
import com.soulcode.projetofinal.models.Administrato;
import com.soulcode.projetofinal.models.Department;
import com.soulcode.projetofinal.models.SupportRequest;
import com.soulcode.projetofinal.services.AdministratoService;
import com.soulcode.projetofinal.services.SupportRequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdministratoControllerTest {

    @Mock
    private AdministratoService administratoService;

    @Mock
    private TechnicianController technicianController;

    @Mock
    private UserController userController;

    @Mock
    SupportRequestService supportRequestService;

    @InjectMocks
    private AdministratoController administratoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAdministratorById() {
        Administrato administrato = new Administrato("John Doe", "john@example.com", "password", null);
        administrato.setId(1L);
        when(administratoService.getAdministratorById(1L)).thenReturn(administrato);

        Administrato result = administratoController.getAdministratorById(1L);

        assertEquals(1L, result.getId());
        verify(administratoService, times(1)).getAdministratorById(1L);
    }

    @Test
    void testGetAdministratoByName() {
        Administrato administrato = new Administrato("John Doe", "john@example.com", "password", null);
        when(administratoService.findAdministratoByName("John Doe")).thenReturn(Optional.of(administrato));

        ResponseEntity<Administrato> responseEntity = administratoController.getAdministratoByName("John Doe");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("John Doe", responseEntity.getBody().getName());
        verify(administratoService, times(1)).findAdministratoByName("John Doe");
    }

    @Test
    void testCreateAdministrator() {
        Administrato administrator = new Administrato("John Doe", "john@example.com", "password", null);
        when(administratoService.createAdministrator(administrator)).thenReturn(administrator);

        Administrato result = administratoController.createAdministrator(administrator);

        assertEquals("John Doe", result.getName());
        verify(administratoService, times(1)).createAdministrator(administrator);
    }

    @Test
    void testUpdateAdministrator() {
        Administrato administrator = new Administrato("John Doe", "john@example.com", "password", null);
        administrator.setId(1L);
        when(administratoService.updateAdministrator(1L, administrator)).thenReturn(administrator);

        Administrato result = administratoController.updateAdministrator(1L, administrator);

        assertEquals(1L, result.getId());
        verify(administratoService, times(1)).updateAdministrator(1L, administrator);
    }

    @Test
    void testDeleteAdministrator() {
        administratoController.deleteAdministrator(1L);

        verify(administratoService, times(1)).deleteAdministrator(1L);
    }

    @Test
    void testTechnicianPage() {
        String expectedView = "technician-page";
        String name = "John Doe";
        Model model = mock(Model.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(technicianController.technicianPage(name, model, request, session)).thenReturn(expectedView);

        String resultView = administratoController.technicianPage(name, model, request, session);

        assertEquals(expectedView, resultView);
        verify(technicianController, times(1)).technicianPage(name, model, request, session);
    }

    @Test
    void testRequestDetails() {
        String expectedView = "request-details";
        int id = 123;
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);

        when(technicianController.requestDetails(id, model, session)).thenReturn(expectedView);

        String resultView = administratoController.requestDetails(id, model, session);

        assertEquals(expectedView, resultView);
        verify(technicianController, times(1)).requestDetails(id, model, session);
    }

    @Test
    void testChangeRequestStatus() {
        String expectedView = "status-changed";
        int id = 123;
        int status = 1;
        HttpSession session = mock(HttpSession.class);

        when(technicianController.changeRequestStatus(id, status, session)).thenReturn(expectedView);

        String resultView = administratoController.changeRequestStatus(id, status, session);

        assertEquals(expectedView, resultView);
        verify(technicianController, times(1)).changeRequestStatus(id, status, session);
    }

    @Test
    void testUserLoginPage() {
        String expectedView = "user-login";

        when(userController.userLoginPage()).thenReturn(expectedView);

        String resultView = administratoController.userLoginPage();

        assertEquals(expectedView, resultView);
        verify(userController, times(1)).userLoginPage();
    }

    @Test
    void testOpenRequestPage() {
        String expectedView = "open-request";

        when(userController.openRequestPage()).thenReturn(expectedView);

        String resultView = administratoController.openRequestPage();

        assertEquals(expectedView, resultView);
        verify(userController, times(1)).openRequestPage();
    }

    @Test
    void testCreateUser() {
        String expectedView = "create-user";

        when(userController.createUser()).thenReturn(expectedView);

        String resultView = administratoController.createUser();

        assertEquals(expectedView, resultView);
        verify(userController, times(1)).createUser();
    }

    @Test
    void testUserRequestDetails() {
        String expectedView = "user-request-details";
        int id = 123;
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);

        when(userController.userRequestDetails(id, model, session)).thenReturn(expectedView);

        String resultView = administratoController.userRequestDetails(id, model, session);

        assertEquals(expectedView, resultView);
        verify(userController, times(1)).userRequestDetails(id, model, session);
    }

    @Test
    void testSaveRequest() {
        String expectedView = "save-request";
        int priority = 1;
        String title = "Test Title";
        String description = "Test Description";
        Department department = new Department();
        department.setId(1);
        department.setName("IT");
        HttpSession session = mock(HttpSession.class);

        when(userController.saveRequest(priority, title, description, department, session)).thenReturn(expectedView);

        String resultView = administratoController.saveRequest(priority, title, description, department, session);

        assertEquals(expectedView, resultView);
        verify(userController, times(1)).saveRequest(priority, title, description, department, session);
    }

    @Test
    void testUserPage() {
        String expectedView = "user-page";
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);

        when(userController.userPage(model, session)).thenReturn(expectedView);

        String resultView = administratoController.userPage(model, session);

        assertEquals(expectedView, resultView);
        verify(userController, times(1)).userPage(model, session);
    }

    @Test
    void testAdminDepartmentPage() {
        String expectedView = "admin-dpto";
        Model model = mock(Model.class);
        List<Department> departments = new ArrayList<>();
        Department itDepartment = new Department();
        itDepartment.setId(1);
        itDepartment.setName("IT");
        Department hrDepartment = new Department();
        hrDepartment.setId(2);
        hrDepartment.setName("HR");
        departments.add(itDepartment);
        departments.add(hrDepartment);

        when(administratoService.getAllDepartments()).thenReturn(departments);

        String resultView = administratoController.adminDepartmentPage(null, model);

        assertEquals(expectedView, resultView);
        verify(model, times(1)).addAttribute("departments", departments);
    }

    @Test
    void testAddDepartment() {
        String departmentName = "IT";

        String resultView = administratoController.addDepartment(departmentName);

        assertEquals("redirect:/admin/admin-dpto", resultView);
        verify(administratoService, times(1)).addDepartment(departmentName);
    }

    @Test
    void testGetAllDepartments() {
        String expectedView = "admin-dpto";
        Model model = mock(Model.class);
        List<Department> departments = new ArrayList<>();
        Department itDepartment = new Department();
        itDepartment.setId(1);
        itDepartment.setName("IT");
        Department hrDepartment = new Department();
        hrDepartment.setId(2);
        hrDepartment.setName("HR");
        departments.add(itDepartment);
        departments.add(hrDepartment);

        when(administratoService.getAllDepartments()).thenReturn(departments);

        String resultView = administratoController.getAllDepartments(model);

        assertEquals(expectedView, resultView);
        verify(model, times(1)).addAttribute("departments", departments);
    }

    @Test
    void testDeleteDepartmentAndTickets() {
        int departmentId = 1;

        String resultView = administratoController.deleteDepartmentAndTickets(departmentId);

        assertEquals("redirect:/admin/departments", resultView);
        verify(administratoService, times(1)).deleteDepartmentAndTickets(departmentId);
    }

    @Test
    void testAdministratorDashboard() {
        String expectedView = "admin-dashboard";
        Model model = mock(Model.class);
        List<Department> departments = new ArrayList<>();
        departments.add(new Department());

        when(administratoService.getAllDepartments()).thenReturn(departments);
        when(administratoService.getOpenRequestsCount()).thenReturn(5);
        when(administratoService.getInProgressRequestsCount()).thenReturn(10);
        when(administratoService.getAnotherDepartmentRequestsCount()).thenReturn(15);
        when(administratoService.getCompletedRequestsCount()).thenReturn(20);

        String resultView = administratoController.administratorDashboard(model);

        assertEquals(expectedView, resultView);
        verify(model, times(1)).addAttribute("departments", departments);
        verify(model, times(1)).addAttribute("openRequestsCount", "5");
        verify(model, times(1)).addAttribute("inProgressRequestsCount", "10");
        verify(model, times(1)).addAttribute("anotherDepartRequestsCount", "15");
        verify(model, times(1)).addAttribute("completedRequestsCount", "20");
    }

    @Test
    void testAdminPage() {
        String expectedView = "admin-page";
        Model model = mock(Model.class);
        List<SupportRequest> availableRequests = new ArrayList<>();
        List<SupportRequest> requestsInProgress = new ArrayList<>();
        String name = "Admin";

        when(supportRequestService.findAvaibleRequests()).thenReturn(availableRequests);
        when(supportRequestService.findRequestsInProgress()).thenReturn(requestsInProgress);

        String resultView = administratoController.adminPage(name, model, null);

        assertEquals(expectedView, resultView);
        verify(model, times(1)).addAttribute("availableRequests", availableRequests);
        verify(model, times(1)).addAttribute("requestsInProgess", requestsInProgress);
        verify(model, times(1)).addAttribute("name", name);
    }

    @Test
    void testFindRequestsInProgress() {
        List<SupportRequest> expectedRequestsInProgress = new ArrayList<>();

        when(supportRequestService.findRequestsInProgress()).thenReturn(expectedRequestsInProgress);

        List<SupportRequest> resultRequestsInProgress = administratoController.findRequestsInProgress();

        assertEquals(expectedRequestsInProgress, resultRequestsInProgress);
        verify(supportRequestService, times(1)).findRequestsInProgress();
    }
}
