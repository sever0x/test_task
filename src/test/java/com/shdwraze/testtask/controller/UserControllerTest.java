package com.shdwraze.testtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shdwraze.testtask.model.entity.UserEntity;
import com.shdwraze.testtask.model.request.UpdateUserRequest;
import com.shdwraze.testtask.model.request.UserRequest;
import com.shdwraze.testtask.model.response.FoundUsersResponse;
import com.shdwraze.testtask.model.response.UpdateUserResponse;
import com.shdwraze.testtask.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateUser() throws Exception {
        UserRequest userRequest = new UserRequest("test@example.com", "John", "Doe",
                LocalDate.parse("1995-08-01"), "1234 Elm St", "1234567890");
        UserEntity expectedUserEntity = UserEntity.builder()
                .id(1)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .birthday(LocalDate.parse("1995-08-01"))
                .address("1234 Elm St")
                .phoneNumber("1234567890")
                .build();
        given(userService.createUser(userRequest)).willReturn(expectedUserEntity);

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(
                "updated@example.com", "UpdatedFirstName", "UpdatedLastName",
                LocalDate.parse("2000-01-01"), "Updated Address", "9876543210");
        UpdateUserResponse expectedUpdateUserResponse = new UpdateUserResponse(
                1, "updated@example.com", "UpdatedFirstName", "UpdatedLastName",
                LocalDate.parse("2000-01-01"), "Updated Address", "9876543210");
        given(userService.updateUser(any(UpdateUserRequest.class), eq(1)))
                .willReturn(expectedUpdateUserResponse);

        mockMvc.perform(put("/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.firstName").value("UpdatedFirstName"))
                .andExpect(jsonPath("$.lastName").value("UpdatedLastName"))
                .andExpect(jsonPath("$.birthday").value("2000-01-01"))
                .andExpect(jsonPath("$.address").value("Updated Address"))
                .andExpect(jsonPath("$.phoneNumber").value("9876543210"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/delete/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindUsersInBirthdateRange() throws Exception {
        LocalDate fromDate = LocalDate.parse("1990-01-01");
        LocalDate toDate = LocalDate.parse("2000-01-01");

        UserEntity user1 = new UserEntity(1, "user1@example.com", "John", "Doe",
                LocalDate.parse("1995-05-10"), "1234 Elm St", "1234567890");
        UserEntity user2 = new UserEntity(2, "user2@example.com", "Jane", "Doe",
                LocalDate.parse("1998-07-20"), "5678 Oak St", "9876543210");
        List<UserEntity> expectedUsers = List.of(user1, user2);
        FoundUsersResponse expectedResponse = new FoundUsersResponse(expectedUsers.size(), expectedUsers);
        given(userService.findUsersInBirthdateRange(fromDate, toDate)).willReturn(expectedResponse);

        mockMvc.perform(get("/find")
                        .param("from", "1990-01-01")
                        .param("to", "2000-01-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(expectedUsers.size()))
                .andExpect(jsonPath("$.users", hasSize(expectedUsers.size())))
                .andExpect(jsonPath("$.users[0].id").value(user1.getId()))
                .andExpect(jsonPath("$.users[0].email").value(user1.getEmail()))
                .andExpect(jsonPath("$.users[1].id").value(user2.getId()))
                .andExpect(jsonPath("$.users[1].email").value(user2.getEmail()));
    }
}