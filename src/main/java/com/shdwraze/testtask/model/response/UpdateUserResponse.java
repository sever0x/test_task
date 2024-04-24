package com.shdwraze.testtask.model.response;

import java.time.LocalDate;

public record UpdateUserResponse(

        int id,

        String email,

        String firstName,

        String lastName,

        LocalDate birthday,

        String address,

        String phoneNumber
) {
}
