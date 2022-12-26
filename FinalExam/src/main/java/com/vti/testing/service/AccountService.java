package com.vti.testing.service;

import com.vti.testing.entity.Account;
import com.vti.testing.entity.filter.AccountFilter;
import com.vti.testing.entity.form.CreateAccountForm;
import com.vti.testing.repository.IAccountRepository;
import com.vti.testing.specification.AccountSpecification;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<Account> getAllAccounts(Pageable pageable, AccountFilter filter) {
        Specification<Account> where = AccountSpecification.buildWhere(filter);
        return accountRepository.findAll(where, pageable);
    }

    @Override
    public Account getAccountById(int id) {
        return accountRepository.findById(id);
    }


    @Override
    public void createAccount(CreateAccountForm form) {
        modelMapper.addMappings(new PropertyMap<CreateAccountForm, Account>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
        Account account = modelMapper.map(form, new TypeToken<Account>() {
        }.getType());
        accountRepository.save(account);
    }

    @Override
    public boolean isAccountExistsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    @Override
    public void deleteAccount(int id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(username, account.getPassword(), AuthorityUtils.createAuthorityList(account.getRole().toString()));
    }


}
