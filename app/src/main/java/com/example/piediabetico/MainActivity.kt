package com.example.piediabetico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.piediabetico.ui.theme.PiediabeticoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PiediabeticoTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var score by remember { mutableStateOf(0) }
    val scrollState = rememberScrollState()

    // Variables para los checkboxes
    val checkboxValues = remember {
        mutableStateListOf(
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false),
            mutableStateOf(false)
        )
    }

    var calculating by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "VALORES PREDICTORES PARA AMPUTACION EN PIE DIABETICO SEGÚN GUIA IWGDF",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Section(title = "¿El paciente cuenta con una úlcera de espesor total?") {
            CheckboxItem(text = "Sí", tag = 3, isChecked = checkboxValues[0]) {
                checkboxValues[0].value = it
            }
            CheckboxItem(text = "No", tag = 0, isChecked = checkboxValues[1]) {
                checkboxValues[1].value = it
            }
        }

        Section(title = "El paciente tiene riesgo de desarrollar neuropatía periférica si uno o más de los siguientes test es positivo") {
            CheckboxItem(text = "Test Monofilamento", tag = 1, isChecked = checkboxValues[2]) {
                checkboxValues[2].value = it
            }
            CheckboxItem(text = "Test Vibración con Diapasón", tag = 1, isChecked = checkboxValues[3]) {
                checkboxValues[3].value = it
            }
            CheckboxItem(text = "Test Táctil de Ipswich", tag = 1, isChecked = checkboxValues[4]) {
                checkboxValues[4].value = it
            }
        }

        Section(title = "El paciente cuenta con alteración vascular si uno o ambos pulsos están ausentes") {
            CheckboxItem(text = "Pulso Tibial Posterior", tag = 1, isChecked = checkboxValues[5]) {
                checkboxValues[5].value = it
            }
            CheckboxItem(text = "Pulso Pedio", tag = 1, isChecked = checkboxValues[6]) {
                checkboxValues[6].value = it
            }
        }

        Section(title = "¿El paciente presenta uno o más de las siguientes alteraciones en los pies?") {
            CheckboxItem(text = "Dedos en Garra o Martillo", tag = 1, isChecked = checkboxValues[7]) {
                checkboxValues[7].value = it
            }
            CheckboxItem(text = "Prominencias Óseas", tag = 1, isChecked = checkboxValues[8]) {
                checkboxValues[8].value = it
            }
            CheckboxItem(text = "Limitación del Movimiento", tag = 1, isChecked = checkboxValues[9]) {
                checkboxValues[9].value = it
            }
        }

        Section(title = "¿El paciente presenta uno o más de los siguientes signos de mala higiene en los pies?") {
            CheckboxItem(text = "Mal Corte de Uñas o Ausencia de Este", tag = 1, isChecked = checkboxValues[10]) {
                checkboxValues[10].value = it
            }
            CheckboxItem(text = "Mala Asepsia de los Pies", tag = 1, isChecked = checkboxValues[11]) {
                checkboxValues[11].value = it
            }
            CheckboxItem(text = "Infección Superficial por Hongos", tag = 2, isChecked = checkboxValues[12]) {
                checkboxValues[12].value = it
            }
            CheckboxItem(text = "Calzado Inapropiado en Actividades Cotidianas o Ausencia de Este", tag = 2, isChecked = checkboxValues[13]) {
                checkboxValues[13].value = it
            }
            CheckboxItem(text = "Ninguna de las Anteriores", tag = 0, isChecked = checkboxValues[14]) {
                checkboxValues[14].value = it
            }
        }

        // Nueva sección agregada
        Section(title = "¿El paciente presenta uno o más de los siguientes items?") {
            CheckboxItem(text = "Rubor en el pie durante la bipedestación y/o al tener los miembros inferiores suspendidos", tag = 2, isChecked = checkboxValues[15]) {
                checkboxValues[15].value = it
            }
            CheckboxItem(text = "Antecedente de úlcera previa", tag = 2, isChecked = checkboxValues[16]) {
                checkboxValues[16].value = it
            }
            CheckboxItem(text = "Antecedente de amputación previa en pie afectado", tag = 3, isChecked = checkboxValues[17]) {
                checkboxValues[17].value = it
            }
            CheckboxItem(text = "Ninguna de las Anteriores", tag = 0, isChecked = checkboxValues[18]) {
                checkboxValues[18].value = it
            }
        }

        Button(
            onClick = {
                calculating = true
                score = calculateScore(checkboxValues)
                checkboxValues.forEach { it.value = false } // Desmarcar todas las opciones
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Calcular")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (calculating) {
            Text(
                text = "Puntaje total: $score",
                fontSize = 18.sp
            )
            Text(
                text = "Categoría de riesgo: ${calculateRiskCategory(score)}",
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun Section(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()
    }
}

@Composable
fun CheckboxItem(text: String, tag: Int, isChecked: MutableState<Boolean>, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = { onCheckedChange(it) },
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(text = text)
    }
}

fun Boolean.toInt() = if (this) 1 else 0

fun calculateScore(checkboxValues: List<MutableState<Boolean>>): Int {
    var score = 0
    for ((index, value) in checkboxValues.withIndex()) {
        when (index) {
            0 -> score += if (value.value) 3 else 0  // Úlcera de espesor total
            1 -> score += if (value.value) 0 else 0  // No úlcera de espesor total (se deja como ejemplo, aunque no suma puntos)
            2, 3, 4 -> score += if (value.value) 1 else 0  // Tests para neuropatía periférica
            5, 6 -> score += if (value.value) 1 else 0  // Alteración vascular
            7, 8, 9 -> score += if (value.value) 1 else 0  // Alteraciones en los pies
            10, 11 -> score += if (value.value) 1 else 0  // Signos de mala higiene en los pies
            12 -> score += if (value.value) 2 else 0  // Infección superficial por hongos
            13 -> score += if (value.value) 2 else 0  // Calzado inapropiado
            14 -> score += if (!value.value) 0 else 0  // Ninguna de las anteriores (último checkbox)
            15 -> score += if (value.value) 2 else 0  // Rubor en el pie
            16 -> score += if (value.value) 2 else 0  // Antecedente de úlcera previa
            17 -> score += if (value.value) 3 else 0  // Antecedente de amputación previa
            18 -> score += if (!value.value) 0 else 0  // Ninguna de las anteriores (último checkbox)
        }
    }
    return score
}

fun calculateRiskCategory(score: Int): String {
    return when (score) {
        in 0..8 -> "Riesgo Bajo"
        in 9..16 -> "Riesgo Moderado"
        in 17..24 -> "Riesgo Alto"
        else -> "Riesgo Muy Alto"
    }
}
