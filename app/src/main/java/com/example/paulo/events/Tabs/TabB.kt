package com.example.paulo.events.Tabs

import android.Manifest
import android.app.Activity
import android.app.FragmentManager
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Color
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.widget.*
import com.example.paulo.events.*
import kotlin.collections.ArrayList
import com.example.paulo.events.Entities.Event
import com.example.paulo.events.Entities.EventAPI
import com.example.paulo.events.Web_Service.DownloadEvents
import com.example.paulo.events.db.database
import com.squareup.picasso.Picasso
import org.jetbrains.anko.db.*
import java.text.SimpleDateFormat
import java.util.*
import android.provider.CalendarContract.Events
import android.provider.CalendarContract
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.view.*
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class TabB : android.support.v4.app.Fragment(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var tk : SwipeRefreshLayout



    override fun onRefresh() {
        takeEventsWeb()

    }



    private var myContext: MainActivity? = null
    private var adapter : AdapterEventosB? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        myContext = activity as MainActivity?
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = activity.layoutInflater.inflate(R.layout.tab_a, container, false)
        val listaDeEventos = view.findViewById<ListView>(android.R.id.list) as ListView

        //var ref : SharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)
        //var edt = ref.edit()
        //edt.putString("tabB",getSup)

        println("ID: "+this.id)
        activity.supportFragmentManager.beginTransaction().addToBackStack(this.tag).commit()



        var mSwipeToRefresh : SwipeRefreshLayout = view.findViewById(R.id.swipe)
        mSwipeToRefresh.setOnRefreshListener(this)
        tk = mSwipeToRefresh
        tk.offsetTopAndBottom(-200)


        this.adapter = AdapterEventosB(activity, false)

        listaDeEventos.adapter = this.adapter

        this.takeEventsDB()
        this.takeEventsWeb()

        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }


        listaDeEventos.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {

            }

            override fun onScroll(absListView:AbsListView, firstVisibleItem:Int, visibleItemCount:Int, totalItemCount:Int) {
                val c = absListView.getChildAt(0)
                if (c != null) {
                    var scrolly = -c.top + absListView.firstVisiblePosition * c.height

                    if (scrolly > 0) {
                        tk.isEnabled = false
                    } else {
                        tk.isEnabled = true
                    }
                }
            }
            })

        listaDeEventos.setOnItemClickListener(
                AdapterView.OnItemClickListener { parent, view, position, id ->
                    val evt = takeEvents()
                    println("clicado " + position)
                    val intent = Intent(activity, Evento::class.java)
                    intent.putExtra("nome", evt[position].title);
                    intent.putExtra("data", evt[position].datetime);
                    intent.putExtra("categoria", evt[position].category_name);
                    intent.putExtra("categoria_image_url", evt[position].category_image_url);
                    intent.putExtra("image_url", evt[position].image_url);
                    intent.putExtra("hex_color", evt[position].hex_color);
                    intent.putExtra("id",evt[position].id.toString())
                    intent.putExtra("descricao", evt[position].description);
                    startActivity(intent)
                })

        return view
    }


     fun checkPermission(permissionName: String): Boolean {
        return ActivityCompat.checkSelfPermission(
                activity, permissionName) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 42) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                //showContacts();
            } else {
                println("falhou")
            }
        }
    }



    fun takeEventsDB(){
        context.database.use {
            val parser = classParser<Event>()
            var result = select("events").orderBy("datetime",SqlOrderDirection.ASC).parseList(parser)
            adapter?.content = result
            adapter?.notifyDataSetChanged()
        }
    }

    fun takeEventsWeb() {
        val showEvents: (List<EventAPI>) -> Unit = fun(events: List<EventAPI>) {
            this.takeEventsDB()
            tk.isRefreshing = false
        }
        DownloadEvents(showEvents, activity).execute(null)
    }

    fun takeEvents(): List<Event>{
        var result : List<Event> = emptyList()
        context.database.use {
            val parser = classParser<Event>()
            result = select("events").orderBy("datetime",SqlOrderDirection.ASC).parseList(parser)

        }
        return result
    }

    data class AdapterEventosB(var act: Activity, var isEditing: Boolean, var content: List<Event> = ArrayList()) : BaseAdapter() {

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

            //Montando o layout da view

            var nome: TextView = view.findViewById<TextView>(R.id.nome)
            var summary: TextView = view.findViewById(R.id.summary)
            var data: TextView = view.findViewById(R.id.data)
            var barra: ImageView = view.findViewById(R.id.barra)
            var catImg: ImageView = view.findViewById(R.id.cat_img)
            var anoHora: TextView = view.findViewById(R.id.ano)
            var cat: TextView = view.findViewById(R.id.categoria)

            // populando as Views
            if (content is List<Event>) {
                //println(content[0].title)
                var texto = Assist.limitTittle(content[position].title)
                nome.setText(texto[0])
                nome.textSize = texto[1].toFloat()

                summary.setText(content[position].summary)

                //arrumando a data
                //println(content[position].datetime)
                val FormatString = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                FormatString.timeZone = (TimeZone.getTimeZone(TimeZone.getDefault().id.toLowerCase()))
                val dataString = content[position].datetime
                val date = FormatString.parse(dataString)
                val nova = FormatString.format(Date())


                var ff = SimpleDateFormat("dd.MM")
                var dataff = ff.format(date)
                data.setText(dataff)


                var anof = SimpleDateFormat("yyyy \n'às' HH'h'mm")
                var dataffa = anof.format(date)
                anoHora.setText(dataffa)

                barra.setBackgroundColor(Color.parseColor(content[position].hex_color))
                nome.setTextColor(Color.parseColor(content[position].hex_color))
                Picasso.with(act).load(content[position].category_image_url).into(catImg)
                catImg.setColorFilter(Color.parseColor(content[position].hex_color),android.graphics.PorterDuff.Mode.SRC_ATOP)
                cat.setText(content[position].category_name.toString())

            }

            isEditing = false

            return view
        }

    }

}
