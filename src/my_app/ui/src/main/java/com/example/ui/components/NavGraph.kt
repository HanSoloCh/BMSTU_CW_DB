package com.example.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ui.navigation.Screen
import com.example.ui.screens.add_entity.AddEntityScreen
import com.example.ui.screens.author_books.AuthorBooksScreen
import com.example.ui.screens.bbk_books.BbkBooksScreen
import com.example.ui.screens.book_detail.BookDetailScreen
import com.example.ui.screens.book_list.BookListScreen
import com.example.ui.screens.login.LoginScreen
import com.example.ui.screens.search_books.SearchBooksScreen
import com.example.ui.screens.user_favorite.UserFavoriteScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.BookList.route,
        modifier = modifier
    ) {
        composable(Screen.BookList.route) {
            BookListScreen(navController = navController)
        }
        composable(route = Screen.BookDetail.route) {
            BookDetailScreen(navController = navController)
        }
        composable(
            route = Screen.AuthorBooks.route,
            arguments = listOf(navArgument("authorId") { type = NavType.StringType })
        ) {
            AuthorBooksScreen(navController = navController)
        }
        composable(
            route = Screen.BbkBooks.route,
            arguments = listOf(navArgument("bbkId") { type = NavType.StringType })
        ) {
            BbkBooksScreen(navController = navController)
        }
        composable(
            route = Screen.SearchResults.route,
            arguments = listOf(navArgument("query") { type = NavType.StringType })
        ) {
            SearchBooksScreen(navController = navController)
        }
        composable(
            route = Screen.Login.route,
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Screen.UserFavorite.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) {
            UserFavoriteScreen(navController = navController)
        }
        composable(
            route = Screen.AddEntity.route,
        ) {
            AddEntityScreen(navController = navController)
        }
    }
}