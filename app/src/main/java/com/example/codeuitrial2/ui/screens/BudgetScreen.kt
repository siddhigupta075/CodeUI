package com.example.codeuitrial2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import com.example.codeuitrial2.ui.components.CircularBudgetChart
import com.example.codeuitrial2.ui.components.GlassCard
import com.example.codeuitrial2.ui.theme.ExpenseRed
import com.example.codeuitrial2.ui.theme.IncomeGreen
import com.example.codeuitrial2.ui.theme.PrimaryBlue
import com.example.codeuitrial2.ui.theme.SavingsBlue

@Composable
fun BudgetScreen(state: FinanceState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 40.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            CenterHeader("Smart Budget")
        }
        item {
            MonthSelector()
        }
        item {
            BudgetOverviewCard(state)
        }
        item {
            YourExpenses(state)
        }
        item {
            GreatInsightBanner()
        }
    }
}

@Composable
fun BudgetOverviewCard(state: FinanceState) {
    val percentSpent = if (state.budgetTotal > 0) (state.budgetSpent / state.budgetTotal).toFloat() else 0f
    
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Your Budget", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularBudgetChart(
                    percentage = percentSpent.coerceIn(0f, 1f),
                    primaryColor = PrimaryBlue
                )
                
                Spacer(modifier = Modifier.width(32.dp))
                
                Column {
                    Text("Total Budget", color = MaterialTheme.colorScheme.onSurface.copy(0.6f), fontSize = 12.sp)
                    Text("$${"%.0f".format(state.budgetTotal)}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text("Remaining", color = MaterialTheme.colorScheme.onSurface.copy(0.6f), fontSize = 12.sp)
                    Text(
                        text = "$${"%.0f".format(state.budgetRemaining)}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = IncomeGreen
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Linear Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onSurface.copy(0.1f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(percentSpent.coerceIn(0f, 1f))
                        .height(8.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlue)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Spent: $${"%.0f".format(state.budgetSpent)}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(0.6f))
        }
    }
}

@Composable
fun YourExpenses(state: FinanceState) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Your Expenses", style = MaterialTheme.typography.titleLarge)
            Text("This Month ⌄", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                state.budgetCategories.forEach { category ->
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
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(category.name, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                Text("$${"%.0f".format(category.spent)} / $${"%.0f".format(category.totalBudget)}", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            // Line progress
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onSurface.copy(0.1f))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(category.percentSpent.toFloat().coerceIn(0f, 1f))
                                        .height(4.dp)
                                        .clip(CircleShape)
                                        .background(Color(category.color))
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${(category.percentSpent * 100).toInt()}%",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GreatInsightBanner() {
    GlassCard(modifier = Modifier.fillMaxWidth(), cornerRadius = 16.dp) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = SavingsBlue, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Insight", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = SavingsBlue)
                Text("You're doing great! \uD83C\uDF89", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("You've saved 12.5% more than last month.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(0.7f))
            }
        }
    }
}
