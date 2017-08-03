package com.keliii.user.repository;

import com.keliii.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by keliii on 2017/6/20.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
