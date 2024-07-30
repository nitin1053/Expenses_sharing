package com.example.expensessharing.service;

import com.example.expensessharing.dto.ExpenseDTO;
import com.example.expensessharing.model.*;
import com.example.expensessharing.repository.ExpenseRepository;
import com.example.expensessharing.repository.SplitRepository;
import com.example.expensessharing.repository.UserRepository;
import com.example.expensessharing.util.MapperUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private SplitRepository splitRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense addExpense(Expense expense) {
        validatePercentages(expense);
        Expense savedExpense = expenseRepository.save(expense);
        calculateSplits(savedExpense);
        return savedExpense;
    }

    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    public List<Expense> getUserExpenses(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    private void calculateSplits(Expense expense) {
        List<Split> splits = expense.getSplits();
        BigDecimal totalAmount = expense.getAmount();
        SplitType splitType = expense.getSplitType();

        switch (splitType) {
            case EQUAL:
                BigDecimal equalAmount = totalAmount.divide(new BigDecimal(splits.size()));
                splits.forEach(split -> {
                    split.setAmount(equalAmount);
                    split.setExpense(expense);
                });
                break;

            case EXACT:
                splits.forEach(split -> {
                    split.setExpense(expense);
                });
                break;

            case PERCENTAGE:
                splits.forEach(split -> {
                    BigDecimal percentageAmount = totalAmount.multiply(split.getPercentage().divide(new BigDecimal(100)));
                    split.setAmount(percentageAmount);
                    split.setExpense(expense);
                });
                break;
        }

        splitRepository.saveAll(splits);
    }

    private void validatePercentages(Expense expense) {
        if (expense.getSplitType() == SplitType.PERCENTAGE) {
            BigDecimal totalPercentage = expense.getSplits().stream()
                    .map(Split::getPercentage)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (totalPercentage.compareTo(new BigDecimal(100)) != 0) {
                throw new IllegalArgumentException("Total percentage must add up to 100%");
            }
        }
    }

    public byte[] generateBalanceSheet() throws IOException {
        List<User> users = userRepository.findAll();
        List<Expense> expenses = expenseRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Balance Sheet");

        int rowNum = 0;
        Row headerRow = sheet.createRow(rowNum++);

        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("User");

        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("Expense Description");

        Cell headerCell3 = headerRow.createCell(2);
        headerCell3.setCellValue("Amount");

        Cell headerCell4 = headerRow.createCell(3);
        headerCell4.setCellValue("Split Type");

        for (User user : users) {
            for (Expense expense : user.getExpenses()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getName());
                row.createCell(1).setCellValue(expense.getDescription());
                row.createCell(2).setCellValue(expense.getAmount().toString());
                row.createCell(3).setCellValue(expense.getSplitType().name());
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}
