package com.keliii.user.service;

import com.keliii.user.repository.UserRepository;
import com.keliii.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by keliii on 2017/6/20.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
