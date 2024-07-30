package com.example.expensessharing;

import com.example.expensessharing.controller.ExpenseController;
import com.example.expensessharing.model.Expense;
import com.example.expensessharing.model.Split;
import com.example.expensessharing.model.SplitType;
import com.example.expensessharing.model.User;
import com.example.expensessharing.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ExpenseController.class)
class ExpensessharingApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpenseService expenseService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private Expense expense;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("nitinjha");
        user.setEmail("nitin@example.com");
        user.setMobileNumber("987654637");

        Split split = new Split();
        split.setUser(user);
        split.setAmount(new BigDecimal("1000"));
        split.setPercentage(BigDecimal.valueOf(33.33));

        List<Split> splits = new ArrayList<>();
        splits.add(split);

        expense = new Expense();
        expense.setId(1L);
        expense.setDescription("Dinner");
        expense.setAmount(new BigDecimal("3000"));
        expense.setSplitType(SplitType.EQUAL);
        expense.setUser(user);
        expense.setSplits(splits);
    }

    @Test
    void testAddExpense() throws Exception {
        when(expenseService.addExpense(any(Expense.class))).thenReturn(expense);

        String json = objectMapper.writeValueAsString(expense);

        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(expenseService, times(1)).addExpense(any(Expense.class));
    }

    @Test
    void testGetAllExpenses() throws Exception {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(expense);

        when(expenseService.getAllExpenses()).thenReturn(expenses);

        mockMvc.perform(get("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Dinner"));

        verify(expenseService, times(1)).getAllExpenses();
    }

    @Test
    void testGetExpenseById() throws Exception {
        when(expenseService.getExpenseById(1L)).thenReturn(Optional.of(expense));

        mockMvc.perform(get("/api/expenses/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Dinner"));

        verify(expenseService, times(1)).getExpenseById(1L);
    }

    @Test
    void testDeleteExpense() throws Exception {
        doNothing().when(expenseService).deleteExpense(1L);

        mockMvc.perform(delete("/api/expenses/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(expenseService, times(1)).deleteExpense(1L);
    }

    @Test
    void testGetUserExpenses() throws Exception {
        List<Expense> expenses = new ArrayList<>();
        expenses.add(expense);

        when(expenseService.getUserExpenses(1L)).thenReturn(expenses);

        mockMvc.perform(get("/api/expenses/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Dinner"));

        verify(expenseService, times(1)).getUserExpenses(1L);
    }

    @Test
    void testDownloadBalanceSheet() throws Exception {
        byte[] emptySheet = new byte[0];
        when(expenseService.generateBalanceSheet()).thenReturn(emptySheet);

        mockMvc.perform(get("/api/expenses/balance-sheet")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=balance_sheet.xlsx"));

        verify(expenseService, times(1)).generateBalanceSheet();
    }
}
