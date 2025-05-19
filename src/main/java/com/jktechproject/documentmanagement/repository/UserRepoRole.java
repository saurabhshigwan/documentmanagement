package com.jktechproject.documentmanagement.repository;

import com.jktechproject.documentmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepoRole extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

}
