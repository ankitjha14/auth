package com.auth.auth.repository;

import com.auth.auth.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    RefreshToken findAllByRefreshToken(String refToken);
}

