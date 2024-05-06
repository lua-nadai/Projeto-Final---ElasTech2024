package com.soulcode.projetofinal.services;

import com.soulcode.projetofinal.models.Person;
import com.soulcode.projetofinal.models.Type;
import com.soulcode.projetofinal.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

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
}

