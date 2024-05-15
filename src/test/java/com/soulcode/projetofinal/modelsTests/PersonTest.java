package com.soulcode.projetofinal.modelsTests;

import com.soulcode.projetofinal.models.Person;
import com.soulcode.projetofinal.models.Type;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    public void testPersonFields() {
        int id = 1;
        String name = "Test Person";
        String email = "test@example.com";
        String password = "password123";
        String resetToken = "abc123";
        Type type = new Type();

        Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setEmail(email);
        person.setPassword(password);
        person.setResetToken(resetToken);
        person.setType(type);

        assertEquals(id, person.getId());
        assertEquals(name, person.getName());
        assertEquals(email, person.getEmail());
        assertEquals(password, person.getPassword());
        assertEquals(resetToken, person.getResetToken());
        assertEquals(type, person.getType());

        person.setName("Updated Person Name");
        person.setEmail("updated@example.com");
        person.setPassword("newpassword456");
        person.setResetToken("def456");
        Type newType = new Type();
        person.setType(newType);

        assertEquals("Updated Person Name", person.getName());
        assertEquals("updated@example.com", person.getEmail());
        assertEquals("newpassword456", person.getPassword());
        assertEquals("def456", person.getResetToken());
        assertEquals(newType, person.getType());
    }
}
