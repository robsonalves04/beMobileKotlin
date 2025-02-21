package com.example.bemobilekotlin.componentes

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun EmployeeCard(
    nome: String,
    imageUrl: String,
    cargo: String,
    data: String,
    telefone: String
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(BorderStroke(1.dp, Color(0XFFDFDFDF)))
    ) {
        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagem do usuário
            AsyncImage(
                model = imageUrl,
                contentDescription = "Imagem do usuário",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
            )
            Spacer(Modifier.width(16.dp))
            // Nome do usuário
            Text(
                text = nome,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            // Ícone de expandir/recolher
            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Expandir/Recolher",
                    tint = Color(0xFF0500FF),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        // Se expandido, mostra os detalhes
        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                DetailRow(label = "Cargo", value = cargo)
                DetailRow(label = "Data de admissão", value = formatDate(data))
                DetailRow(label = "Telefone", value = formatPhoneNumber(telefone))
            }
        }
    }
}
// Informações detalhadas com formatação
@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold, color = Color.Black)
        Text(text = value, color = Color.Black)
    }
}
// Formata para "dd/MM/yyyy"
@SuppressLint("NewApi")
fun formatDate(isoDate: String): String {
    val parsedDate = LocalDate.parse(isoDate.substring(0, 10))
    return parsedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}
// Formata para "+55 (11) 91235-0000"
fun formatPhoneNumber(phone: String): String {
    if (phone.length == 13) {
        val countryCode = phone.substring(0, 2)
        val areaCode = phone.substring(2, 4)
        val prefix = phone.substring(4, 9)
        val suffix = phone.substring(9)
        return "+$countryCode ($areaCode) $prefix-$suffix"
    }
    return phone
}