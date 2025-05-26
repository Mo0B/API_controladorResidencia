package com.example.api_controladorresidencia.data

import android.content.Context

class ControlSesion(context: Context) {
    private val prefs = context.getSharedPreferences("session_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }

    fun clearToken() {
        prefs.edit().remove("auth_token").apply()
    }

    fun saveVigilanteId(id: Long) {
        prefs.edit().putLong("vigilante_id", id).apply()
    }

    fun getVigilanteId(): Long {
        return prefs.getLong("vigilante_id", -1L)
    }
}
