package com.example.paulo.events.Tabs

import android.app.Activity
import android.app.FragmentManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.paulo.events.MainActivity
import com.example.paulo.events.R
import com.example.paulo.events.WebService.StarWarsApi
import org.junit.experimental.results.ResultMatchers.isSuccessful
import retrofit2.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.os.StrictMode
import android.widget.*
import com.example.paulo.events.Eventos
import com.example.paulo.events.WebService.Evento_s
import com.example.paulo.events.Web_Service.DownloadDados
import com.google.gson.Gson
import java.util.*


class TabB : android.support.v4.app.Fragment() {

    internal var fragmentManager: FragmentManager? = null
    private var myContext: MainActivity? = null

    lateinit var listView: ListView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        myContext = activity as MainActivity?
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        val view: View = activity.layoutInflater.inflate(R.layout.tab_a, container, false)
        val listaDeEventos = view.findViewById<ListView>(android.R.id.list) as ListView

        //val listaDeIcones = findViewById<ListView>(R.id.lista) as ListView
        var sList = todosOsEventos()

        //var oadapter : ArrayAdapter<String> = ArrayAdapter(this, simple_list_item_1,outraLista)
        var oadapter: com.example.paulo.events.Tabs.TabB.AdapterEventos = com.example.paulo.events.Tabs.TabB.AdapterEventos(sList, activity,false,"vazio")

        listaDeEventos.adapter = oadapter

        /* listView = ListView(myContext)

        movieAdapter = ArrayAdapter(myContext,
                android.R.layout.simple_list_item_1, movies)
        listView.adapter = movieAdapter

        movies.add("teste")
        movies.add("teste")
        movies.add("teste")
        movies.add("teste")

        val api = StarWarsApi()
        api.loadEventos()?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe ({ event ->
            movies.add("${event.status}")
        }, { e ->
            e.printStackTrace()
        },{
            movieAdapter.notifyDataSetChanged()
        })*/


        /*api.loadEventos()?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())?.subscribe ({ movie ->
            movies.add("${movie.title} -- ${movie.episodeId}")
        }, { e ->
            e.printStackTrace()
        },{
            movieAdapter.notifyDataSetChanged()
        })*/

        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        var url = null
        var DownloadDados = DownloadDados(listaDeEventos,oadapter).execute(url)
        println(DownloadDados)


        return view
    }

    fun todosOsEventos(): ArrayList<Eventos> {
        return ArrayList(Arrays.asList(
                Eventos("Uso de comida", "Gatronomia", 10),
                Eventos("Aprendendo a usar API", "tecnologia", 15),
                Eventos("Curso de responsabilidade", "Administração", 12)))
    }

    data class AdapterEventos(var eventos: MutableList<Eventos>, var act: Activity, var isEditing: Boolean, var content : String) : BaseAdapter() {

        override fun getCount(): Int {
            return eventos.size
        }

        //Usando um opcional ? na frente do Any, dizemos que o retorno pode ser Any ou Null
        override fun getItem(position: Int): Any? {
            return eventos.get(position)
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view: View = act.layoutInflater.inflate(R.layout.b, parent, false)
            var evento: Eventos = eventos.get(position)

            //Montando o layout da view
            var nome: TextView = view.findViewById<TextView>(R.id.nome)
            var descricao : TextView = view.findViewById(R.id.descricao)


            // populando as Views

            if (content != "vazio"){
                val gson = Gson()
                //val jsonInString = "{'name' : 'mkyong'}"
                val ev = gson.fromJson(content, Evento_s::class.java)

                nome.setText(ev.events[position].title)
                descricao.setText(ev.events[position].description)

            }else {
                nome.setText(evento.nome)
            }

            isEditing = false
            //descricao.setText(evento.descricao)
            //imagem.setImageResource(icone.imagemIcone)
            //imagem.setImageResource()


            println("foi")
            return view

        }

        fun atualizaEventos(ev : String){
            println("passou")
            content = ev
            this.notifyDataSetChanged()


        }

    }


}

