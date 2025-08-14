package com.martinezzf.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.graphicsLayer
import com.martinezzf.lab4.ui.theme.Lab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Plataformas UVG",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()              // Ocupa toda la pantalla
            .background(Color.White)
            .padding(1.dp),             // Margen
        contentAlignment = Alignment.Center // Centra
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth(1f)                                   // Ocupa todo el ancho que pueda
                .wrapContentHeight()                                // Alto segun lo que se ponga
                .border(6.dp, Color(0xFF2E7D32), RoundedCornerShape(0.dp)) // margen verde
                .background(Color.White)                            // Fondo de la hoja
                .padding(20.dp)                                     // Padding despues el margen
        ) {
                Image(
                    painter = painterResource(id = R.drawable.logouvg),
                    contentDescription = null,
                    contentScale = ContentScale.Fit , // para que no se deforme la imagen
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(alpha = 0.1f)// la transparencia
                )

                Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Universidad del Valle\nde Guatemala",
                    fontSize = 35.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    lineHeight = 50.sp
                )

                Spacer(Modifier.height(30.dp)) // separa las lineas

                Text(
                    text = "Programación de plataformas \n móviles,  Sección 30",
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 40.sp
                )

                Spacer(Modifier.height(30.dp)) // separa las lineas

                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "INTEGRANTES",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Column(modifier = Modifier.weight(1f),horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Ariana Martínez", fontSize = 18.sp, textAlign = TextAlign.Center)
                        Text("Juan Durini", fontSize = 18.sp, textAlign = TextAlign.Center)
                    }
                }

                Spacer(Modifier.height(16.dp)) // separa las lineas

                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "CATEDRÁTICO",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Column(modifier = Modifier.weight(1f),horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Juan Carlos Durini", fontSize = 18.sp, textAlign = TextAlign.Center)
                    }
                }

                Spacer(Modifier.height(30.dp)) // separa las lineas

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Adriana Martinez", fontSize = 20.sp)
                    Text("24086", fontSize = 20.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab4Theme {
        Greeting("Android")
    }
}
