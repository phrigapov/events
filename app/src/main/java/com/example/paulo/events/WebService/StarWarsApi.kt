package com.example.paulo.events.WebService

/**
 * Created by Paulo on 20/07/2017.
 */
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import java.util.*

class StarWarsApi {
    val service: StarWarsApiDef

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder().setLenient().create()

        /*val retrofit = Retrofit.Builder()
                .baseUrl("http://swapi.co/api/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()*/

        val retrofit = Retrofit.Builder()
                .baseUrl("https://ph-events.herokuapp.com/api/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()

        service = retrofit.create<StarWarsApiDef>(StarWarsApiDef::class.java)
    }

   /* fun loadMovies(): Observable<Movie>? {
        return service.listMovies()
                .flatMap { filmResults -> Observable.from(filmResults.results) }
                .map { film ->
                    Movie(film.title, film.episodeId, ArrayList<Character>())
                }
    }*/

    fun loadEventos(): Observable<Eventol>? {
        return service.listTitle()
                .flatMap { EventlResult -> Observable.from(EventlResult.results) }
                .map { status ->
                    Eventol(status.status)
                }
    }
}
