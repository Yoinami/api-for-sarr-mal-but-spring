package com.yoinami.sarr_mal_api.repository;

import com.yoinami.sarr_mal_api.model.Account;

public class AccountRepo {
    Account findbyUsername(String username) {
        Account acc =  new Account();
        acc.setUserName(username);
        return acc;
    }
}
