package com.shdwraze.testtask.model.response;

import com.shdwraze.testtask.model.entity.UserEntity;

import java.util.List;

public record FoundUsersResponse(
        int count,

        List<UserEntity> users
) {
}
