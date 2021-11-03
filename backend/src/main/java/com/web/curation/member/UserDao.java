package com.web.curation.member;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserDao extends MongoRepository<User, String>, UserCustomDao{

    Optional<User> getUserByEmail(String email);


}
