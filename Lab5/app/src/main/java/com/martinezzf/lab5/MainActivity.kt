package com.martinezzf.lab5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.martinezzf.lab5.ui.theme.Lab5Theme
import java.net.URLEncoder
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.Locale

// --- Para que tenga toques rosas ---
@Composable
fun accentPink(): Color =
    if (isSystemInDarkTheme()) Color(0xFFF48FB1) else Color(0xFFE91E63)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Lab5Theme { Main() } }
    }
}

@Composable
fun Main() {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Banner()
            Spacer(Modifier.height(16.dp))
            EncabezadoCumple()
            Spacer(Modifier.height(16.dp))
            RestaurantFav()
        }
    }
}

//Composable 1: Banner
@Composable
fun Banner() {
    val context = LocalContext.current
    val accent = accentPink()
    val playUrl = "https://play.google.com/store/apps/details?id=com.tapblaze.coffeebusiness"

    Surface(tonalElevation = 1.dp, shape = RoundedCornerShape(12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Update, contentDescription = null, tint = accent)
                Spacer(Modifier.width(8.dp))
                Text("Actualización disponible")
            }
            TextButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(playUrl))
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.textButtonColors(contentColor = accent)
            ) { Text("Descargar") }
        }
    }
}

// Composable 2: Encabezado con fecha
@Composable
fun EncabezadoCumple() {
    val accent = accentPink()
    val localeEs = Locale("es", "GT")
    val birthdayDay = 19
    val birthdayMonth = Month.FEBRUARY

    val date = remember { LocalDate.of(LocalDate.now().year, birthdayMonth, birthdayDay) }
    val dia = remember(date) {
        date.format(DateTimeFormatter.ofPattern("EEEE", localeEs))
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(localeEs) else it.toString() }
    }
    val fecha = remember(date) {
        date.format(DateTimeFormatter.ofPattern("d 'de' MMMM", localeEs))
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(dia, style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.height(4.dp))
            Text(fecha, style = MaterialTheme.typography.bodyMedium)
        }
        OutlinedButton(
            onClick = { /* opcional */ },
            colors = ButtonDefaults.outlinedButtonColors(contentColor = accent),
            border = BorderStroke(1.dp, accent)
        ) { Text("Terminar jornada") }
    }
}

//  Composable 3: Card del restauranto
@Composable
fun RestaurantFav() {
    val context = LocalContext.current
    val accent = accentPink()

    // Datos
    val name = "Rincón Del Steak"
    val address = "5ta Avenida, 10-30 Zona 9"
    val schedule = "11:00AM 9:00PM"
    val fullName = "Adriana Martinez Fuentes"
    val foodType = "SteakHouse"
    val priceTier = "Normal: QQ"


    val onIniciar = {
        Toast.makeText(context, fullName, Toast.LENGTH_SHORT).show()
    }
    val onDetalles = {
        Toast.makeText(context, "Tipo: $foodType\nPrecio: $priceTier", Toast.LENGTH_SHORT).show()
    }
    val onDirections = {
        val lat = 14.6037912
        val lng = -90.5217971
        val label = URLEncoder.encode(name, "UTF-8")
        val uri = Uri.parse("geo:0,0?q=$lat,$lng($label)")
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            setPackage("com.google.android.apps.maps")
        }
        try {
            context.startActivity(intent)
        } catch (_: android.content.ActivityNotFoundException) {
            val web = Uri.parse("https://www.google.com/maps/search/?api=1&query=$lat,$lng")
            context.startActivity(Intent(Intent.ACTION_VIEW, web))
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box {
            Column(Modifier.padding(16.dp)) {
                Text(name, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(4.dp))
                Text(
                    address,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    schedule,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onIniciar,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = accent,
                            contentColor = Color.White
                        )
                    ) { Text("Iniciar") }
                    TextButton(
                        onClick = onDetalles,
                        colors = ButtonDefaults.textButtonColors(contentColor = accent)
                    ) { Text("Detalles") }
                }
            }
            IconButton(
                onClick = onDirections,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Directions,
                    contentDescription = "Direcciones",
                    tint = accent
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    Lab5Theme { Main() }
}