package com.project.wab.repository;

import com.project.wab.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author "Vladyslav Paun"
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
