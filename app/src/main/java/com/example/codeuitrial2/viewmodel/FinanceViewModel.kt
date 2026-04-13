package com.example.codeuitrial2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.codeuitrial2.data.BudgetCategory
import com.example.codeuitrial2.data.FinanceState
import com.example.codeuitrial2.data.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FinanceViewModel : ViewModel() {
    private val _uiState = MutableStateFlow( FinanceState() )
    val uiState: StateFlow<FinanceState> = _uiState.asStateFlow()

    init {
        loadMockData()
    }

    private fun loadMockData() {
        val initialTransactions = listOf(
            Transaction(name = "Netflix", category = "Entertainment", amount = 15.99, date = "Feb 24", isIncome = false),
            Transaction(name = "Salary", category = "Income", amount = 5000.0, date = "Feb 20", isIncome = true),
            Transaction(name = "Groceries", category = "Food", amount = 120.50, date = "Feb 18", isIncome = false)
        )

        val initialCategories = listOf(
            BudgetCategory(name = "Rent", totalBudget = 1200.0, spent = 1200.0, color = 0xFF4CAF50),
            BudgetCategory(name = "Groceries", totalBudget = 300.0, spent = 420.0, color = 0xFFF44336), // Over budget
            BudgetCategory(name = "Transport", totalBudget = 250.0, spent = 150.0, color = 0xFFFFC107)
        )

        _uiState.update { currentState ->
            currentState.copy(
                transactions = initialTransactions,
                budgetCategories = initialCategories,
                totalBalance = 3610.50,
                budgetTotal = 2500.0
            )
        }
    }

    fun addExpense(name: String, amount: Double, categoryName: String) {
        val newTx = Transaction(
            name = name,
            category = categoryName,
            amount = amount,
            date = "Today",
            isIncome = false
        )

        _uiState.update { state ->
            val updatedBalance = state.totalBalance - amount
            val updatedTransactions = listOf(newTx) + state.transactions

            // Update matching category or add a new one
            val matchingCatIndex = state.budgetCategories.indexOfFirst { it.name.equals(categoryName, ignoreCase = true) }
            val updatedCategories = state.budgetCategories.toMutableList()
            
            if (matchingCatIndex != -1) {
                val cat = updatedCategories[matchingCatIndex]
                updatedCategories[matchingCatIndex] = cat.copy(spent = cat.spent + amount)
            } else {
                updatedCategories.add(BudgetCategory(name = categoryName, totalBudget = 500.0, spent = amount, color = 0xFF2196F3))
            }

            state.copy(
                transactions = updatedTransactions,
                budgetCategories = updatedCategories,
                totalBalance = updatedBalance,
                totalExpense = state.totalExpense + amount
            )
        }
    }
}
