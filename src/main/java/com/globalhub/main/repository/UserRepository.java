package com.globalhub.main.repository;

import com.globalhub.main.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM users u WHERE u.email = :login OR u.rgg = :login")
    Optional<User> findByEmailOrRgg(@Param("login") String login);
    boolean existsByRgg(String rgg);

}
