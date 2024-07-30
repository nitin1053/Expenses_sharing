package com.example.expensessharing.repository;

import com.example.expensessharing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
