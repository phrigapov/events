package com.example.paulo.events.WebService

/**
 * Created by Paulo on 20/07/2017.
 */
data class Movie (val title : String,
                  val episodeId : Int,
                  val characters : MutableList<Character>)

data class Character(val name : String,
                     val gender : String){

    override fun toString(): String {
        return "${name} / ${gender}"
    }
}

data class Evento (val status : String)