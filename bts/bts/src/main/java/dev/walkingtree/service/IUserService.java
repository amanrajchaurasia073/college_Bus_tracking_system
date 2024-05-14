package dev.walkingtree.service;


import dev.walkingtree.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    User createUser(User securityUser);

    User updateUser(User updatedUser);

    void deleteUser(String username);

    List<User> findAllUsers();

    Optional<User> findUserByUsername(String username);

}
