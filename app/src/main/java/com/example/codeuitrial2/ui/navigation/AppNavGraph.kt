package com.example.codeuitrial2.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.codeuitrial2.ui.screens.BudgetScreen
import com.example.codeuitrial2.ui.screens.HomeScreen
import com.example.codeuitrial2.ui.screens.ReportsScreen
import com.example.codeuitrial2.ui.theme.PrimaryBlue
import com.example.codeuitrial2.viewmodel.FinanceViewModel
import kotlinx.coroutines.launch

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Reports : Screen("reports", "Reports", Icons.Default.BarChart)
    object Wallet : Screen("wallet", "Wallet", Icons.Default.AccountBalanceWallet)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val navController = rememberNavController()
    val viewModel: FinanceViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    val bottomNavItems = listOf(Screen.Home, Screen.Reports, Screen.Wallet, Screen.Profile)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showAddExpense by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            GlassBottomNavigation(
                items = bottomNavItems,
                currentRoute = currentRoute,
                onItemClick = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    state = uiState,
                    onAddExpenseClick = { showAddExpense = true }
                )
            }
            composable(Screen.Reports.route) {
                ReportsScreen(state = uiState)
            }
            composable(Screen.Wallet.route) {
                BudgetScreen(state = uiState)
            }
            composable(Screen.Profile.route) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Profile Screen Coming Soon")
                }
            }
        }

        if (showAddExpense) {
            ModalBottomSheet(
                onDismissRequest = { showAddExpense = false },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                AddExpenseContent(
                    onAdd = { name, amount, category ->
                        viewModel.addExpense(name, amount, category)
                        scope.launch { sheetState.hide() }.invokeOnCompletion { 
                            showAddExpense = false 
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun GlassBottomNavigation(
    items: List<Screen>,
    currentRoute: String?,
    onItemClick: (String) -> Unit
) {
    val isDark = androidx.compose.foundation.isSystemInDarkTheme()
    val bgColor = if (isDark) Color(0xFF161B22).copy(alpha = 0.8f) else Color.White.copy(alpha = 0.9f)
    
    NavigationBar(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        containerColor = bgColor,
        tonalElevation = 8.dp
    ) {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemClick(screen.route) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = screen.title,
                            tint = if (isSelected) PrimaryBlue else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                        AnimatedVisibility(
                            visible = isSelected,
                            enter = expandVertically(),
                            exit = shrinkVertically()
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .size(width = 20.dp, height = 3.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryBlue)
                            )
                        }
                    }
                },
                label = { Text(screen.title, color = if (isSelected) PrimaryBlue else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseContent(onAdd: (String, Double, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Food") }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add New Expense", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Expense Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category (e.g. Rent, Food, Transport)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = {
                val parsedAmount = amount.toDoubleOrNull()
                if (name.isNotBlank() && parsedAmount != null) {
                    onAdd(name, parsedAmount, category)
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
        ) {
            Text("Save Transation")
        }
    }
}
