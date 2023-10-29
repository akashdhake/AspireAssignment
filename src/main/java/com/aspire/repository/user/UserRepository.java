package com.aspire.repository.user;

import com.aspire.model.User;

public interface UserRepository {

    User addUser(User user);

    User getUser(Long userId);

    User getUserByName(String userName);
}
