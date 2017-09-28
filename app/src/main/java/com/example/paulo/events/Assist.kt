package com.example.paulo.events

import android.content.Context

/**
 * Created by Paulo on 04/08/2017.
 */
class Assist(applicationContext: Context) {

    companion object {
        private var instance: Assist? = null

        @Synchronized
        fun getInstance(ctx: Context): Assist {
            if (instance == null) {
                instance = Assist(ctx.applicationContext)
            }
            return instance!!
        }

        fun  limitTittle(title : String): ArrayList<String>{

            var result = ArrayList<String>()
            val size = title.length

            if (size < 25){
                result.add(title)
                result.add("22")
            }else if (size >= 25 && size < 70 ) {
                result.add(title)
                result.add("20")
            }else{
                result.add(title)
                result.add("18")
            }
            return result
        }
    }

    fun verificaCor(categoria: String): Int {
        if (categoria == "tecnologia" || categoria == "Tecnologia") {
            return R.color.tecnologia
        } else if (categoria == "gastronomia" || categoria == "Gastronomia") {
            return R.color.gastronomia
        } else if (categoria == "biologia" || categoria == "Biologia") {
            return R.color.gastronomia
        } else return R.color.tecnologia


    }

    fun verificaImg(categoria: String): Int {
        if (categoria == "tecnologia" || categoria == "Tecnologia") {
            return R.drawable.app
        } else if (categoria == "gastronomia" || categoria == "Gastronomia") {
            return R.drawable.app
        } else if (categoria == "biologia" || categoria == "Biologia") {
            return R.drawable.app
        } else return R.drawable.app


    }



}