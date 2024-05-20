package com.soulcode.projetofinal.controllers;

import com.soulcode.projetofinal.models.Person;
import com.soulcode.projetofinal.repositories.PersonRepository;
import com.soulcode.projetofinal.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class AuthenticationController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    AuthenticationService authenticationService;



    @RequestMapping(value = "/register-user", method = RequestMethod.POST)
    public String save(@RequestParam String name, @RequestParam String email, @RequestParam String password, @RequestParam String confirmPassword, @RequestParam int typeId, Model model) {

        if (authenticationService.checkIfEmailExists(email)) {
            model.addAttribute("errorEmail", "Esse email já está em uso. Por favor escolha outro.");
            return "register-user";
        }

        if (!authenticationService.confirmPassword(password, confirmPassword)) {
            model.addAttribute("errorPassword", "As senhas não coincidem");
            return "register-user";
        }

        authenticationService.registerNewUser(name, email, password, typeId);

        return "redirect:/login-user";
    }

    @RequestMapping(value = "/login-user", method = RequestMethod.POST)
    public String login(@RequestParam String email, @RequestParam String password, Model model, HttpServletRequest request) {
        // Search in the database if the person already exists using the email
        Person user = personRepository.findByEmail(email);

        // Check if the user exists and if the password is correct
        if (user != null && user.getPassword().equals(password)) {
            int userType = user.getType().getId();

            // Create a session and set the user as logged in, storing the logged-in user's information in the cache
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", user);

            // Redirect to the appropriate page based on the user type (technician or client)
            if (userType == 1) {
                return "redirect:/admin/admin-page?name=" + user.getName();
            } if (userType == 2) {
                return "redirect:/technician-page?name=" + user.getName();
            } else {
                return "redirect:/user-page?name=" + user.getName();
            }
        } else if (user != null && user.getEmail().equals(email)) {
            model.addAttribute("error", "Usuário ou senha inválidos.");
            //redirectAttributes.addAttribute("error", true);
        } else {
            model.addAttribute("error", "Usuário ou senha inválidos.");
            //redirectAttributes.addAttribute("error", true);
        }

        return "login-user";
    }

    @GetMapping("/reset-password")
    public String showPasswordResetForm() {
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email, @RequestParam String newPassword, Model model) {
        Person user = personRepository.findByEmail(email);
        if (user != null) {
            authenticationService.resetPassword(email, newPassword);
            return "redirect:/login-user";
        } else {
            model.addAttribute("errorNotFoundUser", "O e-mail fornecido não está associado a nenhum usuário.");
            return "reset-password";
        }
       // authenticationService.resetPassword(email, newPassword);
       // return "redirect:/login-user";
    }

//    public void requestPasswordReset(String email, HttpServletRequest request) {
//        // Verificar se o e-mail fornecido está associado a um usuário existente
//        Person user = personRepository.findByEmail(email);
//        if (user != null) {
//            // Gerar um token de redefinição de senha único
//            String resetToken = UUID.randomUUID().toString();
//            // Armazenar o token de redefinição de senha no objeto usuário
//            user.setResetToken(resetToken);
//            personRepository.save(user);
//            // Enviar um e-mail com o link de redefinição de senha
//            String resetLink = request.getRequestURL().toString().replace("reset-password", "password-reset") + "?token=" + resetToken;
//            // Aqui você precisaria chamar um serviço de e-mail para enviar o e-mail
//            // Exemplo: emailService.sendResetPasswordEmail(user.getEmail(), resetLink);
//        }
//    }

//    @RequestMapping(value = "/password-reset", method = RequestMethod.GET)
//    public String showPasswordResetForm(@RequestParam String token, Model model) {
//        // Verificar se o token de redefinição de senha é válido
//        Person user = personRepository.findByResetToken(token);
//        if (user == null) {
//            // Redirecionar para uma página de erro ou página de token inválido
//            return "redirect:/password-reset-error";
//        }
//        // Passar o token para a página de redefinição de senha
//        model.addAttribute("token", token);
//        return "password-reset-form"; // Página onde os usuários podem redefinir suas senhas
//    }
//
//    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
//    public String resetPassword(@RequestParam String email, Model model, HttpServletRequest request) {
//        // Verificar se o e-mail fornecido está associado a um usuário existente
//        Person user = personRepository.findByEmail(email);
//        if (user != null) {
//            // Gerar um token de redefinição de senha único
//            String resetToken = UUID.randomUUID().toString();
//            // Armazenar o token de redefinição de senha no objeto usuário
//            user.setResetToken(resetToken);
//            personRepository.save(user);
//            // Enviar um e-mail com o link de redefinição de senha
//            String resetLink = request.getRequestURL().toString().replace("reset-password", "password-reset") + "?token=" + resetToken;
//            // Aqui você precisaria chamar um serviço de e-mail para enviar o e-mail
//            // Exemplo: emailService.sendResetPasswordEmail(user.getEmail(), resetLink);
//        }
//        // Redirecionar para uma página de confirmação de solicitação de redefinição de senha
//        return "redirect:/password-reset-request-sent";
//    }
}
