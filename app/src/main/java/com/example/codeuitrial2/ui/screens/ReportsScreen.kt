package com.example.codeuitrial2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codeuitrial2.data.FinanceState
import com.example.codeuitrial2.ui.components.GlassCard
import com.example.codeuitrial2.ui.components.SimpleBarChart
import com.example.codeuitrial2.ui.theme.ExpenseRed
import com.example.codeuitrial2.ui.theme.IncomeGreen
import com.example.codeuitrial2.ui.theme.PrimaryBlue
import com.example.codeuitrial2.ui.theme.SavingsBlue

@Composable
fun ReportsScreen(state: FinanceState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 40.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            CenterHeader("Insights & Reports")
        }
        item {
            MonthSelector()
        }
        item {
            SummaryCards(state)
        }
        item {
            SpendingTrends()
        }
        item {
            InsightBanner()
        }
        item {
            TopExpenses(state)
        }
    }
}

@Composable
fun CenterHeader(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Default.FilterAlt, contentDescription = "Filter", tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
    }
}

@Composable
fun MonthSelector() {
    GlassCard(modifier = Modifier.fillMaxWidth(), cornerRadius = 30.dp) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.ChevronLeft, contentDescription = "Previous")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("February 2025 ⌄", fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.CalendarToday, contentDescription = "Calendar", modifier = Modifier.size(16.dp))
            }
            Icon(Icons.Default.ChevronRight, contentDescription = "Next")
        }
    }
}

@Composable
fun SummaryCards(state: FinanceState) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        listOf(
            Triple("Income", "$${"%.0f".format(state.totalIncome)}", IncomeGreen),
            Triple("Expenses", "$${"%.0f".format(state.totalExpense)}", ExpenseRed),
            Triple("Savings", "$${"%.0f".format(state.totalSavings)}", SavingsBlue)
        ).forEach { (title, amount, color) ->
            GlassCard(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(title, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(amount, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
                }
            }
        }
    }
}

@Composable
fun SpendingTrends() {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Spending Trends", style = MaterialTheme.typography.titleLarge)
                Text("This Month ⌄", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(0.6f))
            }
            Spacer(modifier = Modifier.height(24.dp))
            SimpleBarChart(
                data = listOf(1.2f, 3.8f, 2.5f, 4f, 2.8f),
                labels = listOf("Feb 1", "Feb 8", "Feb 15", "Feb 22", "Feb 28"),
                barColor = PrimaryBlue
            )
        }
    }
}

@Composable
fun InsightBanner() {
    GlassCard(modifier = Modifier.fillMaxWidth(), cornerRadius = 16.dp) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(32.dp).clip(CircleShape).background(IncomeGreen.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.TrendingUp, contentDescription = null, tint = IncomeGreen, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Spending increased by 15%", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text("Compared to January 2025", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            }
        }
    }
}

@Composable
fun TopExpenses(state: FinanceState) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Top Expenses", style = MaterialTheme.typography.titleLarge)
            Text("This Month ⌄", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                state.budgetCategories.sortedByDescending { it.spent }.take(3).forEach { category ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(category.color)),
                            contentAlignment = Alignment.Center
                        ) {
                           val icon = when(category.name.lowercase()) {
                                "rent" -> Icons.Default.Home
                                "groceries", "food" -> Icons.Default.ShoppingCart
                                "transport" -> Icons.Default.DirectionsCar
                                else -> Icons.Default.Category
                            }
                            Icon(icon, contentDescription = null, tint = Color.White)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(category.name, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                            Text("${(category.percentSpent * 100).toInt()}% of expenses", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        }
                        Text("$${"%.0f".format(category.spent)}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
