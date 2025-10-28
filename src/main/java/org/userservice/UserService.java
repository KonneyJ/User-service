package org.userservice;

import java.util.Collection;

public interface UserService {

    UserDto createUser(UserDto user);

    UserDto updateUser(int id, UserDto user);

    boolean deleteUser(int id);

    UserDto getUserById(int id);

    Collection<UserDto> getAllUsers();
}
