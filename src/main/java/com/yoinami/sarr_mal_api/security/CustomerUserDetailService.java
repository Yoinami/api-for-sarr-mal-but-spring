package com.yoinami.sarr_mal_api.security;

import com.yoinami.sarr_mal_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

public class CustomerUserDetailService {
    @Service
    public static class CustomUserDetailsService implements UserDetailsService {

        @Autowired
        private UserRepository userRepo;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            try {
                System.out.println("loadByUsernameTriggered: " + username);
                com.yoinami.sarr_mal_api.model.User account = userRepo.findItemByName(username);
                String userName = account.getUsername();
                String password = account.getPassword();
                return new User(userName, password, new ArrayList<>());
            } catch (Exception e) {
                throw new UsernameNotFoundException("User not found: " + e.toString());
            }
        }
    }
}