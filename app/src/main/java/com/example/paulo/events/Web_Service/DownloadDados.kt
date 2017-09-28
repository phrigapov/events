package com.example.paulo.events.Web_Service

import android.app.Activity
import android.os.AsyncTask
import android.app.ProgressDialog
import com.example.paulo.events.API.EventManagerAPI
import com.example.paulo.events.Entities.Event
import com.example.paulo.events.Entities.EventAPI
import com.example.paulo.events.db.database
import org.jetbrains.anko.db.*
import android.support.v4.widget.SwipeRefreshLayout
import com.example.paulo.events.R


/**
 * Created by Paulo on 23/07/2017.
 */
class DownloadEvents(var callback :  (List<EventAPI>) -> Unit, var activity: Activity): AsyncTask<Void, Void, List<EventAPI>>() {

    val dialog= ProgressDialog(activity)
    var mTabLayout = activity.findViewById<com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView>(R.id.tab_layout2) as com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView

    override fun doInBackground(vararg params: Void?): List<EventAPI>? {
        val events = EventManagerAPI.getSharedInstance().getEvents()
        return events
    }

    override fun onPreExecute() {
       //TODO: Substituir esse cara depois, ANKO
        dialog.setMessage("Carregando Eventos")
        dialog.setTitle("Aguarde")
        dialog.setCancelable(false)
        dialog.isIndeterminate=true
        dialog.show()
        mTabLayout.selectTab(1)
    }

    override fun onPostExecute(result: List<EventAPI>?) {

       activity.database.use{
            if (result != null) {
                dropTable("events",true)
                createTable("events", true,
                        "id" to INTEGER + PRIMARY_KEY,
                        "title" to TEXT,
                        "description" to TEXT,
                        "summary" to TEXT,
                        "location" to TEXT,
                        "image_url" to TEXT,
                        "is_public" to TEXT,
                        "datetime" to TEXT,
                        "owner_id" to INTEGER,
                        "category_id" to INTEGER,
                        "category_name" to TEXT,
                        "category_image_url" to TEXT,
                        "hex_color" to TEXT)


                for (item in result) {
                    //println(item.datetime.toString())
                    insert("events",
                            "id" to item.id,
                            "title" to item.title,
                            "description" to item.description,
                            "summary" to item.summary,
                            "location" to item.location,
                            "image_url" to item.image_url,
                            "is_public" to item.isPublic,
                            "datetime" to item.datetime,
                            "owner_id" to item.ownerdId,
                            "category_id" to item.category?.id,
                            "category_name" to item.category?.name,
                            "category_image_url" to item.category?.imageurl,
                            "hex_color" to item.category?.hex_color)
                }
            }
        }

        if (result != null) {
            callback(result)
        } else {
            //TODO: exibir tela de erro
            callback(ArrayList<EventAPI>())
        }

        dialog.dismiss()

        mTabLayout.selectTab(0)

    }
}
