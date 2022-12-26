package com.vti.testing.controller;

import com.vti.testing.entity.Account;
import com.vti.testing.entity.dto.LoginInforDTO;
import com.vti.testing.service.IAccountService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/login")
    public LoginInforDTO login(Principal principal) {
        String username = principal.getName();
        Account account = accountService.getAccountByUsername(username);
        return modelMapper.map(account, new TypeToken<LoginInforDTO>() {
        }.getType());
    }
}
