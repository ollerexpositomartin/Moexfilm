package com.example.moexfilm.util

import com.example.moexfilm.models.data.mediaObjects.Episode

object MediaUtil {

    fun msToMinutes(ms:Long):Long {
        return ms / 60000
    }

    fun minutesToMs(minutes:Long):Long {
        return minutes * 60000
    }

    fun minToHoursAndMinutes(min:Long):String{
        val hours = min/60
        val minutes = min%60
        return "${hours}h ${minutes}m"
    }

    fun createNameEpisodeInProgress(episode: Episode):String{
        return "${episode.tvShowName} ${episode.season_number} x ${episode.episode_number}"
    }

}