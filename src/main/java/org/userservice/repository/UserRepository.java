package org.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.userservice.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
