package com.example.api_controladorresidencia.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_controladorresidencia.data.ControlSesion
import com.example.api_controladorresidencia.data.Request.EntradaRequest
import com.example.api_controladorresidencia.data.Request.VisitanteRequest
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.model.InquilinoM
import com.example.api_controladorresidencia.data.model.MantenimientoM
import com.example.api_controladorresidencia.data.model.ObraM
import com.example.api_controladorresidencia.data.model.VigilanteM
import com.example.api_controladorresidencia.data.model.VisitanteM
import com.example.api_controladorresidencia.data.network.ApiService
import com.example.api_controladorresidencia.data.network.RetrofitClient
import com.example.api_controladorresidencia.data.repository.EntradaR
import com.example.api_controladorresidencia.data.repository.InquilinoR
import com.example.api_controladorresidencia.data.repository.MantenimientoR
import com.example.api_controladorresidencia.data.repository.ObraR
import com.example.api_controladorresidencia.data.repository.VisitanteR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class EntradaViewModel (
    private val entradaR: EntradaR,
    private val controlSesion: ControlSesion
) : ViewModel() {

    private val _vigilanteActual = MutableStateFlow<VigilanteM?>(null)
    val vigilanteActual: StateFlow<VigilanteM?> = _vigilanteActual

    fun setVigilanteActual(vigilante: VigilanteM) {
        _vigilanteActual.value = vigilante
    }

    private val repositoryInquilino = InquilinoR(RetrofitClient.create(controlSesion))
    private val repositoryVisitante = VisitanteR(RetrofitClient.create(controlSesion))
    private val repositoryMantenimiento = MantenimientoR(RetrofitClient.create(controlSesion))
    private val repositoryObra = ObraR(RetrofitClient.create(controlSesion))

    private val _entradas = MutableStateFlow<List<EntradaM>>(emptyList())
    val entradas: StateFlow<List<EntradaM>> = _entradas

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _entradaSeleccionada = MutableStateFlow<EntradaM?>(null)
    val entradaSeleccionada: StateFlow<EntradaM?> = _entradaSeleccionada


    private val _categoriaSeleccionada = MutableStateFlow("Inquilino")
    val categoriaSeleccionada: StateFlow<String> = _categoriaSeleccionada


    fun setCategoriaSeleccionada(categoria: String) {
        _categoriaSeleccionada.value = categoria
    }

    init {
        obtenerEntradas()
    }

    fun obtenerEntradas() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val lista = entradaR.getEntradaS()
                _entradas.value = lista
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando entradas: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun obtenerEntrada(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _entradaSeleccionada.value = entradaR.getEntrada(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Error cargando entrada: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun actualizarEntrada(id: Long, nuevaEntrada: EntradaM, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                entradaR.updateEntrada(id, nuevaEntrada)
                obtenerEntradas()
                onSuccess()
            } catch (e: Exception) {
                onError("Error al actualizar entrada: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun eliminarEntrada(id: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                entradaR.deleteEntrada(id)
                obtenerEntradas()
                onSuccess()
            } catch (e: Exception) {
                onError("Error eliminando entrada: ${e.message}")
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun setError(msg: String) {
        _error.value = msg
    }
    // oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
    fun registrarEntradaConInquilino(
        nombre: String,
        apellido: String,
        documento: String,
        telefono: String,
        apartamento: String,
        torre: String
    ) {
        viewModelScope.launch {
            val vigilanteId = controlSesion.getVigilanteId()
            if (vigilanteId == null) {
                Log.e("EntradaViewModel", "ID del vigilante no disponible")
                return@launch
            }
            try {
                val nuevoInquilino = InquilinoM(
                    nombre = nombre,
                    apellido = apellido,
                    telefono = telefono,
                    num_Documento = documento,
                    num_Torre = torre,
                    num_Apartamento = apartamento
                )
                val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
                scope.launch {
                    val inquilinoRegistrado = repositoryInquilino.saveInquilino(nuevoInquilino)
                    if (inquilinoRegistrado == null) {
                        Log.e("EntradaViewModel", "No se pudo registrar el inquilino")
                        return@launch
                    }

                    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                    val fechaActual = formatter.format(Date())
                    val entrada = EntradaRequest(

                        fecha_entrada = fechaActual,
                        categoria = "Inquilino",
                        referencia = 100L,
                        vigilante_reg = vigilanteId,
                        perInquilino = inquilinoRegistrado.id
                    )
                    Log.d("EntradaViewModel", "4 ingreso")
                    val entradaRegistrada = entradaR.registrarEntrada(entrada)
                    if (entradaRegistrada != null) {
                        Log.d("EntradaViewModel", "Entrada registrada correctamente: $entradaRegistrada")
                    } else {
                        Log.e("EntradaViewModel", "Error al registrar la entrada: respuesta nula o error del API")
                    }
                }

            } catch (e: Exception) {
                Log.e("EntradaViewModel", "Error al registrar entrada con inquilino: ${e.message}", e)
            }
        }
    }
    // oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
    @RequiresApi(Build.VERSION_CODES.O)
    fun registrarEntradaConVisitante(
        nombre: String,
        apellido: String,
        documento: String,
        telefono: String,
        razon: String,
        inquilino: Long
    ) {
        viewModelScope.launch {
            val vigilanteId = controlSesion.getVigilanteId()
            if (vigilanteId == null) {
                Log.e("EntradaViewModel", "ID del vigilante no disponible")
                return@launch
            }
            try {


                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                val fechaActual = LocalDateTime.now().format(formatter)
                Log.e("NOMBRE LLEGADO?", nombre)
                val nuevoVisitante = VisitanteRequest(
                    nombre = nombre,
                    apellido = apellido,
                    telefono = telefono,
                    num_Documento = documento,
                    razon = razon,
                    fecha_visita = fechaActual,
                    vigilante_reg = vigilanteId,
                    inquilino = inquilino
                )
                val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
                scope.launch {
                    val visitanteRegistrado = repositoryVisitante.registrarVisitante(nuevoVisitante)
                    if (visitanteRegistrado == null) {
                        Log.e("EntradaViewModel", "No se pudo registrar al visitante")
                        return@launch
                    }


                    val entrada = EntradaRequest(

                        fecha_entrada = fechaActual,
                        categoria = "Visitante",
                        referencia = 100L,
                        vigilante_reg = vigilanteId,
                        perVisitante = visitanteRegistrado.id
                    )
                    Log.d("EntradaViewModel", "4 ingreso")
                    val entradaRegistrada = entradaR.registrarEntrada(entrada)
                    if (entradaRegistrada != null) {
                        Log.d("EntradaViewModel", "Entrada registrada correctamente: $entradaRegistrada")
                    } else {
                        Log.e("EntradaViewModel", "Error al registrar la entrada: respuesta nula o error del API")
                    }
                }

            } catch (e: Exception) {
                Log.e("EntradaViewModel", "Error al registrar entrada con inquilino: ${e.message}", e)
            }
        }
    }
    // oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
    fun registrarEntradaConMantenimiento(
        nombre: String,
        apellido: String,
        documento: String,
        telefono: String,
        empresa: String,
        tipo: String
    ) {
        viewModelScope.launch {
            val vigilanteId = controlSesion.getVigilanteId()
            if (vigilanteId == null) {
                Log.e("EntradaViewModel", "ID del vigilante no disponible")
                return@launch
            }
            try {
                val nuevoMantenimiento = MantenimientoM(
                    nombre = nombre,
                    apellido = apellido,
                    telefono = telefono,
                    num_Documento = documento,
                    compania = empresa,
                    categoria = tipo
                )
                val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
                scope.launch {
                    val manRegistrado = repositoryMantenimiento.saveMantenimiento(nuevoMantenimiento)
                    if (manRegistrado == null) {
                        Log.e("EntradaViewModel", "No se pudo registrar personal de mantenimiento")
                        return@launch
                    }

                    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                    val fechaActual = formatter.format(Date())
                    val entrada = EntradaRequest(

                        fecha_entrada = fechaActual,
                        categoria = "Mantenimiento",
                        referencia = 100L,
                        vigilante_reg = vigilanteId,
                        perMantenimiento = manRegistrado.id
                    )
                    Log.d("EntradaViewModel", "4 ingreso")
                    val entradaRegistrada = entradaR.registrarEntrada(entrada)
                    if (entradaRegistrada != null) {
                        Log.d("EntradaViewModel", "Entrada registrada correctamente: $entradaRegistrada")
                    } else {
                        Log.e("EntradaViewModel", "Error al registrar la entrada: respuesta nula o error del API")
                    }
                }

            } catch (e: Exception) {
                Log.e("EntradaViewModel", "Error al registrar entrada con inquilino: ${e.message}", e)
            }
        }
    }
    // oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
    fun registrarEntradaConObra(
        nombre: String,
        apellido: String,
        documento: String,
        telefono: String,
        apartamento: String,
        torre: String
    ) {
        viewModelScope.launch {
            val vigilanteId = controlSesion.getVigilanteId()
            if (vigilanteId == null) {
                Log.e("EntradaViewModel", "ID del vigilante no disponible")
                return@launch
            }
            try {
                val nuevoInquilino = InquilinoM(
                    nombre = nombre,
                    apellido = apellido,
                    telefono = telefono,
                    num_Documento = documento,
                    num_Torre = torre,
                    num_Apartamento = apartamento
                )
                val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
                scope.launch {
                    val inquilinoRegistrado = repositoryInquilino.saveInquilino(nuevoInquilino)
                    if (inquilinoRegistrado == null) {
                        Log.e("EntradaViewModel", "No se pudo registrar el inquilino")
                        return@launch
                    }

                    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                    val fechaActual = formatter.format(Date())
                    val entrada = EntradaRequest(

                        fecha_entrada = fechaActual,
                        categoria = "Inquilino",
                        referencia = 100L,
                        vigilante_reg = vigilanteId,
                        perInquilino = inquilinoRegistrado.id
                    )
                    Log.d("EntradaViewModel", "4 ingreso")
                    val entradaRegistrada = entradaR.registrarEntrada(entrada)
                    if (entradaRegistrada != null) {
                        Log.d("EntradaViewModel", "Entrada registrada correctamente: $entradaRegistrada")
                    } else {
                        Log.e("EntradaViewModel", "Error al registrar la entrada: respuesta nula o error del API")
                    }
                }

            } catch (e: Exception) {
                Log.e("EntradaViewModel", "Error al registrar entrada con inquilino: ${e.message}", e)
            }
        }
    }
    // oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
}

