package app.vlad.melnikov.yandeximagelibraryapp

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiServices {
    @GET("resources")
    fun fetchDisk(@Query("path") path : String): Single<DiskResponse>

    @GET("resources/download")
    fun fetchLink(@Query("path") path : String): Observable<Link>
}