package com.auth.auth.repository;

import com.auth.auth.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Modifying
    @Query("update User u set u.accessToken = ?1, u.accessTokenCreationTime = ?2 where u.refreshToken = ?3")
    void updateAccessTokenByRefreshToken(String accessToken, long ts, String refreshToken);

    List<User> findByRefreshToken(String refreshToken);

    List<User> findByAccessToken(String accessToken);
}

