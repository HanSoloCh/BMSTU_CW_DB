package com.example.ui.screens.add_entity.form.book_form

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
fun BookFormScreen(viewModel: AddEntityViewModel) {
    val form = viewModel.bookForm
    Column {
        OutlinedTextField(
            value = form.title,
            onValueChange = { viewModel.bookForm = form.copy(title = it) },
            label = { Text("Название книги*") },
            isError = form.title.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = form.authors[0],
            onValueChange = { viewModel.bookForm = form.copy(authors = listOf(it)) },
            label = { Text("Автор") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = form.annotation,
            onValueChange = { viewModel.bookForm = form.copy(annotation = it) },
            label = { Text("Аннотация") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = form.publisher,
            onValueChange = { viewModel.bookForm = form.copy(publisher = it) },
            label = { Text("Издатель") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = form.publicationYear,
            onValueChange = {
                if (it.all { ch -> ch.isDigit() })
                    viewModel.bookForm = form.copy(publicationYear = it)
            },
            label = { Text("Год издания") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = form.codeISBN,
            onValueChange = { viewModel.bookForm = form.copy(codeISBN = it) },
            label = { Text("ISBN") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = form.bbk,
            onValueChange = { viewModel.bookForm = form.copy(bbk = it) },
            label = { Text("ББК*") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = form.volume,
            onValueChange = { viewModel.bookForm = form.copy(volume = it) },
            label = { Text("Объем (стр.)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = form.copies,
            onValueChange = {
                if (it.all { ch -> ch.isDigit() })
                    viewModel.bookForm = form.copy(copies = it)
            },
            label = { Text("Количество экземпляров*") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = form.copies.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )

        // TODO: добавить поиск по авторам, издателям, ББК и связывать по UUID
        Text("Выбор авторов, издателя и ББК реализуется отдельно")
    }
}
