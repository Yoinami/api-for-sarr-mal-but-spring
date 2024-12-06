package com.yoinami.sarr_mal_api.repository;

import com.yoinami.sarr_mal_api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface UserRepository extends MongoRepository<User, String> {
    @Query("{username:'?0'}")
    User findItemByName(String name);
}
