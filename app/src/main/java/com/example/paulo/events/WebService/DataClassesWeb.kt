package com.example.paulo.events.WebService

/**
 * Created by Paulo on 20/07/2017.
 */
import android.support.v7.widget.DialogTitle
import com.google.gson.annotations.SerializedName

data class EventResult(val results : List<Eventol>)


data class Eventol (@SerializedName("status") val status : String)


/*data class FilmResult(val results : List<Film>)

data class Film (val title : String,
                 @SerializedName("episode_id")
                 val episodeId : Int,
                 @SerializedName("characters")
                 val personUrls : List<String>)

data class Person(val name : String,
                  val gender : String)*/

