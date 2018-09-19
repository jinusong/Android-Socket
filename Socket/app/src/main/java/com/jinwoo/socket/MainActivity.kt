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
    lateinit var receive_data : String
    lateinit var receive_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connect_btn = findViewById(R.id.connect_button)
        disconnect_btn = findViewById(R.id.disconnect_button)
        Text = findViewById(R.id.message)
        Receive_Text = findViewById(R.id.receive_Text)
        receive_btn = findViewById(R.id.receive_btn)

        socket = SocketApplication.get()
        if(socket != null){
            Text.setText("Socket 생성")
        }

        connect_btn.setOnClickListener { v ->
            socket.connect()
            socket.on(Socket.EVENT_CONNECT, onConnect)
        }

        receive_btn.setOnClickListener({ v ->
            socket.connect()
            socket.on(Socket.EVENT_CONNECT, Receiver)
        })

        disconnect_btn.setOnClickListener { v ->
            onDestroy()
            Text.setText("Disconnect 성공")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
        socket.off(Socket.EVENT_DISCONNECT, onConnect)
    }

    private val onConnect = Emitter.Listener { args ->

        Thread(Runnable {
            var data = JSONObject()



            try {
                socket.emit("Connect", data)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Text.setText("onConnect 성공")

        }).start()

    }

    private val Receiver = Emitter.Listener { args ->
        Thread(Runnable {

            try {
                socket.emit("call_Receive")
                receive_data = args.toString()
            } catch (e: IOException){
                e.printStackTrace()
            }
            Receive_Text.setText(receive_data)

        }).start()
    }
}