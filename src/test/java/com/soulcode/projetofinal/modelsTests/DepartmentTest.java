package com.soulcode.projetofinal.modelsTests;

import com.soulcode.projetofinal.models.Department;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DepartmentTest {

    @Test
    public void testDepartmentFields() {
        int id = 1;
        String name = "Test Department";

        Department department = new Department();
        department.setId(id);
        department.setName(name);

        assertEquals(id, department.getId());
        assertEquals(name, department.getName());

        department.setName("Updated Department Name");

        assertEquals("Updated Department Name", department.getName());
    }
}
