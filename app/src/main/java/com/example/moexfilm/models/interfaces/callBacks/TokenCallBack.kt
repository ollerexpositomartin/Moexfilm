package com.example.moexfilm.models.interfaces.callBacks

import com.example.moexfilm.models.data.Token

/**
 * Interfaz para los callbacks de Autenticación
 */
interface TokenCallBack {
    fun onSucess()
    fun onFailure()
}