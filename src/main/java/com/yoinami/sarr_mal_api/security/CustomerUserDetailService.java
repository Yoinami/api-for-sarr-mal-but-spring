package com.yoinami.sarr_mal_api.security;

import com.yoinami.sarr_mal_api.model.Account;
import com.yoinami.sarr_mal_api.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public class CustomerUserDetailService {
    @Service
    public static class CustomUserDetailsService implements UserDetailsService {
//        @Autowired
//        private AccountRepo accountRepo;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            try {
                System.out.println("loadByUsernameTriggered: " + username);
                Account account = new Account();
                account.setUserName(username);
                account.setPassword("$2a$11$c52gL8In8kb8ffDJa35Hj.rEn4ZtBqp9R1Pn/6XQWzVEo6QmCs8UC");
                String userName = account.getUserName();
                String password = account.getPassword();
                return new User(userName, password, new ArrayList<>());
            } catch (Exception e) {
                throw new UsernameNotFoundException("User not found: " + e.toString());
            }
        }
    }
}