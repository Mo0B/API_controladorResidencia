package com.example.api_controladorresidencia.data.network

import android.util.Log
import com.example.api_controladorresidencia.data.ControlSesion
import com.example.api_controladorresidencia.data.Request.EntradaRequest
import com.example.api_controladorresidencia.data.Request.VisitanteRequest
import com.example.api_controladorresidencia.data.model.CorrespondenciaM
import com.example.api_controladorresidencia.data.model.EntradaM
import com.example.api_controladorresidencia.data.model.EntradaVehiculoM
import com.example.api_controladorresidencia.data.model.InquilinoM
import com.example.api_controladorresidencia.data.model.MantenimientoM
import com.example.api_controladorresidencia.data.model.ObraM
import com.example.api_controladorresidencia.data.model.VehiculoM
import com.example.api_controladorresidencia.data.model.VigilanteM
import com.example.api_controladorresidencia.data.model.VisitanteM
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("/api/correspondencia/all")
    suspend fun getCorrespondenciaS(): List<CorrespondenciaM>
    @GET("/api/entrada/all")
    suspend fun getEntradaS(): List<EntradaM>
    @GET("/api/entrada-vehiculo/all")
    suspend fun getEntradaVehiculoS(): List<EntradaVehiculoM>
    @GET("/api/inquilino/all")
    suspend fun getInquilinoS(): List<InquilinoM>
    @GET("/api/mantenimiento/all")
    suspend fun getMantenimientoS(): List<MantenimientoM>
    @GET("/api/obra/all")
    suspend fun getObraS(): List<ObraM>
    @GET("/api/vehiculo/all")
    suspend fun getVehiculoS(): List<VehiculoM>
    @GET("/api/vigilante/all")
    suspend fun getVigilanteS(): List<VigilanteM>
    @GET("/api/visitante/all")
    suspend fun getVisitanteS(): List<VisitanteM>

    @GET("/api/correspondencia/byId/{id}")
    suspend fun getCorrespondecia(@Path("id") id: Long): CorrespondenciaM
    @GET("/api/entrada/byId/{id}")
    suspend fun getEntrada(@Path("id") id: Long): EntradaM
    @GET("/api/entrada-vehiculo/byId/{id}")
    suspend fun getEntradaVehiculo(@Path("id") id: Long): EntradaVehiculoM
    @GET("/api/inquilino/byId/{id}")
    suspend fun getInquilino(@Path("id") id: Long): InquilinoM
    @GET("/api/mantenimiento/byId/{id}")
    suspend fun getMantenimiento(@Path("id") id: Long): MantenimientoM
    @GET("/api/obra/byId/{id}")
    suspend fun getObra(@Path("id") id: Long): ObraM
    @GET("/api/vehiculo/byId/{id}")
    suspend fun getVehiculo(@Path("id") id: Long): VehiculoM
    @GET("/api/vigilante/byId/{id}")
    suspend fun getVigilante(@Path("id") id: Long): VigilanteM
    @GET("/api/visitante/byId/{id}")
    suspend fun getVisitante(@Path("id") id: Long): VisitanteM

    @POST("/api/correspondencia/create")
    suspend fun saveCorrespondencia(@Body correspondencia: CorrespondenciaM): CorrespondenciaM
    @POST("/api/entrada/create")
    suspend fun saveEntrada(@Body entrada: EntradaRequest): Response<Unit>
    @POST("/api/entrada-vehiculo/create")
    suspend fun saveEntradaVehiculo(@Body entradaVehiculo: EntradaVehiculoM): EntradaVehiculoM




    @POST("/api/mantenimiento/create")
    suspend fun saveMantenimiento(@Body mantenimiento: MantenimientoM): MantenimientoM
    @POST("/api/obra/create")
    suspend fun saveObra(@Body obra: ObraM): ObraM
    @POST("/api/vehiculo/create")
    suspend fun saveVehiculo(@Body vehiculo: VehiculoM): VehiculoM
    @POST("/api/vigilante/create")
    suspend fun saveVigilante(@Body vigilante: VigilanteM): VigilanteM
    @POST("/api/visitante/create")
    suspend fun saveVisitante(@Body visitante: VisitanteM): VisitanteM

    @DELETE("/api/correspondencia/delete/{id}")
    suspend fun deleteCorrespondencia(@Path("id") id: Long)
    @DELETE("/api/entrada/delete/{id}")
    suspend fun deleteEntrada(@Path("id") id: Long)
    @DELETE("/api/entrada-vehiculo/delete/{id}")
    suspend fun deleteEntradaVehiculo(@Path("id") id: Long)
    @DELETE("/api/inquilino/delete/{id}")
    suspend fun deleteInquilino(@Path("id") id: Long)
    @DELETE("/api/mantenimiento/delete/{id}")
    suspend fun deleteMantenimiento(@Path("id") id: Long)
    @DELETE("/api/obra/delete/{id}")
    suspend fun deleteObra(@Path("id") id: Long)
    @DELETE("/api/vehiculo/delete/{id}")
    suspend fun deleteVehiculo(@Path("id") id: Long)
    @DELETE("/api/vigilante/delete/{id}")
    suspend fun deleteVigilante(@Path("id") id: Long)
    @DELETE("/api/visitante/delete/{id}")
    suspend fun deleteVisitante(@Path("id") id: Long)

    @PUT("/api/correspondencia/update/{id}")
    suspend fun updateCorrespondencia(@Path("id") id: Long, @Body correspondencia: CorrespondenciaM): CorrespondenciaM
    @PUT("/api/entrada/update/{id}")
    suspend fun updateEntrada(@Path("id") id: Long, @Body entrada: EntradaM): EntradaM
    @PUT("/api/entrada-vehiculo/update/{id}")
    suspend fun updateEntradaVehiculo(@Path("id") id: Long, @Body entradaVehiculo: EntradaVehiculoM): EntradaVehiculoM
    @PUT("/api/inquilino/update/{id}")
    suspend fun updateInquilino(@Path("id") id: Long, @Body inquilino: InquilinoM): InquilinoM
    @PUT("/api/mantenimiento/update/{id}")
    suspend fun updateMantenimiento(@Path("id") id: Long, @Body mantenimiento: MantenimientoM): MantenimientoM
    @PUT("/api/obra/update/{id}")
    suspend fun updateObra(@Path("id") id: Long, @Body obra: ObraM): ObraM
    @PUT("/api/vehiculo/update/{id}")
    suspend fun updateVehiculo(@Path("id") id: Long, @Body vehiculo: VehiculoM): VehiculoM
    @PUT("/api/vigilante/update/{id}")
    suspend fun updateVigilante(@Path("id") id: Long, @Body vigilante: VigilanteM): VigilanteM
    @PUT("/api/visitante/update/{id}")
    suspend fun updateVisitante(@Path("id") id: Long, @Body visitante: VisitanteM): VisitanteM

    @POST("/api/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("api/vigilante/me")
    suspend fun getLoggedVigilante(
        @Header("Authorization") token: String
    ): Response<VigilanteM> //


    @POST("/api/entrada/registrar")
    suspend fun registrarEntrada(@Body entrada: EntradaRequest): Response<EntradaM>

    @POST("/api/visitante/registrar")
    suspend fun registrarVisita(@Body entrada: VisitanteRequest): Response<VisitanteM>

    @POST("/api/inquilino/create")
    suspend fun saveInquilino(@Body inquilino: InquilinoM): Response<InquilinoM>


}
data class LoginRequest(
    val usuario: String,
    val contra: String
)
data class LoginResponse(
    val token: String
)


object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080"
    //private const val BASE_URL = "https://dbplatica.onrender.com"


    fun create(controlSesion: ControlSesion): ApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                val token = controlSesion.getToken() // Se lee en cada petición
                Log.d("TOKEN_INTERCEPTOR", "Usando token: $token")
                if (!token.isNullOrBlank()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")

                }
                chain.proceed(requestBuilder.build())
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}



