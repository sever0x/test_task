package com.shdwraze.testtask.repository;

import com.shdwraze.testtask.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    List<UserEntity> findByBirthdayBetween(LocalDate from, LocalDate to);
}
