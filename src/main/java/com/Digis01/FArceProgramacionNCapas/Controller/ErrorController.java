package com.Digis01.FArceProgramacionNCapas.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/access-denied")
    public String accessDenied(HttpServletRequest request, Model model) {
        String referer = request.getHeader("Referer");

        // Obtener el rol del usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rol = "Invitado"; // Valor por defecto

        if (auth != null && auth.isAuthenticated() && !auth.getAuthorities().isEmpty()) {
            rol = auth.getAuthorities().iterator().next().getAuthority(); // Ej: "ROLE_ANALISTA"
        }

        // Personalizar mensaje
        String mensaje = switch (rol) {
            case "ROLE_ADMINISTRADOR" -> "Eres administrador, pero no tienes permiso para esta sección específica.";
            case "ROLE_ANALISTA" -> "Eres analista, pero esta sección requiere permisos especiales.";
            case "ROLE_PROGRAMADOR" -> "Eres programador, pero parece que algo salió mal.";
            default -> "No tienes permisos para acceder a esta página.";
        };

        model.addAttribute("mensaje", mensaje);
        model.addAttribute("rol", rol);
        model.addAttribute("rutaIntentada", referer != null ? referer : "desconocida");

        return "access-denied";
    }
}