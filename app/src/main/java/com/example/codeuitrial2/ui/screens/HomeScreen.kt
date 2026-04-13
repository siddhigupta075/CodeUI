package com.example.codeuitrial2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.SyncAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codeuitrial2.data.FinanceState
import com.example.codeuitrial2.ui.components.GlassCard
import com.example.codeuitrial2.ui.theme.*

@Composable
fun HomeScreen(
    state: FinanceState,
    onAddExpenseClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 40.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item { HeaderSection() }
        item { TotalBalanceCard(state.totalBalance) }
        item { AccountCardsRow() }
        item { QuickActionsRow(onAddExpenseClick) }
        item { SpendingOverview(state) }
        item { RecentTransactions(state) }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("Good Morning, Alex \uD83D\uDC4B", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), fontSize = 14.sp)
        }
        Row {
            Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            ) // Placeholder for Profile Pic
        }
    }
}

@Composable
fun TotalBalanceCard(balance: Double) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Total Balance", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.Visibility, contentDescription = "Visibility", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurface.copy(0.7f))
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Default.MoreVert, contentDescription = "More", tint = MaterialTheme.colorScheme.onSurface)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text("$${"%.2f".format(balance)}", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(8.dp))
            Text("↑ +12.5% vs last month", color = IncomeGreen, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun AccountCardsRow() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Box(modifier = Modifier
            .weight(1f)
            .height(110.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Brush.linearGradient(listOf(BankCardStart, BankCardEnd)))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    Text("Bank Account", color = Color.White, fontSize = 12.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.AccountBalance, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text("$4,550.50", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("•••• 1234", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
            }
        }
        Box(modifier = Modifier
            .weight(1f)
            .height(110.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Brush.linearGradient(listOf(DebitCardStart, DebitCardEnd)))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    Text("Debit Card", color = Color.White, fontSize = 12.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.CreditCard, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text("- $940.00", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("•••• 1234", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun QuickActionsRow(onAddExpenseClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Button(
            onClick = onAddExpenseClick,
            modifier = Modifier.weight(1f).height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Rounded.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Expense")
        }
        OutlinedButton(
            onClick = { /* Transfer action, mocked */ },
            modifier = Modifier.weight(1f).height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
        ) {
            Icon(Icons.Rounded.SyncAlt, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Transfer")
        }
    }
}

@Composable
fun SpendingOverview(state: FinanceState) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Spending Overview", style = MaterialTheme.typography.titleLarge)
            Text("This Month ⌄", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                state.budgetCategories.take(3).forEachIndexed { index, category ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(category.color).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            val icon = when(category.name.lowercase()) {
                                "rent" -> Icons.Default.Home
                                "groceries", "food" -> Icons.Default.ShoppingCart
                                else -> Icons.Default.DirectionsCar
                            }
                            Icon(icon, contentDescription = null, tint = Color(category.color))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(category.name, fontSize = 16.sp, modifier = Modifier.weight(1f))
                        Column(horizontalAlignment = Alignment.End) {
                            Text("$${"%.0f".format(category.spent)}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text("${(category.percentSpent * 100).toInt()}%", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecentTransactions(state: FinanceState) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Recent Transactions", style = MaterialTheme.typography.titleLarge)
            Text("See All", color = PrimaryBlue, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                state.transactions.take(3).forEach { tx ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Receipt, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(tx.name, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                            Text(tx.category, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "${if(tx.isIncome) "+" else "-"} $${"%.2f".format(tx.amount)}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (tx.isIncome) IncomeGreen else MaterialTheme.colorScheme.onSurface
                            )
                            Text(tx.date, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        }
                    }
                }
            }
        }
    }
}
