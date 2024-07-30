package com.example.expensessharing.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Expense {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @Setter
    @Getter
    private BigDecimal amount;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private SplitType splitType;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Setter
    @Getter
    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL)
    private List<Split> splits;

    public @NotBlank(message = "Description is mandatory") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Description is mandatory") String description) {
        this.description = description;
    }

}
