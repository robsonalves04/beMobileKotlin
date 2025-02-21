package com.example.bemobilekotlin.componentes

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.bemobilekotlin.R
import androidx.compose.ui.platform.LocalContext
import com.example.bemobilekotlin.screen.SearchScreen


@Composable
fun OnBoard() {
    val context = LocalContext.current
    Column(
        Modifier.background(Color(0xFF0500FD))

    ) {
        //== Logo da Empresa
        Box(Modifier) {
            Image(
                painter = painterResource(id = R.drawable.employees2),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {val intent = Intent(context, SearchScreen::class.java)
                        context.startActivity(intent)},
            )
        }
    }
}