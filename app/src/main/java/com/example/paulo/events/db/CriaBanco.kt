/*
package com.example.paulo.events.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log



/**
 * Created by Paulo on 05/08/2017.
 */
class CriaBanco(context: Context) : SQLiteOpenHelper(context, "instancia.db", null, 4) {


    val DATABASE_CREATE =
            "CREATE TABLE if not exists " + "events" + " (" +
                    "_id integer PRIMARY KEY," +
                    "title text," +
                    "description text,"+
                    "isPublic text,"+
                    "datetime text,"+
                    "created_at text,"+
                    "ownerId integer,"+
                    "category text"+
                    ")"



    override fun onCreate(db: SQLiteDatabase) {
        println(DATABASE_CREATE)
        db.dropTable("events",true)
        db.execSQL("DROP TABLE IF EXISTS " + "events")
        db.execSQL(DATABASE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {

        onCreate(db);
    }
}
*/