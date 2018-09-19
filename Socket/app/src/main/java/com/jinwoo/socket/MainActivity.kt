package com.jinwoo.socket

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var Text : TextView
    lateinit var Receive_Text : TextView
    lateinit var connect_btn: Button
    lateinit var disconnect_btn: Button
    lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connect_btn = findViewById(R.id.connect_button)
        disconnect_btn = findViewById(R.id.disconnect_button)
        Text = findViewById(R.id.message)
        Receive_Text = findViewById(R.id.receive_Text)

        socket = SocketApplication.get()
        if(socket != null){
            Text.setText("Socket 생성")
        }

        connect_btn.setOnClickListener { v ->
            socket.connect()
            socket.on(Socket.EVENT_CONNECT, onConnect)
        }
        disconnect_btn.setOnClickListener { v ->
            onDestroy()
            Text.setText("Disconnect 성공")
        }
    }

    override fun onDestroy() {
        socket.disconnect()
        super.onDestroy()
        socket.off(Socket.EVENT_DISCONNECT, onConnect)
    }

    private val onConnect = Emitter.Listener { args ->

        Thread(Runnable {
            var data = JSONObject()
            data.put("Hello", "Server")
            try {
                socket.emit("Connect", data)
                Receive_Text.setText(args.toString())
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Text.setText("onConnect 성공")
        }).start()
    }
}