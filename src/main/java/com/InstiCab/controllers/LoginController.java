package com.InstiCab.controllers;

import com.InstiCab.models.User;
import com.InstiCab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class LoginController extends BaseController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Resource(name="authenticationManager")
    private AuthenticationManager authManager;

    public LoginController(UserService userService) {
        super(userService);
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }


    @GetMapping("/login")
    public String loginGoto(Model model){
        if(isLoggedIn()){
            return "redirect:/";
        }
        model.addAttribute("user",new User());
        return "login";
    }

    @PostMapping("/login/")
    public String loginManager(@ModelAttribute("user") User user, Model model,
                                  RedirectAttributes redirectAttributes) {

//        System.out.println(user.getUsername());
//        System.out.println(user.getPassword());

        if (isLoggedIn()) {
            return "redirect:/";
        }

        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(user, user.getPassword());
        Authentication auth = authManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        return "redirect:/";
    }

    @GetMapping("/loggedin/")
    public String loginManager(Model model, RedirectAttributes redirectAttributes) {
        if (!isLoggedIn())
            return PAGE_NOT_FOUND_ERROR_PAGE;

        redirectAttributes.addFlashAttribute("successMsg", "Welcome!");
        return "redirect:/";
    }
    @GetMapping("/login-error/")
    public String loginError(Model model,RedirectAttributes redirectAttributes){
        System.out.println(model.getAttribute("username"));
        return "redirect:/loggedin/";
    }
    @GetMapping("/loggedout/")
    public String logoutManager(Model model, RedirectAttributes redirectAttributes) {
        if (isLoggedIn())
            return PAGE_NOT_FOUND_ERROR_PAGE;
        System.out.println("Logged out!");
        return "redirect:/";
    }
}
