package com.example.ui.screens.add_entity

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ui.screens.add_entity.form.EntityType
import com.example.ui.screens.add_entity.form.author_form.AuthorFormScreen
import com.example.ui.screens.add_entity.form.book_form.BookFormScreen
import com.example.ui.screens.add_entity.form.publisher_form.PublisherFormScreen

@Composable
fun AddEntityScreen(
    navController: NavController,
    viewModel: AddEntityViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val selectedType by remember { viewModel::selectedType }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Добавить сущность", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Выбор типа сущности
        EntityTypeDropdown(
            selectedType = selectedType,
            onTypeSelected = { viewModel.selectedType = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedType) {
            EntityType.AUTHOR -> AuthorFormScreen(viewModel)
            EntityType.BOOK -> BookFormScreen(viewModel)
            EntityType.PUBLISHER -> PublisherFormScreen(viewModel)
            else -> Text("Пока не поддерживается")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.addEntity() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сохранить")
        }

        if (state is AddEntityState.Error) {
            Text(
                text = (state as AddEntityState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
            println((state as AddEntityState.Error).message)
        }

        if (state is AddEntityState.Success) {
            Text("Успешно добавлено", color = MaterialTheme.colorScheme.primary)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntityTypeDropdown(selectedType: EntityType, onTypeSelected: (EntityType) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val types = EntityType.entries

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedType.displayName,
            onValueChange = {},
            readOnly = true,
            label = { Text("Тип сущности") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryEditable)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            types.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.displayName) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}
