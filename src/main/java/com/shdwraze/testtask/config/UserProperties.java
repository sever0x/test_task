package com.shdwraze.testtask.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class UserProperties {

    @Value("${min-age}")
    private int minAge;

    @Value("${user.not.found.message}")
    private String userNotFoundMessage;
}
