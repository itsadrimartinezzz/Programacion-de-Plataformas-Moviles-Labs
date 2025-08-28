@file:OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.martinezzf.lab6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ContadorApp(nombre = "Adriana Martinez Fuentes")
                }
            }
        }
    }
}

@Composable
fun ContadorApp(nombre: String = "") {

    var contador by rememberSaveable { mutableStateOf(0) }
    var totalInc by rememberSaveable { mutableStateOf(0) }
    var totalDec by rememberSaveable { mutableStateOf(0) }
    var maxVal by rememberSaveable { mutableStateOf<Int?>(null) }
    var minVal by rememberSaveable { mutableStateOf<Int?>(null) }

    var history by rememberSaveable(
        stateSaver = listSaver(
            save = { list -> list.flatMap { listOf(it.valor, if (it.esIncremento) 1 else 0) } },
            restore = { flat -> flat.chunked(2).map { (v, f) -> HistorialItem(v as Int, (f as Int) == 1) } }
        )
    ) { mutableStateOf(emptyList<HistorialItem>()) }

    fun aplicarCambio(delta: Int) {
        val nuevo = contador + delta
        contador = nuevo
        if (delta > 0) totalInc++ else totalDec++

        if (minVal == null && maxVal == null) {
            minVal = nuevo
            maxVal = nuevo
        } else {
            if (nuevo > (maxVal ?: nuevo)) maxVal = nuevo
            if (nuevo < (minVal ?: nuevo)) minVal = nuevo
        }
        history = history + HistorialItem(nuevo, delta > 0)
    }

    fun reiniciar() {
        contador = 0
        totalInc = 0
        totalDec = 0
        maxVal = null
        minVal = null
        history = emptyList<HistorialItem>()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = nombre,
                fontSize = 31.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(40.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(26.dp)
            ) {
                BotonCircular("−") { aplicarCambio(-1) }
                Text(text = contador.toString(), fontSize = 60.sp, fontWeight = FontWeight.Bold)
                BotonCircular("+") { aplicarCambio(+1) }
            }

            Spacer(Modifier.height(40.dp))

            Column(Modifier.fillMaxWidth()) {
                EstadisticaFila("Total incrementos:", totalInc)
                EstadisticaFila("Total decrementos:", totalDec)
                EstadisticaFila("Valor máximo:", maxVal ?: 0)
                EstadisticaFila("Valor mínimo:", minVal ?: 0)
                EstadisticaFila("Total cambios:", totalInc + totalDec)
            }

            Spacer(Modifier.height(16.dp))

            if (history.isNotEmpty()) {
                Text(
                    "Historial:",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(Modifier.height(8.dp))

                FlowRow(
                    maxItemsInEachRow = 5,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally), // 👈 centrado
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    history.forEach { item ->
                        HistorialChip(valor = item.valor, esIncremento = item.esIncremento)
                    }
                }
            }
        }


        Button(
            onClick = { reiniciar() },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F7C8A)),
            shape = RoundedCornerShape(8.dp)
        ) { Text("Reiniciar", color = Color.White) }
    }
}


@Composable
fun BotonCircular(texto: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F7C8A)),
        modifier = Modifier.size(60.dp)
    ) { Text(text = texto, fontSize = 25.sp, color = Color.White) }
}

@Composable
fun EstadisticaFila(label: String, valor: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
        Text(valor.toString(), fontSize = 30.sp)
    }
}

@Composable
fun HistorialChip(valor: Int, esIncremento: Boolean) {
    val bg = if (esIncremento) Color(0xFF4CAF50) else Color(0xFFF44336)
    Box(
        modifier = Modifier
            .defaultMinSize(minWidth = 60.dp, minHeight = 50.dp) // más largo y alto
            .background(bg, RoundedCornerShape(20.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = valor.toString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}


data class HistorialItem(val valor: Int, val esIncremento: Boolean)


@Preview(showBackground = true, widthDp = 360)
@Composable
fun PreviewContadorApp() {
    MaterialTheme { ContadorApp(nombre = "Adriana Martinez Fuentes") }
}
