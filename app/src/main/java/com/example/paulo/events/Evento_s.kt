package com.example.paulo.events

import com.google.gson.annotations.SerializedName
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by Paulo on 23/07/2017.
 */
data class Evento_s (@SerializedName("code")var code : Int, @SerializedName("status")var status : String, @SerializedName("events")var events : List<Eventinhos>) {
}

data class Eventinhos (@SerializedName("id")var id : Int, @SerializedName("title")var title : String,@SerializedName("description")var description : String,
@SerializedName("isPublic")var isPublic : Boolean,@SerializedName("datetime")var datetime : Date,@SerializedName("created_at")var createdAt : Date,
                       @SerializedName("ownerId")var ownerdId : Int)

data class Token (@SerializedName("code")var id : Int,@SerializedName("status")var tkStatus : String,@SerializedName("token")var token : String)

// *****datetime timestamp utc
