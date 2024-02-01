package io.github.AndCandido.apispringsecurity.entities.repositories;

import io.github.AndCandido.apispringsecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
}
