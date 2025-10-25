package org.userservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;

    @Override
    public UserDto createUser(UserDto user) {
        return null;
    }

    @Override
    public UserDto updateUser(int id, UserDto user) {
        return null;
    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public UserDto getUserById(int id) {
        return null;
    }

    @Override
    public Collection<UserDto> getAllUsers() {
        return List.of();
    }
}
