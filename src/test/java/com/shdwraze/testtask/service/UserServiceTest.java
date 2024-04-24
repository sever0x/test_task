package com.shdwraze.testtask.service;

import com.shdwraze.testtask.config.UserProperties;
import com.shdwraze.testtask.mapper.UserMapper;
import com.shdwraze.testtask.model.entity.UserEntity;
import com.shdwraze.testtask.model.request.UpdateUserRequest;
import com.shdwraze.testtask.model.request.UserRequest;
import com.shdwraze.testtask.model.response.FoundUsersResponse;
import com.shdwraze.testtask.model.response.UpdateUserResponse;
import com.shdwraze.testtask.repository.UserRepository;
import com.shdwraze.testtask.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserProperties userProperties;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldCreateUserEntityFromValidUserRequest() {
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
        when(userProperties.getMinAge()).thenReturn(18);
        when(userMapper.toUserEntity(userRequest)).thenReturn(expectedUserEntity);
        when(userRepository.save(expectedUserEntity)).thenReturn(expectedUserEntity);

        UserEntity result = userService.createUser(userRequest);

        assertThat(result).isEqualTo(expectedUserEntity);
    }

    @Test
    void shouldCreateUpdateUserResponseFromValidUpdateUserRequest() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(
                "updated@example.com", "UpdatedFirstName", "UpdatedLastName",
                LocalDate.parse("2000-01-01"), "Updated Address", "9876543210");
        UserEntity existingUserEntity = UserEntity.builder()
                .id(1)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .birthday(LocalDate.parse("1995-08-01"))
                .address("1234 Elm St")
                .phoneNumber("1234567890")
                .build();
        UserEntity updatedUserEntity = UserEntity.builder()
                .id(1)
                .email("updated@example.com")
                .firstName("UpdatedFirstName")
                .lastName("UpdatedLastName")
                .birthday(LocalDate.parse("2000-01-01"))
                .address("Updated Address")
                .phoneNumber("9876543210")
                .build();
        UpdateUserResponse expectedUpdateUserResponse = new UpdateUserResponse(
                1, "updated@example.com", "UpdatedFirstName", "UpdatedLastName",
                LocalDate.parse("2000-01-01"), "Updated Address", "9876543210");
        when(userProperties.getMinAge()).thenReturn(18);
        when(userRepository.findById(1)).thenReturn(Optional.of(existingUserEntity));
        when(userMapper.toUpdateUserResponse(updatedUserEntity)).thenReturn(expectedUpdateUserResponse);
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUserEntity);

        UpdateUserResponse result = userService.updateUser(updateUserRequest, 1);

        assertThat(result).isEqualTo(expectedUpdateUserResponse);
    }

    @Test
    void shouldDeleteUserWhenUserExists() {
        when(userRepository.existsById(1)).thenReturn(true);

        userService.deleteUser(1);

        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void shouldFindUsersInBirthdateRangeAndReturnFoundUsersResponse() {
        LocalDate fromDate = LocalDate.parse("1990-01-01");
        LocalDate toDate = LocalDate.parse("2000-01-01");
        UserEntity user1 = new UserEntity(1, "user1@example.com", "John", "Doe",
                LocalDate.parse("1995-05-10"), "1234 Elm St", "1234567890");
        UserEntity user2 = new UserEntity(2, "user2@example.com", "Jane", "Doe",
                LocalDate.parse("1998-07-20"), "5678 Oak St", "9876543210");
        List<UserEntity> expectedUsers = List.of(user1, user2);
        when(userRepository.findByBirthdayBetween(fromDate, toDate)).thenReturn(expectedUsers);
        FoundUsersResponse expectedResponse = new FoundUsersResponse(expectedUsers.size(), expectedUsers);
        lenient().when(userService.findUsersInBirthdateRange(fromDate, toDate)).thenReturn(expectedResponse);

        FoundUsersResponse result = userService.findUsersInBirthdateRange(fromDate, toDate);

        assertThat(result.count()).isEqualTo(expectedUsers.size());
        assertThat(result.users()).isEqualTo(expectedUsers);
    }
}
