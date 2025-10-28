package org.userservice;

import java.util.Collection;

public interface UserDAO {
    User createUser(User user);

    User updateUser(int id, User user);

    boolean deleteUser(int id);

    User getUserById(int id);

    Collection<User> getAllUsers();
}
