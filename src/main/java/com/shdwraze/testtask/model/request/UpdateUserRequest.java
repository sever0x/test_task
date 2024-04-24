package com.shdwraze.testtask.model.request;

import com.shdwraze.testtask.validation.Email;

import java.time.LocalDate;

public record UpdateUserRequest(

        @Email
        String email,

        String firstName,

        String lastName,

        LocalDate birthday,

        String address,

        String phoneNumber
) {
}
