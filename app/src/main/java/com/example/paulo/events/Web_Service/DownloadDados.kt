package com.example.paulo.events.Web_Service

import android.app.Activity
import android.app.Dialog
import android.os.AsyncTask
import android.util.Log
import android.widget.ListView
import com.example.paulo.events.Eventinhos
import java.net.HttpURLConnection
import java.net.URL
import com.example.paulo.events.Tabs.TabB
import com.example.paulo.events.Evento_s
import com.google.gson.Gson
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
//import android.support.test.espresso.core.deps.guava.io.Flushables.flush
import android.support.v4.widget.SearchViewCompat.getQuery
import com.example.paulo.events.Token
import okhttp3.internal.Util
import org.json.JSONObject
//import com.sun.xml.internal.ws.streaming.XMLStreamWriterUtil.getOutputStream
//import android.support.test.espresso.core.deps.guava.io.Flushables.flush
//import com.sun.xml.internal.ws.streaming.XMLStreamWriterUtil.getOutputStream
import java.io.*
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection


//import sun.net.www.http.HttpClient




/**
 * Created by Paulo on 23/07/2017.
 */
class DownloadDados(var callback :  (List<Eventinhos>) -> Unit,var activity: Activity): AsyncTask<Void, Void, String>() {

    val dialog= ProgressDialog(activity)


    override fun doInBackground(vararg params: Void?): String? {
        // Esta etapa é usada para executar a tarefa em background ou fazer a chamada para o webservice

        var user = "admin@admin.com"
        var pass = "admin"
        val urlLogin = URL("https://ph-events.herokuapp.com/api/auth/login")
        val urlConLogin = urlLogin.openConnection() as HttpURLConnection
        //urlConLogin.setDoOutput(true)
        urlConLogin.setRequestMethod("POST")
        urlConLogin.addRequestProperty("Content-Type","application/x-www-form-urlencoded")

        val urlParameters = "username="+user+"&password="+pass
        //val postData = urlParameters.getBytes(StandardCharsets.UTF_8)
        var outputInBytes = urlParameters.toByteArray()
        val os = urlConLogin.getOutputStream()

        os.write(outputInBytes)
        os.close()

        urlConLogin.connect()

        println(urlConLogin.responseCode)

        val resultLogin = urlConLogin.getInputStream()
        val readerLogin = BufferedReader(InputStreamReader(resultLogin))
        urlConLogin.disconnect()
        var tkbuff : String = readerLogin.readLine()

        val gsonT = Gson()
        val token = gsonT.fromJson(tkbuff, Token::class.java)


        var result : String
        val url = URL("https://ph-events.herokuapp.com/api/events")
        val urlConnection = url.openConnection() as HttpURLConnection
        urlConnection.setRequestMethod("GET")
        urlConnection.setRequestProperty("Authorization","Bearer "+token.token)
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

        dialog.setMessage("Carregando Eventos")
        dialog.setTitle("Aguarde")
        dialog.setCancelable(false)
        dialog.isIndeterminate=true
        dialog.show()

    }


    override protected fun onPostExecute(result: String?) {
        // O resultado da execução em background é passado para este passo como um parâmetro.
        println(result)



        val gson = Gson()
        //val jsonInString = "{'name' : 'mkyong'}"
        val ev = gson.fromJson(result, Evento_s::class.java)
        callback(ev.events)

        println(ev.events[1])


        dialog.dismiss()

    }
}

