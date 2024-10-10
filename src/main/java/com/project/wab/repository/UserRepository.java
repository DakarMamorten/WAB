package com.project.wab.repository;

import com.project.wab.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author "Vladyslav Paun"
 */
public interface UserRepository extends CrudRepository<User, Long> {

}
