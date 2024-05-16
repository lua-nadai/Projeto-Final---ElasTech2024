package com.soulcode.projetofinal.modelsTests;

import com.soulcode.projetofinal.models.Status;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatusTest {

    @Test
    public void testStatusFields() {
        int id = 1;
        String name = "Test Status";

        Status status = new Status();
        status.setId(id);
        status.setName(name);

        assertEquals(id, status.getId());
        assertEquals(name, status.getName());

        status.setName("Updated Status Name");

        assertEquals("Updated Status Name", status.getName());
    }
}

