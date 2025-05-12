package com.example.api_controladorresidencia

import android.os.Bundle


import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


import com.example.api_controladorresidencia.Interfaces.RetrofitClient
import com.example.api_controladorresidencia.Modelos.InquilinoM
import com.example.api_controladorresidencia.ui.theme.API_controladorResidenciaTheme



import android.util.Log

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            API_controladorResidenciaTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val call = RetrofitClient.instancia.obtenerInquilinos()

        call.enqueue(object : Callback<List<InquilinoM>> {
            override fun onResponse(call: Call<List<InquilinoM>>, response: Response<List<InquilinoM>>) {
                if (response.isSuccessful) {
                    val lista = response.body()
                    lista?.forEach {
                        Log.d("API", "Nombre: ${it.nombre}, Torre: ${it.num_Torre}")
                    }
                } else {
                    Log.e("API", "Error de respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<InquilinoM>>, t: Throwable) {
                Log.e("API", "Error: ${t.message}")
            }
        })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    API_controladorResidenciaTheme {
        Greeting("Android")
    }
}