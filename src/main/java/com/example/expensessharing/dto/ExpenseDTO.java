package com.example.expensessharing.dto;

import java.util.List;

public class ExpenseDTO {
    private Long id;
    private String description;
    private Double amount; // Ensure this is Double
    private String splitType;
    private UserDTO user;
    private List<SplitDTO> splits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getSplitType() {
        return splitType;
    }

    public void setSplitType(String splitType) {
        this.splitType = splitType;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<SplitDTO> getSplits() {
        return splits;
    }

    public void setSplits(List<SplitDTO> splits) {
        this.splits = splits;
    }
}
