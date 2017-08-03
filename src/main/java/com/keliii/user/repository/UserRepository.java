package com.keliii.user.repository;

import com.keliii.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by keliii on 2017/6/20.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
