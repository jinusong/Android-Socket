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

        connect_btn.setOnClickListener { v ->
            Thread(Runnable {
                socket = SocketApplication.get()
                if(socket != null){
                    Text.setText("Socket 생성")
                }
                socket.on("Connected", onConnect)
                socket.connect()
            }).start()
        }

        receive_btn.setOnClickListener { v ->
            Thread(Runnable {
                socket.emit("call", socket.on("data_call", Receiver))
                socket.connect()
                Text.setText("Receive 성공")
            }).start()
        }

        disconnect_btn.setOnClickListener { v ->
            Thread(Runnable {
                onDestroy()
                Text.setText("Disconnect 성공")
            }).start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.off(Socket.EVENT_DISCONNECT)
        socket.disconnect()
    }

    private val onConnect = Emitter.Listener { args ->

            var data = "Hello Server"

            try {
                socket.emit("Connect", data)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
    }

    private val Receiver = Emitter.Listener { args ->

        try{
            receive_data = args.toString()
        } catch( e : JSONException){
            e.printStackTrace()
        }
        Receive_Text.setText(receive_data)
    }
}