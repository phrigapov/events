package com.example.paulo.events

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView

class Evento : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evento)
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)

        var evImg : ImageView = findViewById(R.id.evento_img)
        evImg.setImageResource(R.drawable.ex)

        var evNome : TextView = findViewById(R.id.evento_nome)
        evNome.setText("Arte e Cultura da Gastronomia")

    }
}
