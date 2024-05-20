package com.soulcode.projetofinal.modelsTests;

import com.soulcode.projetofinal.models.Type;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TypeTest {

    @Test
    public void testTypeFields() {
        // Mock data
        int id = 1;
        String name = "Test Type";

        // Create Type instance
        Type type = new Type();
        type.setId(id);
        type.setName(name);

        // Test fields
        assertEquals(id, type.getId());
        assertEquals(name, type.getName());

        // Update fields
        type.setName("Updated Type Name");

        // Test updated fields
        assertEquals("Updated Type Name", type.getName());
    }
}

