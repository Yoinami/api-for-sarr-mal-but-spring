package com.yoinami.sarr_mal_api.service;

import com.yoinami.sarr_mal_api.model.Account;
import com.yoinami.sarr_mal_api.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountImplementation implements AccountService {
//    @Autowired
//    private AccountRepo accountRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Account createAccount(Account account) throws Exception {
//        AccountSolr accountSolr = new AccountSolr();
//        accountSolr.setId(account.getId());
//        accountSolr.setLastName(account.getLastName());
//        accountSolr.setUserName(account.getUserName());
//        accountSolr.setFirstName(account.getFirstName());
//        accountSolr.setPassword(account.getPassword());
        Account newAccount = new Account();
        return newAccount;
    }

    @Override
    public List<Account> searchCustomAccount(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        Account account = new Account();

        return List.of(account);
        //return accountRepo.findByCreatedDateBetween(start, end);
    }
}