package com.soulcode.projetofinal.services;

import com.soulcode.projetofinal.models.Person;
import com.soulcode.projetofinal.models.Type;
import com.soulcode.projetofinal.repositories.PersonRepository;
import com.soulcode.projetofinal.repositories.StatusRepository;
import com.soulcode.projetofinal.repositories.SupportRequestRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService {

    @Autowired
    private SupportRequestRepository supportRequestRepository;

    @Autowired
    private PersonRepository personRepository;

    public void registerNewUser(String name, String email, String password, int typeId) {
        Person user = new Person();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        Type type = new Type();
        type.setId(typeId);
        user.setType(type);

        personRepository.save(user);
    }

    public boolean checkIfEmailExists(String email) {
        Person existingUser = personRepository.findByEmail(email);
        return existingUser != null;
    }

    public boolean confirmPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public void resetPassword(String email, String newPassword) {
        // Verificar se o e-mail fornecido está associado a um usuário existente
        Person user = personRepository.findByEmail(email);
        if (user != null) {
            // Atualizar a senha do usuário
            user.setPassword(newPassword); // Aqui você deve garantir que a senha seja armazenada de forma segura, como um hash
            personRepository.save(user);
        }
    }

//    public void requestPasswordReset(String email, HttpServletRequest request) {
//        Person user = personRepository.findByEmail(email);
//        if (user != null) {
//            String resetToken = generateResetToken();
//            user.setResetToken(resetToken);
//            personRepository.save(user);
//            String resetLink = request.getRequestURL().toString().replace("reset-password", "password-reset") + "?token=" + resetToken;
//        }
//    }
//
//    public boolean resetPassword(String token, String newPassword) {
//        Person user = personRepository.findByResetToken(token);
//        if (user != null) {
//            user.setPassword(newPassword);
//            user.setResetToken(null);
//            personRepository.save(user);
//            return true;
//        }
//        return false;
//    }

//    private String generateResetToken() {
//        return UUID.randomUUID().toString();
//    }
//
//    public boolean isResetTokenValid(String token) {
//        Person user = personRepository.findByResetToken(token);
//        return user != null;
//    }

}
