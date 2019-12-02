package com.diaspark.INB.controller;

import com.diaspark.INB.DTO.*;
import com.diaspark.INB.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public void registration(@RequestBody RegisterUserDTO registerUserDTO) {
        userService.registerUser(registerUserDTO);
    }

    @PostMapping("/login")
    public UserResponseDTO authenticate(@RequestBody LoginUserDTO loginUserDTO) throws Exception {
        return userService.authenticateUser(loginUserDTO);
    }

    @PreAuthorize("hasRole('ROLE_user')")
    @PostMapping("/contact-us")
    public EmailResponseDTO send(@RequestBody SendMailDTO sendMailDTO) {
        return userService.sendQuery(sendMailDTO);
    }

    /*
     * usage : http://localhost:8080/user/fetch?status=rejected
     */
    @PreAuthorize("hasRole('ROLE_admin')")
    @GetMapping("/fetch")
    public List<UserResponseDTO> retreiveUsers(@RequestParam String status) {
        return userService.retreiveUsers(status);
    }

    /*
     * http://localhost:8080/user/update/status/1?status=approved
     */
    @PreAuthorize("hasRole('ROLE_admin')")
    @PutMapping("/update/{customerId}")
    public UserResponseDTO updateUserStatus(@PathVariable long customerId, @RequestParam String status) {
        return userService.updateUserStatus(customerId, status);

    }
}

