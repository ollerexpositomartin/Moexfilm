package com.example.moexfilm.models.interfaces.services

import com.example.moexfilm.models.data.mediaObjects.Episode
import com.example.moexfilm.models.data.mediaObjects.Movie
import com.example.moexfilm.models.data.mediaObjects.Season
import com.example.moexfilm.models.data.responseObjects.TMDBResponseCast
import com.example.moexfilm.models.data.responseObjects.TMDBResponseMovie
import com.example.moexfilm.models.data.responseObjects.TMDBResponseTvShow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz para realizar peticiones a la API de TMDBService
 */
interface TMDBService {

    /**
     * Obtiene una lista de peliculas acorde con la busqueda
     * @param api_key clave de la api
     * @param query pelicula a buscar
     * @param year año en el que se estreno la pelicula
     * @param language idioma en el que se quiere obtener la informacion
     * @return lista de peliculas
     */
    @GET("/3/search/movie")
    suspend fun searchMovie(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("year") year: String,
        @Query("language") language:String
    ): Response<TMDBResponseMovie>

    /**
     * Obtiene los detalles de una pelicula
     * @param api_key clave de la api
     * @param movie_id id de la pelicula
     * @param language idioma en el que se quiere obtener la informacion
     * @return Devuelve la Pelicula con los detalles
     */
    @GET("/3/movie/{movie_id}")
    suspend fun searchMovieDetails(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language:String
    ): Response<Movie>

    /**
     * Obtiene una lista de series acorde con la busqueda
     * @param api_key clave de la api
     * @param query serie a buscar
     * @param year año en el que se estreno la serie
     * @param language idioma en el que se quiere obtener la informacion
     * @return lista de series
     */
    @GET("/3/search/tv")
    suspend fun searchTvShow(
        @Query("api_key") api_key: String,
        @Query("query") query: String,
        @Query("first_air_date_year") year: String,
        @Query("language") language:String
    ): Response<TMDBResponseTvShow>

    /**
     * Obtiene la informacion de la temporada de una serie
     * @param api_key clave de la api
     * @param season_number numero de la temporada
     * @param tv_id id de la serie
     * @param language idioma en el que se quiere obtener la informacion
     * @return Devuelve la información de la temporada
     */
    @GET("/3/tv/{tv_id}/season/{season_number}")
    suspend fun searchTvSeason(
        @Path("tv_id") tv_id:Int,
        @Path("season_number") season_number:Int,
        @Query("api_key") api_key: String,
        @Query("language") language:String
    ): Response<Season>

    /**
     * Obtiene la informacion de un episodio de una temporada de una serie
     * @param api_key clave de la api
     * @param tv_id id de la serie
     * @param season_number numero de la temporada
     * @param episode_number numero del episodio
     * @param language idioma en el que se quiere obtener la informacion
     * @return Devuelve la información del episodio
     */
    @GET("/3/tv/{tv_id}/season/{season_number}/episode/{episode_number}")
    suspend fun searchTvEpisode(
        @Path("tv_id") tv_id:Int,
        @Path("season_number") season_number:Int,
        @Path("episode_number") episode_number:Int,
        @Query("api_key") api_key: String,
        @Query("language") language:String
    ): Response<Episode>

    /**
     * Obtiene el reparto de una pelicula
     * @param api_key clave de la api
     * @param movie_id id de la pelicula
     * @return Devuelve el reparto de la pelicula
     */
    @GET("3/movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ): Response<TMDBResponseCast>

}