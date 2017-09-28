package com.example.paulo.events

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.provider.CalendarContract
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import com.example.paulo.events.Entities.Event
import java.text.SimpleDateFormat
import com.example.paulo.events.R.id.imageView
import com.example.paulo.events.db.database
import com.squareup.picasso.Picasso
import org.jetbrains.anko.act
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update
import java.util.*
import android.provider.CalendarContract.Events
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.*
import org.jetbrains.anko.contentView


class Evento : AppCompatActivity() {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evento)
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)

        var rela: RelativeLayout = findViewById(R.id.tam)
        //rela.layoutParams.height = 2000

        var params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        rela.layoutParams= params


        var evClose: ImageView = findViewById(R.id.evento_close)
        evClose.setImageResource(R.drawable.close)

        var idFav = intent.getStringExtra("id")

        var evNome: TextView = findViewById(R.id.evento_nome)
        evNome.setText(intent.getStringExtra("nome"))
        evNome.setTextColor(Color.parseColor(intent.getStringExtra("hex_color")))

        var evDescricao: WebView = findViewById(R.id.evento_descricao)
        evDescricao.visibility= View.GONE
        evDescricao.loadData("<style>img{display: inline;height: auto;max-width: 100%;}</style>" + intent.getStringExtra("descricao"), "text/html", "UTF-8")


        /*evDescricao!!.setWebChromeClient(WebChromeClient())
        evDescricao.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                println(evDescricao.height.toString())
                val height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT

                    println(height)



                //rela.layoutParams.height = evDescricao.height

            }
        })*/


        evDescricao.visibility= View.VISIBLE




        val FormatString = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        FormatString.timeZone = (TimeZone.getTimeZone(TimeZone.getDefault().id.toLowerCase()))
        val dataString = intent.getStringExtra("data")
        val date = FormatString.parse(dataString)

        var evData: TextView = findViewById(R.id.evento_data)
        var ff = SimpleDateFormat("dd.MM")
        var dataff = ff.format(date)
        evData.setText(dataff)

        var evAno: TextView = findViewById(R.id.evento_ano)
        var anof = SimpleDateFormat("yyyy \n'às' HH'h'mm")
        var dataffa = anof.format(date)
        evAno.setText(dataffa)

        var evCategoria: TextView = findViewById(R.id.evento_categoria)
        evCategoria.setText(intent.getStringExtra("categoria"))

        var evCategoriaImgIc: ImageView = findViewById(R.id.evento_cat_img)
        Picasso.with(act).load(intent.getStringExtra("categoria_image_url")).into(evCategoriaImgIc)

        var evCategoriaImg: ImageView = findViewById(R.id.evento_img)
        Picasso.with(act).load(intent.getStringExtra("image_url")).into(evCategoriaImg)




        var evBtnFav: Button = findViewById(R.id.evento_btnFav)

        var viewObserver : ViewTreeObserver = evDescricao.viewTreeObserver

        viewObserver.isAlive

        var pg = findViewById<ProgressBar>(R.id.progressBar)
        pg.visibility = View.VISIBLE

        viewObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                var height = evDescricao.contentHeight

                if(height != 0){
                    pg.visibility = View.GONE
                    var sreenSize = evNome.measuredHeight+evData.measuredHeight+evCategoriaImg.measuredHeight+evCategoria.measuredHeight
                    Toast.makeText(act,"height:"+height,Toast.LENGTH_SHORT).show()
                    evDescricao.viewTreeObserver.removeOnPreDrawListener(this)
                    println("Conteudo: "+evDescricao.contentHeight+" Outro: "+evDescricao.measuredHeight)
                    println("Height: "+sreenSize)

                    println("Conteudo Total: "+(sreenSize+evDescricao.contentHeight))
                    //rela.layoutParams.height = sreenSize+ height

                    var params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)
                    rela.layoutParams= params

                    //var params2 : FrameLayout.LayoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            //FrameLayout.LayoutParams.WRAP_CONTENT)
                   // rela2.layoutParams = params2
                    println("todo: "+rela.height)

                    rela.layoutParams.height = rela.height+height*2+evBtnFav.measuredHeight
                    var view : View = findViewById(R.id.tela_evento)
                    view.invalidate()

                }
                return true

            }
        })

        var fav = "not"

        act.database.use {
            val parser = classParser<EventFav>()
            var result = select("events_fav")
                    .whereArgs("( id = {IdFav} )",
                            "IdFav" to idFav.toInt()).parseOpt(parser)

            if (result != null) {
                fav = result.fav.toString()
            }
        }
        println("idFav= " + idFav + " fav= " + fav)

        if (fav == "1") {
            evBtnFav.setBackgroundColor(ContextCompat.getColor(act, android.R.color.holo_blue_light))
            evBtnFav.setText("REMOVER DOS MEUS EVENTOS")
        } else {
            evBtnFav.setBackgroundColor(ContextCompat.getColor(act, android.R.color.holo_red_light))
            evBtnFav.setText("ADICIONAR AOS MEUS EVENTOS")
        }

        evBtnFav.setOnClickListener {
            if (fav == "1") {
                act.database.use {
                    update("events_fav", "fav" to 0)
                            .where("id = {idFav}", "idFav" to idFav.toInt())
                            .exec()
                }
                evBtnFav.setBackgroundColor(ContextCompat.getColor(act, android.R.color.holo_red_light))
                evBtnFav.setText("ADICIONAR AOS MEUS EVENTOS")
                fav = "0"
                deleteCalendar(intent)
            } else if (fav == "0") {
                act.database.use {
                    update("events_fav", "fav" to 1)
                            .where("id = {idFav}", "idFav" to idFav.toInt())
                            .exec()
                }
                evBtnFav.setBackgroundColor(ContextCompat.getColor(act, android.R.color.holo_blue_light))
                evBtnFav.setText("REMOVER DOS MEUS EVENTOS")
                fav = "1"
                insertCalendar(intent)
                println("embaixo" + evDescricao.height.toString())
            } else {
                act.database.use {
                    insert("events_fav",
                            "id" to idFav,
                            "fav" to 1)
                }
                evBtnFav.setBackgroundColor(ContextCompat.getColor(act, android.R.color.holo_blue_light))
                evBtnFav.setText("REMOVER DOS MEUS EVENTOS")
                fav = "1"

                insertCalendar(intent)
            }
        }
        evClose.setOnClickListener {
            finish()
        }

    }



    fun insertCalendar(intent : Intent){
        try {

            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(act,
                    Manifest.permission.READ_CALENDAR)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                        Manifest.permission.READ_CALENDAR)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(act,
                            arrayOf<String>(Manifest.permission.READ_CALENDAR),
                            1)

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
            var cr : ContentResolver = getContentResolver()
            var values : ContentValues = ContentValues()
            var now = System.currentTimeMillis()
            values.put(CalendarContract.Events.DTSTART, now);
            values.put(CalendarContract.Events.DTEND, now + 3600*1000 /* one hour later */)
            values.put(CalendarContract.Events.TITLE, intent.getStringExtra("nome"))
            values.put(CalendarContract.Events.DESCRIPTION, intent.getStringExtra("descricao"))
            values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID())
            // default calendar
            values.put(CalendarContract.Events.CALENDAR_ID, 1)
            val uri = cr.insert(Events.CONTENT_URI, values)
            //act.contentResolver.insert(CalendarContract.Events.CONTENT_URI, values);

            val eventID = java.lang.Long.parseLong(uri.lastPathSegment)

            act.database.use {
                update("events_fav", "id_ev" to eventID)
                        .where("id = {idFav}", "idFav" to intent.getStringExtra("id").toInt())
                        .exec()
            }

        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    fun deleteCalendar(intent : Intent){

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                    Manifest.permission.READ_CALENDAR)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(act,
                        arrayOf<String>(Manifest.permission.READ_CALENDAR),
                        1)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        val DEBUG_TAG = "MyActivity"
        var eventID : Int = 210

        act.database.use {
            val parser = classParser<EventFav>()
            var result = select("events_fav")
                    .whereArgs("( id = {IdFav} )",
                            "IdFav" to intent.getStringExtra("id").toInt()).parseOpt(parser)

            if(result != null){ eventID = result.id_ev as Int
            }
        }

        var cr : ContentResolver = getContentResolver()
        var values : ContentValues = ContentValues()
        var deleteUri : Uri = Events.CONTENT_URI
        deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, eventID.toLong());
        var rows = getContentResolver().delete(deleteUri, null, null);
        Log.i(DEBUG_TAG, "Rows deleted: " + rows);

    }
}




data class EventFav (var id : Int , var fav : Int, var id_ev : Int?){

}
