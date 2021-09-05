

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApi {

    companion object {

        var retrofite = Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        @JvmStatic
        fun getRetrofit(): RetrofitInterface? {

            return retrofite.build().create(RetrofitInterface::class.java)
        }
    }
}