package com.soulcode.projetofinal.controllersTests;

import com.soulcode.projetofinal.controllers.UserController;
import com.soulcode.projetofinal.models.Person;
import com.soulcode.projetofinal.models.SupportRequest;
import com.soulcode.projetofinal.repositories.SupportRequestRepository;
import com.soulcode.projetofinal.services.SupportRequestService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Mock
    private SupportRequestService supportRequestService;

    @Mock
    SupportRequestRepository supportRequestRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserLoginPage() {
        String result = userController.userLoginPage();
        assertEquals("login-user", result);
    }

    @Test
    public void testOpenRequestPage() {
        String result = userController.openRequestPage();
        assertEquals("open-request", result);
    }

    @Test
    public void testCreateUser() {
        String result = userController.createUser();
        assertEquals("register-user", result);
    }

    @Test
    public void testUserPage() {
        Person mockUser = new Person();
        mockUser.setId(1);
        mockUser.setName("Test User");

        List<SupportRequest> mockRequests = new ArrayList<>();
        SupportRequest mockRequest1 = new SupportRequest();
        mockRequest1.setId(1);
        mockRequest1.setTitle("Test Request 1");
        mockRequests.add(mockRequest1);
        SupportRequest mockRequest2 = new SupportRequest();
        mockRequest2.setId(2);
        mockRequest2.setTitle("Test Request 2");
        mockRequests.add(mockRequest2);

        when(session.getAttribute("loggedUser")).thenReturn(mockUser);
        when(supportRequestRepository.findByUserId(mockUser.getId())).thenReturn(mockRequests);

        String viewName = userController.userPage(model, session);

        verify(session, times(1)).getAttribute("loggedUser");
        verify(supportRequestRepository, times(1)).findByUserId(mockUser.getId());

        verify(model, times(1)).addAttribute("userRequests", mockRequests);
        verify(model, times(1)).addAttribute("name", mockUser.getName());

        assertEquals("user-page", viewName);
    }
}