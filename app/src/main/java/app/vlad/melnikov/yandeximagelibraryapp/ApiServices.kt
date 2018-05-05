package app.vlad.melnikov.yandeximagelibraryapp

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*


interface ApiServices {
    @GET("resources")
    fun fetchDisk(@Query("path") path: String): Single<DiskResponse>

    @GET("resources/download")
    fun fetchLink(@Query("path") path: String): Observable<Link>

    @POST("resources/upload")
    @FormUrlEncoded
    fun postPhoto(@Field("path") path: String, @Field("url") url: String)
}