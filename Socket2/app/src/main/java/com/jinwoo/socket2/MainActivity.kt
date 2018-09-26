package com.jinwoo.socket2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import io.socket.client.Socket
import io.socket.emitter.Emitter

class MainActivity : AppCompatActivity() {

    lateinit var Text : TextView
    lateinit var Receive_Text: TextView
    lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Text = findViewById(R.id.message)
        Receive_Text = findViewById(R.id.receive_Text)

        socket = SocketApplication.get()

        socket.on("lightOn",light_on)
        socket.on("lightOff", light_off)
        socket.connect()
    }

    var light_on = Emitter.Listener { args ->
        Text.setText("소켓 on 성공")
        Receive_Text.setText(args.toString())
    }

    var light_off = Emitter.Listener { args ->
        Text.setText("소켓 on 성공")
        Receive_Text.setText(args.toString())
    }

}
