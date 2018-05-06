package app.vlad.melnikov.yandeximagelibraryapp

import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiServices {
    @GET("resources")
    fun fetchDisk(@Query("path") path: String): Single<DiskResponse>

    @GET("resources/download")
    fun fetchLink(@Query("path") path: String): Observable<Link>

    @FormUrlEncoded
    @POST("resources/upload")
    fun postPhoto(@Field(value = "path", encoded = false) path: String, @Field("url") url: String): Call<ResponseBody>
}