package com.yoinami.sarr_mal_api.model;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "accounts")
public class Account {
    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    @CreatedDate
    private LocalDate createdDate;
}