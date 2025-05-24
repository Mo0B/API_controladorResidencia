package com.example.api_controladorresidencia.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_controladorresidencia.viewmodel.LoginViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.example.api_controladorresidencia.data.ControlSesion
import com.example.api_controladorresidencia.data.network.RetrofitClient
import com.example.api_controladorresidencia.data.repository.LoginR
import com.example.api_controladorresidencia.viewmodel.LoginViewModelFactory

private val api = RetrofitClient.instancia
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current

    val viewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(
            repository = LoginR(api),
            controlSesion = ControlSesion(context)
        )
    )

    val loginSuccess = viewModel.loginSuccess.observeAsState()
    val loginError = viewModel.loginError.observeAsState()

    val usuarioState = remember { mutableStateOf("") }
    val contrasenaState = remember { mutableStateOf("") }


    LaunchedEffect(loginSuccess.value) {
        if (loginSuccess.value == true) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Iniciar sesión",
                style = MaterialTheme.typography.headlineMedium
            )

            OutlinedTextField(
                value = usuarioState.value,
                onValueChange = { usuarioState.value = it },
                label = { Text("Usuario", style = MaterialTheme.typography.labelLarge) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = contrasenaState.value,
                onValueChange = { contrasenaState.value = it },
                label = { Text("Contraseña", style = MaterialTheme.typography.labelLarge) },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.login(usuarioState.value, contrasenaState.value)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ingresar")
            }

            loginError.value?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}




