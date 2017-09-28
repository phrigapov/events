package com.example.paulo.events.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 * Created by Paulo on 05/08/2017.
 */
class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyiDatabase", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.getApplicationContext())
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("events", true,
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

        db.createTable("events_fav", true,
                "id" to INTEGER + PRIMARY_KEY,
                "fav" to INTEGER,
                "id_ev" to INTEGER)

        print("passou-create")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable("events", true)
        onCreate(db)
        print("passou-upgrade")
    }
}


// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(getApplicationContext())


