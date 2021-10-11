package com.pankbuto.ecommerce.controller;

import com.pankbuto.ecommerce.dto.ResponseDto;
import com.pankbuto.ecommerce.dto.user.SignInDto;
import com.pankbuto.ecommerce.dto.user.SignInResponseDto;
import com.pankbuto.ecommerce.dto.user.SignUpDto;
import com.pankbuto.ecommerce.models.User;
import com.pankbuto.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @PostMapping("/signup")
    public ResponseDto signUp(@RequestBody SignUpDto signupDto) throws Exception{
        return this.userService.signUp(signupDto);
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) throws Exception {
        return this.userService.signIn(signInDto);
    }
}
