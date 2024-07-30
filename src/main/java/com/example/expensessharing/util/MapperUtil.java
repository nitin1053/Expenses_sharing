package com.example.expensessharing.util;

import com.example.expensessharing.dto.ExpenseDTO;
import com.example.expensessharing.dto.SplitDTO;
import com.example.expensessharing.dto.UserDTO;
import com.example.expensessharing.model.Expense;
import com.example.expensessharing.model.Split;
import com.example.expensessharing.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class MapperUtil {

    public static UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setMobileNumber(user.getMobileNumber());
        return dto;
    }

    public static SplitDTO toSplitDTO(Split split) {
        SplitDTO dto = new SplitDTO();
        dto.setId(split.getId());
        dto.setUser(toUserDTO(split.getUser()));
        dto.setAmount(split.getAmount() != null ? split.getAmount().doubleValue() : null);
        dto.setPercentage(split.getPercentage() != null ? split.getPercentage().intValue() : null); // Change to intValue()
        return dto;
    }

    public static ExpenseDTO toExpenseDTO(Expense expense) {
        ExpenseDTO dto = new ExpenseDTO();
        dto.setId(expense.getId());
        dto.setDescription(expense.getDescription());
        dto.setAmount(expense.getAmount().doubleValue());
        dto.setSplitType(expense.getSplitType().toString());
        dto.setUser(toUserDTO(expense.getUser()));
        List<SplitDTO> splits = expense.getSplits().stream()
                .map(MapperUtil::toSplitDTO)
                .collect(Collectors.toList());
        dto.setSplits(splits);
        return dto;
    }
}
