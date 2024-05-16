package com.soulcode.projetofinal.controllersTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.soulcode.projetofinal.controllers.AuthenticationController;
import com.soulcode.projetofinal.controllers.TechnicianController;
import com.soulcode.projetofinal.models.Person;
import com.soulcode.projetofinal.repositories.PersonRepository;
import com.soulcode.projetofinal.services.AuthenticationService;
import com.soulcode.projetofinal.services.SupportRequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

class AuthenticationControllerTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private SupportRequestService supportRequestService;

    @Mock
    private TechnicianController technicianController;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Model model = mock(Model.class);
        String name = "John Doe";
        String email = "john@example.com";
        String password = "password";
        String confirmPassword = "password";
        int typeId = 1;

        when(authenticationService.checkIfEmailExists(email)).thenReturn(false);
        when(authenticationService.confirmPassword(password, confirmPassword)).thenReturn(true);

        String resultView = authenticationController.save(name, email, password, confirmPassword, typeId, model);

        verify(authenticationService, times(1)).registerNewUser(name, email, password, typeId);
        assertEquals("redirect:/login-user", resultView);
    }

    @Test
    void testLogin() {
        Model model = mock(Model.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        String email = "john@example.com";
        String password = "password";
        Person user = new Person();
        user.setEmail(email);
        user.setPassword(password);

        when(personRepository.findByEmail(email)).thenReturn(null);

        String resultView = authenticationController.login(email, password, model, request);

        verify(session, never()).setAttribute("loggedUser", user);

        assertEquals("login-user", resultView);
    }

    @Test
    void testShowPasswordResetForm() {
        String expectedView = "reset-password";

        String resultView = authenticationController.showPasswordResetForm();

        assertEquals(expectedView, resultView);
    }

}