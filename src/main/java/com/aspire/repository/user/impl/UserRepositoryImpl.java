package com.aspire.repository.user.impl;

import com.aspire.exceptions.UserAlreadyExistsException;
import com.aspire.exceptions.UserNotFoundException;
import com.aspire.model.InMemoryStore;
import com.aspire.model.User;
import com.aspire.repository.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Override
    public User addUser(User user) {
        boolean userExists = InMemoryStore.users.keySet()
                .stream()
                .anyMatch(userId -> (userId == user.getId()));
        if (!userExists) {
            InMemoryStore.users.put(user.getId(), user);
            return user;
        }
        throw new UserAlreadyExistsException();
    }

    @Override
    public User getUser(Long userId) {
        if (InMemoryStore.users.containsKey(userId)) {
            return InMemoryStore.users.get(userId);
        }
        throw new UserNotFoundException();
    }

    @Override
    public User getUserByName(String userName) {
        Optional<User> user = InMemoryStore.users.values()
                .stream()
                .filter(u -> userName.equals(u.getName()))
                .findFirst();
        return user.orElse(null);
    }
}
