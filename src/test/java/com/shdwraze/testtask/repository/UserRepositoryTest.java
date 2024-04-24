package com.shdwraze.testtask.repository;

import com.shdwraze.testtask.model.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUsersByBirthdayBetween() {
        UserEntity user1 = new UserEntity();
        user1.setBirthday(LocalDate.parse("2000-01-01"));
        entityManager.persist(user1);

        UserEntity user2 = new UserEntity();
        user2.setBirthday(LocalDate.parse("1990-01-01"));
        entityManager.persist(user2);

        LocalDate fromDate = LocalDate.parse("1995-01-01");
        LocalDate toDate = LocalDate.parse("2005-01-01");

        List<UserEntity> result = userRepository.findByBirthdayBetween(fromDate, toDate);

        assertThat(result).containsExactly(user1);
    }
}