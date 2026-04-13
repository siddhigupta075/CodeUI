package com.example.codeuitrial2.data

import java.util.UUID

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val category: String,
    val amount: Double,
    val date: String,
    val isIncome: Boolean,
    val iconRes: Int? = null // For simplicity, we could use preset icons or emojis
)

data class BudgetCategory(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val totalBudget: Double,
    val spent: Double,
    val color: Long // Hex color
) {
    val remaining get() = totalBudget - spent
    val percentSpent get() = if (totalBudget > 0) spent / totalBudget else 0.0
}

data class FinanceState(
    val totalBalance: Double = 3610.50,
    val totalIncome: Double = 5000.0,
    val totalExpense: Double = 3500.0,
    val totalSavings: Double = 1500.0,
    val budgetTotal: Double = 2500.0,
    val transactions: List<Transaction> = emptyList(),
    val budgetCategories: List<BudgetCategory> = emptyList()
) {
    val budgetSpent: Double get() = budgetCategories.sumOf { it.spent }
    val budgetRemaining: Double get() = budgetTotal - budgetSpent
}
