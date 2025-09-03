package com.example.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ui.navigation.Screen
import com.example.ui.util.UserStore

val searchBarVisibleRoutes = listOf(
    Screen.BookList.route,
    Screen.AuthorBooks.route,
    Screen.SearchResults.route,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(
    navController: NavController,
    searchText: String,
    onSearchChange: (String) -> Unit,
    onSearchSubmit: (String) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isSearchVisible = searchBarVisibleRoutes.any { currentRoute?.startsWith(it.substringBefore("/{")) == true }

    var expanded by remember { mutableStateOf(false) }

    Column {
        TopAppBar(
            title = { Text("Моя библиотека") },
            actions = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Меню")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Домой") },
                        onClick = {
                            expanded = false
                            navController.navigate(Screen.BookList.route) {
                                popUpTo(0)
                            }
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Добавить") },
                        onClick = {
                            expanded = false
                            navController.navigate(Screen.AddEntity.route)
                        }
                    )
                    if (UserStore.getRole() == null) {
                        DropdownMenuItem(
                            text = { Text("Войти") },
                            onClick = {
                                expanded = false
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(0)
                                }
                            }
                        )
                    } else {
                        DropdownMenuItem(
                            text = { Text("Избранное") },
                            onClick = {
                                expanded = false
                                navController.navigate(Screen.UserFavorite.createRoute(UserStore.getId()!!))
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Выйти") },
                            onClick = {
                                expanded = false
                                UserStore.clear()
                                navController.navigate(Screen.BookList.route) {
                                    popUpTo(0)
                                }
                            }
                        )
                    }
                }
            }
        )
        if (isSearchVisible) {
            SearchBar(
                query = searchText,
                onQueryChange = onSearchChange,
                onSearchSubmit = onSearchSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun MenuItem(text: String, onClick: () -> Unit) {
    DropdownMenuItem(
        text = { Text(text = text) },
        onClick = onClick,
    )
}
