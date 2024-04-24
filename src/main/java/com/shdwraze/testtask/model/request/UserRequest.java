package com.shdwraze.testtask.model.request;

import com.shdwraze.testtask.validation.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserRequest(

        @Email
        @NotNull(message = "{NotNull.userRequest.email}")
        String email,

        @NotNull(message = "{NotNull.userRequest.firstName}")
        String firstName,

        @NotNull(message = "{NotNull.userRequest.lastName}")
        String lastName,

        @NotNull(message = "{NotNull.userRequest.birthday}")
        LocalDate birthday,

        String address,

        String phoneNumber
) {
}
