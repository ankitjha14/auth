package com.auth.auth.repository;

import com.auth.auth.model.GoogleUser;
import com.auth.auth.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoogleUserRepository extends CrudRepository<GoogleUser, Long> {

    GoogleUser findByEmail(String userEmail);
    GoogleUser findByPhone(String userPhone);
}

