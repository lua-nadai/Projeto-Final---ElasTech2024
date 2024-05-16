package com.soulcode.projetofinal.servicesTests;

import com.soulcode.projetofinal.models.Person;
import com.soulcode.projetofinal.repositories.PersonRepository;
import com.soulcode.projetofinal.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private PersonRepository personRepository;

    @Test
    void checkIfEmailExistsTest() {
        String existingEmail = "existing@example.com";
        when(personRepository.findByEmail(existingEmail)).thenReturn(new Person());
        assertTrue(authenticationService.checkIfEmailExists(existingEmail));
        String nonExistingEmail = "nonexisting@example.com";
        when(personRepository.findByEmail(nonExistingEmail)).thenReturn(null);
        assertFalse(authenticationService.checkIfEmailExists((nonExistingEmail)));
    }

    @Test
    void confirmPasswordTest() {
        String password = "password123";
        String confirmPassword = "password123";
        assertTrue(authenticationService.confirmPassword(password, confirmPassword));
        String differentPassword = "password456";
        assertFalse(authenticationService.confirmPassword(password, differentPassword));
    }

    @Test
    void resetPasswordTest() {
        String email = "user@example.com";
        String newPassword = "newPassword123";
        Person user = new Person();
        when(personRepository.findByEmail(email)).thenReturn(user);
        authenticationService.resetPassword(email, newPassword);
        verify(personRepository).findByEmail(email);
        assertEquals(newPassword, user.getPassword());
        verify(personRepository).save(user);
    }

    @Test
    void registerNewUserTest() {
        String name = "John Doe";
        String email = "john@example.com";
        String password = "password";
        int typeId = 1;

        authenticationService.registerNewUser(name, email, password, typeId);

        // Verifica se o método save do personRepository foi chamado com um objeto Person correto
        verify(personRepository).save(argThat(user ->
                user.getName().equals(name) &&
                        user.getEmail().equals(email) &&
                        user.getPassword().equals(password) &&
                        user.getType() != null && user.getType().getId() == typeId
        ));
    }
}
