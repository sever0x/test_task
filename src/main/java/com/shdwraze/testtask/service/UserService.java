package com.shdwraze.testtask.service;

import com.shdwraze.testtask.model.entity.UserEntity;
import com.shdwraze.testtask.model.request.UpdateUserRequest;
import com.shdwraze.testtask.model.request.UserRequest;
import com.shdwraze.testtask.model.response.FoundUsersResponse;
import com.shdwraze.testtask.model.response.UpdateUserResponse;

import java.time.LocalDate;
import java.util.Date;

public interface UserService {

    UserEntity createUser(UserRequest userRequest);

    UpdateUserResponse updateUser(UpdateUserRequest userRequest, int id);

    void deleteUser(int id);

    FoundUsersResponse findUsersInBirthdateRange(LocalDate from, LocalDate to);
}
