package com.yoinami.sarr_mal_api.security;

import com.yoinami.sarr_mal_api.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

public class CustomerUserDetailService {
    @Service
    public class CustomUserDetailsService implements UserDetailsService {
        @Autowired
        private AccountRepo accountRepo;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            try {
                Account account = accountRepo.findByuserName(username);
                String userName = account.getUserName();
                String password = account.getPassword();
                return new User(userName, password, new ArrayList<>());
            } catch (Exception e) {
                throw new UsernameNotFoundException("User not found: " + e.toString());
            }
        }
    }
}