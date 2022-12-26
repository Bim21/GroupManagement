package com.vti.testing.service;

import com.vti.testing.entity.Account;
import com.vti.testing.entity.filter.AccountFilter;
import com.vti.testing.entity.form.CreateAccountForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAccountService extends UserDetailsService {

    Page<Account> getAllAccounts(Pageable pageable, AccountFilter filter);

    Account getAccountById(int id);

    void createAccount(CreateAccountForm form);

    boolean isAccountExistsByUsername(String username);

    void deleteAccount(int id);

    Account getAccountByUsername(String username);

}
