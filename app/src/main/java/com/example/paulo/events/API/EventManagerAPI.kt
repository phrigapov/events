package com.example.paulo.events.API

import android.util.Log
import com.example.paulo.events.Entities.*
import com.example.paulo.events.Token
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by Paulo on 09/08/2017.
 */
class EventManagerAPI private constructor(){

    companion object {
        private var instance: EventManagerAPI? = null

        @Synchronized
        fun getSharedInstance(): EventManagerAPI {
            if (instance == null) {
                instance = EventManagerAPI()
            }
            return instance!!
        }
    }

    private var token : String = ""
    private val user = "admin@admin.com"
    private val pass = "admin"
    private val urlLogin = URL("https://ph-events.herokuapp.com/api/auth/login")
    private val eventURL = URL("https://ph-events.herokuapp.com/api/events/?upcoming=true")

    private fun login(): Boolean {
        try {
            val urlConLogin = urlLogin.openConnection() as HttpURLConnection
            urlConLogin.setRequestMethod("POST")
            urlConLogin.addRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            val urlParameters = "username=" + user + "&password=" + pass
            var outputInBytes = urlParameters.toByteArray()
            val os = urlConLogin.getOutputStream()
            os.write(outputInBytes)
            os.close()

            urlConLogin.connect()

            val resultIS = urlConLogin.getInputStream()
            val readerBR = BufferedReader(InputStreamReader(resultIS))
            urlConLogin.disconnect()
            var tkbuff: String = readerBR.readLine()

            val gsonT = Gson()
            val tokenResponse = gsonT.fromJson(tkbuff, TokenResponse::class.java)
            this.token = tokenResponse.token

        }catch (e: Exception){
            Log.e("Erro", e.message)
            return false
        }

        return true
    }

    fun getEvents(): List<EventAPI>?{

        if (this.token == ""){
            if (!this.login()){
                return null
            }
        }

        try {
            val urlConnection = this.eventURL.openConnection() as HttpURLConnection
            urlConnection.setRequestMethod("GET")
            urlConnection.setRequestProperty("Authorization", "Bearer " + this.token)

            urlConnection.connect()

            val inputStream = urlConnection.getInputStream()

            val reader = BufferedReader(InputStreamReader(inputStream))
            val buffer = StringBuffer()
            val eventBuff = reader.readLine()
            val gsonT = Gson()
            val eventsResponse = gsonT.fromJson(eventBuff, EventsServerResponse::class.java)

            urlConnection.disconnect()
            //println(eventsResponse.result.events[0].datetime)
            return eventsResponse.result.events

        }catch (e : Exception){
            Log.e("Error", " Get Event Failed", e)
            return null
        }
    }
}