package com.example.paulo.events.Tabs

import android.graphics.Color.parseColor
import android.R.id.tabs
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.paulo.events.MainActivity
import android.app.Activity
import android.app.FragmentManager
import android.graphics.Color
import android.view.View
import android.widget.*
import com.example.paulo.events.Eventos
import com.example.paulo.events.R
import java.util.*


class TabA : android.support.v4.app.Fragment() {

    internal var fragmentManager: FragmentManager? = null
    private var myContext: MainActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        myContext = activity as MainActivity?
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view : View = activity.layoutInflater.inflate(R.layout.tab_a,container,false)
        val listaDeEventos = view.findViewById<ListView>(android.R.id.list) as ListView

        //val listaDeIcones = findViewById<ListView>(R.id.lista) as ListView
        var sList = todosOsEventos()
        //var oadapter : ArrayAdapter<String> = ArrayAdapter(this, simple_list_item_1,outraLista)
        var oadapter: AdapterEventos = AdapterEventos(sList, activity)

        listaDeEventos.adapter = oadapter

       // (listaDeEventos.adapter as BaseAdapter).notifyDataSetChanged()


        return view
    }



}

fun todosOsEventos(): ArrayList<Eventos> {
    return ArrayList(Arrays.asList(
            Eventos("Uso de comida", "Gatronomia", 10),
            Eventos("Aprendendo a usar API", "tecnologia", 15),
            Eventos("Curso de responsabilidade", "Administração",12)))
}

data class AdapterEventos(var eventos: MutableList<Eventos>, var act: Activity): BaseAdapter(){

    override fun getCount():Int {
        return eventos.size
    }
    //Usando um opcional ? na frente do Any, dizemos que o retorno pode ser Any ou Null
    override fun getItem(position:Int):Any? {
        return eventos.get(position)
    }
    override fun getItemId(position:Int):Long {
        return 0
    }
    override fun getView(position:Int, convertView: View?, parent: ViewGroup):View? {
        val view : View = act.layoutInflater.inflate(R.layout.a,parent,false)
        var evento : Eventos = eventos.get(position)

        //Montando o layout da view
        var nome : TextView = view.findViewById<TextView>(R.id.nome)
       // var descricao : TextView = view.findViewById(R.id.descricao)


        // populando as Views
        nome.setText(evento.nome)
        //descricao.setText(evento.descricao)
        //imagem.setImageResource(icone.imagemIcone)
        //imagem.setImageResource()


        return view

    }

    //var icones : List<Icone> = ArrayList<Icone>()
// variavel = nome: tipo
//    fun AdapterIconesPersonalizado(icones:List<Icone>, act: Activity) {
//        this.icones = icones
//    }

}