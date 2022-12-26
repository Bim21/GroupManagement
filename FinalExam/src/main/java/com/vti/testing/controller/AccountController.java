package com.vti.testing.controller;

import com.vti.testing.entity.Account;
import com.vti.testing.entity.dto.AccountDTO;
import com.vti.testing.entity.filter.AccountFilter;
import com.vti.testing.entity.form.CreateAccountForm;
import com.vti.testing.service.IAccountService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/accounts")
@Validated
public class AccountController {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public Page<AccountDTO> getAllAccounts(@PageableDefault(sort = "username") Pageable pageable, AccountFilter filter) {
        Page<Account> page = accountService.getAllAccounts(pageable, filter);
        List<AccountDTO> dtos = modelMapper.map(page.getContent(), new TypeToken<List<AccountDTO>>() {
        }.getType());

        return new PageImpl<>(dtos, pageable, page.getTotalPages());
    }

    @GetMapping({"/{id}"})
    public AccountDTO getAccountById(@PathVariable("id") int id) {
        Account account = accountService.getAccountById(id);
        return modelMapper.map(account, new TypeToken<AccountDTO>() {
        }.getType());
    }

    @PostMapping
    public void createAccount(@RequestBody @Valid CreateAccountForm form) {
        accountService.createAccount(form);
    }

    @DeleteMapping("/{ids}")
    public void deleteAccount(@PathVariable("ids") List<Integer> ids) {
        ids.forEach(id -> accountService.deleteAccount(id));
    }
}
