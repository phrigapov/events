package com.example.paulo.events.WebService

import com.google.gson.annotations.SerializedName

/**
 * Created by Paulo on 23/07/2017.
 */
data class Evento_s (@SerializedName("code")var code : Int, @SerializedName("status")var status : String, @SerializedName("events")var events : List<Eventinhos>) {
}

data class Eventinhos (@SerializedName("id")var id : Int, @SerializedName("title")var title : String,@SerializedName("description")var description : String)

/*data class FilmResult(val results : List<Film>)

data class Film (val title : String,
                 @SerializedName("episode_id")
                 val episodeId : Int,
                 @SerializedName("characters")
                 val personUrls : List<String>)*/