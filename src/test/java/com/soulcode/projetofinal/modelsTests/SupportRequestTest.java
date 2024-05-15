package com.soulcode.projetofinal.modelsTests;

import com.soulcode.projetofinal.models.Department;
import com.soulcode.projetofinal.models.Person;
import com.soulcode.projetofinal.models.Status;
import com.soulcode.projetofinal.models.SupportRequest;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;

public class SupportRequestTest {

    @Test
    public void testSupportRequestFields() {
        int id = 1;
        String title = "Test Support Request";
        String description = "Test Description";
        int priority = 1;
        LocalDateTime startDate = LocalDateTime.of(2022, 1, 1, 0, 0); // Data fictícia para o teste
        Department department = new Department();
        Status status = new Status();
        Person technician = new Person();
        Person user = new Person();

        SupportRequest supportRequest = new SupportRequest();
        supportRequest.setId(id);
        supportRequest.setTitle(title);
        supportRequest.setDescription(description);
        supportRequest.setPriority(priority);
        supportRequest.setStartDate(startDate);
        supportRequest.setDepartment(department);
        supportRequest.setStatus(status);
        supportRequest.setTechnician(technician);
        supportRequest.setUser(user);

        assertEquals(id, supportRequest.getId());
        assertEquals(title, supportRequest.getTitle());
        assertEquals(description, supportRequest.getDescription());
        assertEquals(priority, supportRequest.getPriority());
        assertEquals(department, supportRequest.getDepartment());
        assertEquals(status, supportRequest.getStatus());
        assertEquals(technician, supportRequest.getTechnician());
        assertEquals(user, supportRequest.getUser());

        user.setName("Test User");
        assertEquals("Test User", supportRequest.getRequesterName());

        supportRequest.setUser(null);
        assertNull(supportRequest.getRequesterName());
    }
}
