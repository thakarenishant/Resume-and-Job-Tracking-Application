package org.web.resumeandjobtracking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.web.resumeandjobtracking.Model.User;
@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<User,Integer> {
}
