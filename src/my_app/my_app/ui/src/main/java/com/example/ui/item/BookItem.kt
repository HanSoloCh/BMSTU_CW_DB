package com.example.ui.item

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ui.common.json.appJson
import com.example.ui.model.BookModel
import com.example.ui.navigation.Screen

@Composable
fun BookItem(book: BookModel, navController: NavController) {
    Card(
        onClick = {
            val bookJson = appJson.encodeToString(BookModel.serializer(), book)
            navController.currentBackStackEntry?.savedStateHandle?.set("book", bookJson)
            navController.navigate(Screen.BookDetail.route)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(book.title, style = MaterialTheme.typography.titleMedium)
            book.annotation?.let { annotation ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = annotation,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}