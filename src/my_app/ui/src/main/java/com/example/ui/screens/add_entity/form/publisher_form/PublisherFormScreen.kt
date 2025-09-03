package com.example.ui.screens.add_entity.form.publisher_form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.ui.screens.add_entity.AddEntityViewModel

@Composable
fun PublisherFormScreen(viewModel: AddEntityViewModel) {
    val form = viewModel.publisherForm
    Column {
        OutlinedTextField(
            value = form.name,
            onValueChange = { viewModel.publisherForm = form.copy(name = it) },
            label = { Text("Название") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = form.description,
            onValueChange = { viewModel.publisherForm = form.copy(description = it) },
            label = { Text("Описание") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = form.foundationYear,
            onValueChange = { viewModel.publisherForm = form.copy(foundationYear = it) },
            label = { Text("Год основания") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = form.email,
            onValueChange = { viewModel.publisherForm = form.copy(email = it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = form.phoneNumber,
            onValueChange = { viewModel.publisherForm = form.copy(phoneNumber = it) },
            label = { Text("Телефон") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
