package app.vlad.melnikov.yandeximagelibraryapp.utils

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    companion object {

        private fun getClient(withToken: Boolean): OkHttpClient {
            return OkHttpClient.Builder().addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain?): Response {
                    val originalRequest = chain?.request()
                    val builder = originalRequest?.newBuilder()
                            ?.header(Constants.CONTENT_TYPE_HEADER, Constants.CONTENT_TYPE_VALUE)
                            ?.header("Accept-Charset", "utf-8")

                    if (withToken) {
                        builder?.header(Constants.AUTH_TYPE_HEADER, Constants.AUTH_TYPE_VALUE)
                    }

                    val newRequest = builder!!.build()
                    return chain.proceed(newRequest)
                }
            }).build()
        }

        /*
            Set param to true if request should have auth header, else set param == false
         */
        fun createWithToken(withToken: Boolean): ApiServices {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient(withToken))
                    .baseUrl(Constants.BASE_URL)

            return retrofit.build().create(ApiServices::class.java)
        }
    }
}