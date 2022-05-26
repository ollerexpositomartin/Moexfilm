package com.example.moexfilm.models.interfaces.services

import com.example.moexfilm.models.data.responseObjects.GDriveResponse
import com.example.moexfilm.models.data.responseObjects.VideoMetadataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz para realizar peticiones a la API de Google Drive
 */
interface GDriveService {

    /**
     * Obtiene los subItems de una carpeta
     * @param corpora Agrupaciones de archivos a los que se aplica la consulta
     * @param q consulta
     * @param pageToken El token para continuar una solicitud de lista anterior en la página siguiente
     * @param pageSize numero de elementos por pagina
     * @param supportsTeamDrive si se quiere obtener los subItems de una carpeta de un TeamDrive
     * @param includeItemsFromAllDrives si se quiere obtener los subItems de una carpeta de un TeamDrive
     * @param supportsAllDrives si quiere soportar todos los drives
     * @param fields campos a consultar
     * @param accessToken token de acceso
     * @return devuelve una respuesta con los subItems de una carpeta
     */
    @GET("/drive/v3/files")
    suspend fun getChildItems(
        @Query("corpora")corpora:String,
        @Query("q") q: String,
        @Query("pageToken") pageToken: String,
        @Query("pageSize") pageSize: Int,
        @Query("supportsTeamDrive") supportsTeamDrive: Boolean,
        @Query("includeItemsFromAllDrives") includeItemsFromAllDrives:Boolean,
        @Query("supportsAllDrives") supportsAllDrives:Boolean,
        @Query("fields") fields: String,
        @Header("Authorization") accessToken:String
    ): Response<GDriveResponse>

    /**
     * Obtiene los teamDrives de un usuario
     * @param q consulta
     * @param pageToken El token para continuar una solicitud de lista anterior en la página siguiente
     * @param pageSize numero de elementos por pagina
     * @param accessToken token de acceso
     * @return devuelve una respuesta con los teamsDrives del usuario
     */
    @GET("/drive/v3/drives")
    suspend fun getTeamDrives(
        @Query("q") q: String,
        @Query("pageToken") pageToken: String,
        @Query("pageSize") pageSize: Int,
        @Header("Authorization") accessToken:String
    ): Response<GDriveResponse>

    /**
     * Obtiene los metadatos de un archiv
     * @param fileId El id del archivo
     * @param supportsAllDrives si quiere soportar todos los drives
     * @param fields campos a consultar
     * @param accessToken token de acceso
     * @return devuelve una respuesta con metadatos del archivo
     */
    @GET("/drive/v3/files/{fileId}")
    suspend fun getVideoMetadata(
        @Path("fileId") fileId:String,
        @Query("supportsAllDrives") supportsAllDrives:Boolean,
        @Query("fields") fields:String,
        @Header("Authorization") accessToken: String
    ):Response<VideoMetadataResponse>

}