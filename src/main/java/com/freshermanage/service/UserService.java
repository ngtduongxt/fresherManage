package com.freshermanage.service;

import com.freshermanage.model.Users;

public interface UserService {
    Users findByUserName(String userName);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    Users saveOrUpdate(Users user);


}
