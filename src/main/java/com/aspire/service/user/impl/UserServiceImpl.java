package com.aspire.service.user.impl;

import com.aspire.repository.user.UserRepository;
import com.aspire.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.aspire.model.User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        log.info("Checking if user with name " + user.getName() + " exists or not");
        User userByName = getUserByName(user.getName());
        if (userByName != null) {
            log.info("User already exists with name - " + user.getName());
            return userByName;
        }
        log.info("Creating user: {}", user.getName());
        User createdUser = userRepository.addUser(user);
        log.info("User created: {}", createdUser.getId());
        return createdUser;
    }

    @Override
    public User getUserById(Long userId) {
        log.info("fetching user data for userId - " + userId);
        return userRepository.getUser(userId);
    }

    @Override
    public User getUserByName(String userName) {
        log.info("fetching user data for userName - " + userName);
        return userRepository.getUserByName(userName);
    }
}
