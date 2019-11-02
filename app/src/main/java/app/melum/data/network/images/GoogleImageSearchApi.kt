package app.melum.data.network.images

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleImageSearchApi {

    @GET("wiki/{path}")
    suspend fun search(@Path("path") path: String): ResponseBody
}

interface MusicbrainzApi {


    @GET("ws/2/artist/{id}")
    suspend fun artistInfo(@Path("id") mbid: String, @Query("inc") tbm: String = "url-rels"): ResponseBody

    //XML WikiURL: /metadata/relation-list/relation[type=image]

}