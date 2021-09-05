
import com.opencode.movies.Models.CreditsResponse
import com.opencode.movies.Models.MovieResponse
import com.opencode.movies.Models.SearchModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface   RetrofitInterface{

      @GET("3/movie/{id}")
      fun getFilmById(@Path("id") id: Int,
                      @Query("language") language: String?,
                      @Query("api_key") api_key: String?,
                          ): Single<MovieResponse>

      @GET("3/search/movie")
      fun getSearchResult(@Query("language") language: String?,
                          @Query("api_key") api_key: String?,
                          @Query("query") search: String?
                          ): Single<SearchModel>

    @GET("3/movie/{id}/credits")
    fun getCreditsById(@Path("id") id: Int,
                    @Query("language") language: String?,
                    @Query("api_key") api_key: String?,
    ): Single<CreditsResponse>

}