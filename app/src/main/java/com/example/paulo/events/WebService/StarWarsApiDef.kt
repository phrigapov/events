package com.example.paulo.events.WebService

/**
 * Created by Paulo on 20/07/2017.
 */
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import rx.Observable

interface StarWarsApiDef {
    @Headers("Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MDE4MTAyNTksImlhdCI6MTUwMDYwMDY1OSwic3ViIjoxfQ.5WihjhyPcLeAf5q76WlSXBTDrqdoEp-Nw5Ov0aNTp_E")
    @GET("events/")
    fun listTitle() : Observable<EventResult>

    /*@GET("films")
    fun listMovies() : Observable<FilmResult>

    @GET("people/{personId}")
    fun loadPerson(@Path("personId") personId : String) : Observable<Person>*/
}
