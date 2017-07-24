package com.example.paulo.events.Web_Service

import android.os.AsyncTask
import android.util.Log
import android.widget.ListView
import com.beust.klaxon.JsonArray
import com.beust.klaxon.Parser
import com.google.gson.JsonObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.transform.Result
import com.beust.klaxon.string
import com.example.paulo.events.R
import com.example.paulo.events.Tabs.AdapterEventos
import com.example.paulo.events.Tabs.TabB
import com.example.paulo.events.WebService.Evento_s
import com.google.gson.Gson





/**
 * Created by Paulo on 23/07/2017.
 */
class DownloadDados(var li : ListView,var adapter : TabB.AdapterEventos): AsyncTask<Void, Void, String>() {
    @Override
    override fun doInBackground(vararg params: Void?): String? {
        // Esta etapa é usada para executar a tarefa em background ou fazer a chamada para o webservice
        var result : String
        val url = URL("https://ph-events.herokuapp.com/api/events")
        val urlConnection = url.openConnection() as HttpURLConnection
        urlConnection.setRequestMethod("GET")
        urlConnection.setRequestProperty("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MDE4MTAyNTksImlhdCI6MTUwMDYwMDY1OSwic3ViIjoxfQ.5WihjhyPcLeAf5q76WlSXBTDrqdoEp-Nw5Ov0aNTp_E")
        urlConnection.connect()
        val inputStream = urlConnection.getInputStream()
        if (inputStream == null)
        {
            return null
        }
        val reader = BufferedReader(InputStreamReader(inputStream))
        var linha:String
        val buffer = StringBuffer()
        linha = reader.readLine()
        while ((reader.readLine()) != null) {

            buffer.append(linha + "\n");

        }
        var JsonResponse: String? = null
        JsonResponse = linha.toString();
//response data
        Log.i("o/p:", JsonResponse);
        //return linha
        result = linha
        if (buffer.length == 0)
        {
            return result
        }

        if (urlConnection != null)
        {
            urlConnection.disconnect()
        }
        if (reader != null)
        {
            try
            {
                reader.close()
            }
            catch (e: IOException) {
                Log.e("Erro", "Erro fechando o stream", e)
            }
        }
        return null
    }
    @Override
    protected override fun onPreExecute() {
        // Este passo é usado para configurar a tarefa, por exemplo, mostrando uma barra de progresso na interface do usuário.
    }


    override protected fun onPostExecute(result: String?) {
        // O resultado da execução em background é passado para este passo como um parâmetro.
        println(result)



        val parser: Parser = Parser()
        val stringBuilder: StringBuilder = StringBuilder(result)
        val json: com.beust.klaxon.JsonObject = parser.parse(stringBuilder) as com.beust.klaxon.JsonObject
         //var ba = 'pokemon'

        //println("var : ${json.string("events")}")

        //val array = parser.parse(stringBuilder) as JsonArray<com.beust.klaxon.JsonObject>
        //println(array["a"])

        val gson = Gson()
        //val jsonInString = "{'name' : 'mkyong'}"
        val ev = gson.fromJson(result, Evento_s::class.java)


        println(ev.events[1])

        var adp : TabB.AdapterEventos = adapter

        adp.atualizaEventos(result as String)



    }
}

