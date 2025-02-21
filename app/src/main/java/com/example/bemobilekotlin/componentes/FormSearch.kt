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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bemobilekotlin.viewModels.EmployeeViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun FormSearch() {
    val employeeViewModel: EmployeeViewModel = getViewModel()
    val searchQuery = remember { mutableStateOf("") }
    val employees by employeeViewModel._dados.observeAsState(emptyList())
    val context = LocalContext.current

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
        //atualizando/obtendo a lista da pasta assets
        employeeViewModel.fetchEmployeesFromJson(context)
        //atualizando/obtendo a lista do arquivo clonado
        employeeViewModel.fetchEmployees()
        //atualizando de acordo com a pesquisa dinamica
        employeeViewModel.searchByName(searchQuery.value)
        employees.forEach { employee ->
            Log.d("Pesquisa", "Pesquisando: ${employee.id}, ${employee.name}")
        }
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
                        .offset(x = (-6).dp, y = (8).dp)
                        .size(19.dp)
                        .clip(CircleShape)
                        .background(Color.Blue),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "02",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center

                    )
                }
            }
        }
        Column(
            Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())) {
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
                    employeeViewModel.fetchEmployeesFromJson(context)
                    Log.d("Pesquisa", "Pesquisando: ${searchQuery.value}")
                    Log.d(
                        "Pesquisa json",
                        "Colaboradores: ${employeeViewModel.fetchEmployeesFromJson(context)}"
                    )
                },
            )
            Spacer(Modifier.height(16.dp))
            //barra de orientação
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEDEFFB))
                    .border(BorderStroke(1.dp, Color(0XFFDFDFDF)))
            ) {
                Row(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Foto",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = "Nome",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "\u25CF",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(Modifier.padding(end = 16.dp))
                }
            }
            Column(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxSize()

            ) {
                // Lista de funcionários (filtrada ou completa)
                Column(Modifier) {
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