package com.soulcode.projetofinal.controllersTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.soulcode.projetofinal.controllers.TechnicianController;
import com.soulcode.projetofinal.models.Department;
import com.soulcode.projetofinal.models.Person;
import com.soulcode.projetofinal.models.Status;
import com.soulcode.projetofinal.models.SupportRequest;
import com.soulcode.projetofinal.repositories.StatusRepository;
import com.soulcode.projetofinal.services.SupportRequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;

class TechnicianControllerTest {

    @Mock
    private SupportRequestService supportRequestService;

    @Mock
    StatusRepository statusRepository;

    @InjectMocks
    private TechnicianController technicianController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTechnicianPage() {
        Model model = mock(Model.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        Person loggedUser = new Person();
        loggedUser.setId(1);
        loggedUser.setName("John Doe");
        when(session.getAttribute("loggedUser")).thenReturn(loggedUser);

        List<SupportRequest> availableRequests = new ArrayList<>();
        List<SupportRequest> technicianRequests = new ArrayList<>();
        when(supportRequestService.findAvaibleRequests()).thenReturn(availableRequests);
        when(supportRequestService.findRequestsInProgressByTech(loggedUser.getId())).thenReturn(technicianRequests);

        String resultView = technicianController.technicianPage(null, model, request, session);

        assertEquals("technician-page", resultView);
        verify(model, times(1)).addAttribute("availableRequests", availableRequests);
        verify(model, times(1)).addAttribute("technicianRequests", technicianRequests);
        verify(model, times(1)).addAttribute("name", loggedUser.getName());
    }

    @Test
    void testRequestDetails() {
        int requestId = 1;
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);

        SupportRequest request = new SupportRequest();
        Department department = new Department();
        department.setName("IT");
        request.setId(requestId);
        request.setDepartment(department);
        when(supportRequestService.getRequestById(requestId)).thenReturn(request);

        Person loggedUser = new Person();
        when(session.getAttribute("loggedUser")).thenReturn(loggedUser);

        String resultView = technicianController.requestDetails(requestId, model, session);

        assertEquals("request-details", resultView);
        verify(model, times(1)).addAttribute("request", request);
        verify(model, times(1)).addAttribute("department", department.toString());
    }

    @Test
    public void testChangeRequestStatus_Successful() {
        SupportRequest mockRequest = new SupportRequest();
        mockRequest.setId(1);

        Status mockStatus = new Status();
        mockStatus.setId(2);

        Person mockTechnician = new Person();
        mockTechnician.setName("Mock Technician");

        HttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("loggedUser", mockTechnician);

        when(supportRequestService.getRequestById(anyInt())).thenReturn(mockRequest);
        when(statusRepository.findById(eq(2))).thenReturn(Optional.of(mockStatus));

        String result = technicianController.changeRequestStatus(1, 1, mockSession);

        verify(supportRequestService, times(1)).saveRequest(mockRequest);

        assertTrue(result.startsWith("redirect:/technician-page?name=" + mockTechnician.getName()));
    }

    @Test
    public void testChangeRequestStatus_InvalidStatus() {
        Person mockTechnician = new Person();
        mockTechnician.setName("Mock Technician");

        HttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("loggedUser", mockTechnician);

        String result = technicianController.changeRequestStatus(1, 5, mockSession);

        verify(supportRequestService, never()).saveRequest(any());

        verifyNoInteractions(statusRepository);

        assertTrue(result.startsWith("redirect:/technician-page?name=" + mockTechnician.getName()));
    }
}
