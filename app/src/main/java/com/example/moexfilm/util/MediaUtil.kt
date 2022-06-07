package com.example.moexfilm.util

import com.example.moexfilm.models.data.mediaObjects.Episode

object MediaUtil {

    /**
     * Metodo que convierte tiempo en milisegundo a minutos
     * @param ms tiempo en milisegundos
     * @return tiempo en minutos
     */
    fun msToMinutes(ms:Long):Long {
        return ms / 60000
    }

    /**
     * Metodo que convierte tiempo en minutos a milisegundos
     * @param minutes tiempo en minutos
     * @return tiempo en milisegundos
     */
    fun minutesToMs(minutes:Long):Long {
        return minutes * 60000
    }

    /**
     * Metodo que convierte minutos a horas y minutos
     * @param min tiempo en minutos
     * @return tiempo en horas y minutos
     */
    fun minToHoursAndMinutes(min:Long):String{
        val hours = min/60
        val minutes = min%60
        return "${hours}h ${minutes}m"
    }

    /**
     * Metodo que devuelve un nombre adecuado para volver a reproducir un episodio
     * @param episode episodio
     * @return nombre con formato adecuado
     */
    fun createNameEpisodeInProgress(episode: Episode):String{
        return "${episode.tvShowName} ${episode.season_number} x ${episode.episode_number}"
    }

}