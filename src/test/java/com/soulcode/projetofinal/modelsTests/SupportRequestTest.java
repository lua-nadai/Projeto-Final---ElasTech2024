package com.soulcode.projetofinal.modelsTests;

import com.soulcode.projetofinal.models.Person;
import com.soulcode.projetofinal.models.SupportRequest;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class SupportRequestTest {

    @Test
    void testGetRequesterName_UserNotNull_ReturnsUserName() {
        Person user = new Person();
        user.setName("John Doe");
        SupportRequest request = new SupportRequest();
        request.setUser(user);

        String requesterName = request.getRequesterName();

        assertEquals("John Doe", requesterName);
    }

    @Test
    void testGetRequesterName_UserNull_ReturnsNull() {
        SupportRequest request = new SupportRequest();

        String requesterName = request.getRequesterName();

        assertNull(requesterName);
    }

    @Test
    void testGetStartDate_ValidStartDate_ReturnsStartDate() {
        LocalDateTime startDate = LocalDateTime.now();
        SupportRequest request = new SupportRequest();
        request.setStartDate(startDate);

        LocalDateTime retrievedStartDate = request.getStartDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedStartDate = startDate.format(formatter);
        LocalDateTime convertedStartDate = LocalDateTime.parse(formattedStartDate, formatter);
        assertEquals(convertedStartDate, retrievedStartDate);
    }
}

