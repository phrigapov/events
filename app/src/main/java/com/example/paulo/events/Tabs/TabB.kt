package com.example.paulo.events.Tabs

import android.app.Activity
import android.app.Dialog
import android.app.FragmentManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Debug
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.StrictMode
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DialogTitle
import android.util.Log
import android.widget.*
import com.example.paulo.events.*
import com.example.paulo.events.Web_Service.DownloadDados
import com.google.gson.Gson
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.widget.Toast
import android.view.MotionEvent
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener






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
        var oadapter: AdapterEventosB = AdapterEventosB(activity,false)

        listaDeEventos.adapter = oadapter

        listaDeEventos.setOnItemClickListener(
                OnItemClickListener { parent, view, position, id ->
                    println("clicado "+position)
                    val intent = Intent(activity, Evento::class.java)
                    startActivity(intent)
                })


        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        val showEvents: (List<Eventinhos>) -> Unit = fun(events : List<Eventinhos>){
            oadapter.content = events
            oadapter.notifyDataSetChanged()
        }
        var DownloadDados = DownloadDados(showEvents,activity).execute(null)



        return view
    }




    fun todosOsEventos(): ArrayList<Eventos> {
        return ArrayList(Arrays.asList(
                Eventos("Uso de comida", "Gatronomia", 10),
                Eventos("Aprendendo a usar API", "tecnologia", 15),
                Eventos("Curso de responsabilidade", "Administração", 12)))
    }

    data class AdapterEventosB(var act: Activity, var isEditing: Boolean, var content : List<Eventinhos> = ArrayList()) : BaseAdapter() {

        override fun getCount(): Int {
            return content.size
        }

        //Usando um opcional ? na frente do Any, dizemos que o retorno pode ser Any ou Null
        override fun getItem(position: Int): Any? {
            return content.get(position)
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            val view: View = act.layoutInflater.inflate(R.layout.b, parent, false)
            //var evento: Eventos = eventos.get(position)

            //Montando o layout da view
            var nome: TextView = view.findViewById<TextView>(R.id.nome)
            var descricao : TextView = view.findViewById(R.id.descricao)
            var data : TextView = view.findViewById(R.id.data)
            var barra : ImageView = view.findViewById(R.id.barra)
            var catImg : ImageView = view.findViewById(R.id.cat_img)
            var anoHora : TextView = view.findViewById(R.id.ano)
            var cat : TextView = view.findViewById(R.id.categoria)


            // populando as Views

            if (content is List<Eventinhos>){

                nome.setText(content[position].title)
                descricao.setText(content[position].description)

                println(content[position].datetime)

                //arrumando a data
                var ff = SimpleDateFormat("dd.MM")
                var dataff = ff.format(content[position].datetime)
                data.setText(dataff)

                var anof = SimpleDateFormat("yyyy \n'às' HH'h'mm")
                var dataffa = anof.format(content[position].datetime)
                anoHora.setText(dataffa)

                if((position%2) == 0) {
                    barra.setBackgroundColor(ContextCompat.getColor(act,R.color.tecnologia))
                    nome.setTextColor(ContextCompat.getColor(act,R.color.tecnologia))
                    catImg.setImageResource(R.drawable.tec)
                    cat.setText("Tecnologia")
                } else {
                    barra.setBackgroundColor(ContextCompat.getColor(act,R.color.gastronomia))
                    nome.setTextColor(ContextCompat.getColor(act,R.color.gastronomia))
                    catImg.setImageResource(R.drawable.gas)
                    cat.setText("Gastronomia")
                }



            }else {
                nome.setText("nada")
            }

            isEditing = false
            //descricao.setText(evento.descricao)
            //imagem.setImageResource(icone.imagemIcone)
            //imagem.setImageResource()



            return view


        }




    }



/*fun arrumaData(data : String) : Date {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    try {
        val date = format.parse(data)
        return date
    } catch (e: ParseException) {
        // TODO Auto-generated catch block
        e.printStackTrace()
    }

}*/

}



