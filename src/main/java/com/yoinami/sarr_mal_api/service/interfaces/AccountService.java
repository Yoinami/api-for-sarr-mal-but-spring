package com.yoinami.sarr_mal_api.service.interfaces;

import com.yoinami.sarr_mal_api.model.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(Account account) throws Exception;
    List<Account> searchCustomAccount(String startDate, String endDate);
}