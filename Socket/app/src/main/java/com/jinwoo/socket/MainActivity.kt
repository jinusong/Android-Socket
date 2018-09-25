package com.jinwoo.socket

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    lateinit var Text : TextView
    lateinit var Receive_Text : TextView
    lateinit var lighton_btn: Button
    lateinit var lightoff_btn: Button
    lateinit var socket: Socket
    lateinit var receive_data : String
    lateinit var receive_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lighton_btn = findViewById(R.id.lighton_button)
        lightoff_btn = findViewById(R.id.lightoff_button)
        Text = findViewById(R.id.message)
        Receive_Text = findViewById(R.id.receive_Text)
        receive_btn = findViewById(R.id.receive_btn)

        socket = SocketApplication.get()

        Text.setText("소켓 생성")

        socket.on("lightOn",light_on)
        socket.on("lightOff", light_off)
        socket.connect()

        lighton_btn.setOnClickListener({ v ->
            socket.emit("lightOn")
        })

        lightoff_btn.setOnClickListener({ v ->
            socket.emit("lightOff")
        })
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
