package com.example.bemobilekotlin.componentes

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bemobilekotlin.viewModels.EmployeeViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun FormSearch() {
    val employeeViewModel: EmployeeViewModel = getViewModel()
    val searchQuery = remember { mutableStateOf("") }
    val employees by employeeViewModel._dados.observeAsState(emptyList())

    // Lista de funcionários filtrada ou completa
    val filteredEmployees = if (searchQuery.value.isBlank()) {
        employees
    } else {
        employees.filter {
            it.name.contains(searchQuery.value, ignoreCase = true)
        }
    }
    // Atualiza a lista com base na pesquisa
    LaunchedEffect(Unit) {
        employeeViewModel.fetchEmployees()
        employeeViewModel.searchByName(searchQuery.value)
        employees.forEach { employee ->
        Log.d("Pesquisa", "Pesquisando: ${employee.id}, ${employee.name}")}
    }

    Column(
        Modifier.padding(top = 60.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            Arrangement.SpaceBetween
        )
        {
            Box(
                Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(color = Color(0xFFECEFF1))
                    .clickable { },
                Alignment.Center
            ) {
                Text("CG", fontSize = 22.sp, fontWeight = FontWeight.Normal)
            }
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                // Ícone de notificação
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notificações",
                    tint = Color(0xFF10100F),
                    modifier = Modifier.size(35.dp)
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x =(-6).dp, y = (8).dp)
                        .size(19.dp)
                        .clip(CircleShape)
                        .background(Color.Blue),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "02",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Column(Modifier.padding(16.dp)) {
            Text("Funcionários", fontSize = 24.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(16.dp))
            // Campo de busca
            TextFieldSearch(
                searchQuery = searchQuery,
                placeholder = "Pesquisar por nome",
                onValueChange = {
                    searchQuery.value = it
                },
                onEnterPressed = {
                    employeeViewModel.fetchEmployees()
                    Log.d("Pesquisa", "Pesquisando: ${searchQuery.value}")
                    Log.d("Pesquisa", "Colaboradores: ${employeeViewModel.fetchEmployees()}")
                },
            )
            Spacer(Modifier.height(16.dp))
            Column(
                Modifier
                    .padding(8.dp)
            ) {
                Row(
                    Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .background(color = Color(0xFFEDEFFB))
                        .border(BorderStroke(1.dp, color = Color(0XFFDFDFDF))),
                ) {
                    Spacer(Modifier.width(16.dp))
                    Text("Foto", Modifier.align(Alignment.CenterVertically))
                    Spacer(Modifier.width(40.dp))
                    Text("Nome", Modifier.align(Alignment.CenterVertically))
                    Text(
                        "\u25CF",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 190.dp),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                // Lista de funcionários (filtrada ou completa)
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    if (filteredEmployees.isEmpty()) {
                        Text("Nenhum colaborador encontrado", Modifier.padding(8.dp))
                    } else {
                        filteredEmployees.forEach { employee ->
                            EmployeeCard(
                                nome = employee.name,
                                data = employee.admission_date,
                                cargo = employee.job,
                                imageUrl = employee.image,
                                telefone = employee.phone
                            )
                        }
                    }
                }
            }
        }
    }
}