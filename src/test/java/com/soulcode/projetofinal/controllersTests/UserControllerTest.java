package com.soulcode.projetofinal.controllersTests;

import com.soulcode.projetofinal.controllers.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {
    private UserController userController;

    @BeforeEach
    public void setUp() {
        userController = new UserController(null); // Passa null porque o repositório não é necessário para esses testes
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
}