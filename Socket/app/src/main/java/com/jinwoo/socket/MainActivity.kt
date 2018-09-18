package com.jinwoo.socket

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.io.ObjectInputStream
import java.io.OutputStream
import java.net.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var Text : TextView
    lateinit var connect_btn: Button
    lateinit var disconnect_btn: Button
    lateinit var send: Button
    lateinit var socket: Socket
    lateinit var Message: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connect_btn = findViewById(R.id.connect_button)
        disconnect_btn = findViewById(R.id.disconnect_button)
        send = findViewById(R.id.send_button)
        Text = findViewById(R.id.resultText)

        socket = SocketApplication.get()

        connect_btn.setOnClickListener({socket.on(Socket.EVENT_CONNECT, onConnect)})
        disconnect_btn.setOnClickListener({socket.off("Off", onConnect)})
        send.setOnClickListener({socket.on("message", Receiver)})

    }
    private val onConnect = Emitter.Listener {
        override fun call(vararg args: Object) {
            val data = JSONObject()
            try {
                data.put("key1", "value1")
                data.put("key2", "value2")
                socket.emit("Connect", data)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }
    private val Receiver = Emitter.Listener {
        override fun call(vararg args: JSONObject) {
            val eceivedData: JSONObject = args[0];
            Message = eceivedData.toString()
        }
    }
}

