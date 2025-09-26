package org.web.resumeandjobtracking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.web.resumeandjobtracking.Model.User;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}
