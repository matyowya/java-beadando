package org.example.javabeadando.config;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MenuModelAdvice {

    @ModelAttribute("loggedIn")
    public boolean loggedIn(Authentication authentication) {
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin(Authentication authentication) {
        return loggedIn(authentication) && authentication.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
    }

    @ModelAttribute("currentUser")
    public String currentUser(Authentication authentication) {
        return loggedIn(authentication) ? authentication.getName() : null;
    }
}
