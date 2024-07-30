package com.example.expensessharing.repository;

import com.example.expensessharing.model.Split;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SplitRepository extends JpaRepository<Split, Long> {
}
