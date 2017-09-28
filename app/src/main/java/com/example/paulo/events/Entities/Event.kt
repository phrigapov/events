package com.example.paulo.events.Entities

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Paulo on 09/08/2017.
 */
data class EventsServerResponse (@SerializedName("code")var code : Int, @SerializedName("status")var status : String, @SerializedName("result")var result : Result) {
    }

data class Result (@SerializedName("events")var events : List<EventAPI>) {
}

data class EventAPI (@SerializedName("id")var id : Int, @SerializedName("title")var title : String, @SerializedName("description")var description : String,@SerializedName("summary")var summary : String?,@SerializedName("image_url")var image_url : String?,
                           @SerializedName("is_public")var isPublic : String?, @SerializedName("datetime")var datetime : String?,
                           @SerializedName("owner_id")var ownerdId : Int?, @SerializedName("category")var category : Category?)

data class Category (@SerializedName("id")var id : Int, @SerializedName("name")var name : String, @SerializedName("image_url")var imageurl : String, @SerializedName("hex_color")var hex_color : String) {
}

data class Event (@SerializedName("id")var id : Int, @SerializedName("title")var title : String, @SerializedName("description")var description : String,@SerializedName("summary")var summary : String?,@SerializedName("image_url")var image_url : String?,
                     @SerializedName("is_public")var isPublic : String?, @SerializedName("datetime")var datetime : String?,
                     @SerializedName("owner_id")var ownerdId : Int?,
                     @SerializedName("category_id")var category_id : Int?, @SerializedName("category_name")var category_name : String?,
                     @SerializedName("category_image_url")var category_image_url : String?, @SerializedName("hex_color")var hex_color : String?)


//TODO: Iremos remover a autenticação
data class TokenResponse (@SerializedName("code")var id : Int, @SerializedName("status")var tkStatus : String, @SerializedName("token")var token : String)
