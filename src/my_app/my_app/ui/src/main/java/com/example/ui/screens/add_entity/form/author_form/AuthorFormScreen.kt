package com.example.ui.screens.add_entity.form.author_form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ui.screens.add_entity.AddEntityViewModel

@Composable
fun AuthorFormScreen(viewModel: AddEntityViewModel) {
    val form = viewModel.authorForm
    Column {
        OutlinedTextField(
            value = form.name,
            onValueChange = { viewModel.authorForm = form.copy(name = it) },
            label = { Text("Имя автора") },
            modifier = Modifier.fillMaxWidth(),
            isError = form.name.isNotBlank()
        )
    }
}