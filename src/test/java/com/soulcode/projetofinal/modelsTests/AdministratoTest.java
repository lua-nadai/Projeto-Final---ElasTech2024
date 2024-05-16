package com.soulcode.projetofinal.modelsTests;

import com.soulcode.projetofinal.models.Administrato;
import com.soulcode.projetofinal.models.SupportRequest;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AdministratoTest {

    @Test
    public void testAdministratoFields() {
        Long id = 1L;
        String name = "Test Admin";
        String email = "test@admin.com";
        String password = "password123";
        Set<SupportRequest> requests = new HashSet<>();

        Administrato administrato = new Administrato(name, email, password, requests);

        assertEquals(name, administrato.getName());
        assertEquals(email, administrato.getEmail());
        assertEquals(password, administrato.getPassword());
        assertEquals(requests, administrato.getRequests());

        administrato.setName("Updated Admin Name");
        administrato.setEmail("updated@admin.com");
        administrato.setPassword("newpassword456");
        Set<SupportRequest> newRequests = new HashSet<>();
        newRequests.add(new SupportRequest());
        administrato.setRequests(newRequests);

        assertEquals("Updated Admin Name", administrato.getName());
        assertEquals("updated@admin.com", administrato.getEmail());
        assertEquals("newpassword456", administrato.getPassword());
        assertEquals(newRequests, administrato.getRequests());
    }
}