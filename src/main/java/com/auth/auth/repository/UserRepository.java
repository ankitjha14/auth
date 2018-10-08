package com.auth.auth.repository;

import com.auth.auth.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String userEmail);
    User findByPhone(String userPhone);
}

