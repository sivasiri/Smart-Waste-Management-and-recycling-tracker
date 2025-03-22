package com.akatsuki.project.Repo;

import com.akatsuki.project.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
User findByEmail(String email);
}
