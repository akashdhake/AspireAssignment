package com.aspire.service.user;

import com.aspire.model.User;

public interface UserService {
    User createUser(User user);

    User getUserById(Long userId);

    User getUserByName(String userName);
}
