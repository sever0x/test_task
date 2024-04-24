package com.shdwraze.testtask.controller;

import com.shdwraze.testtask.model.entity.UserEntity;
import com.shdwraze.testtask.model.request.UpdateUserRequest;
import com.shdwraze.testtask.model.request.UserRequest;
import com.shdwraze.testtask.model.response.FoundUsersResponse;
import com.shdwraze.testtask.model.response.UpdateUserResponse;
import com.shdwraze.testtask.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public UserEntity createUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PutMapping("/update/{id}")
    public UpdateUserResponse updateUser(@RequestBody @Valid UpdateUserRequest userRequest,
                                         @PathVariable int id) {
        return userService.updateUser(userRequest, id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @GetMapping("/find")
    public FoundUsersResponse findUsersInBirthdateRange(
            @RequestParam LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {
        return userService.findUsersInBirthdateRange(from, to);
    }
}
