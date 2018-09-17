package com.jinwoo.socket

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress
import java.net.UnknownHostException

class MainActivity : AppCompatActivity() {

    val socket = Socket()
    lateinit var Text : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connect_btn: Button = findViewById(R.id.connect_button)
        val message : EditText = findViewById(R.id.message)
        Text = findViewById(R.id.resultText)

        connect_btn.setOnClickListener({ v -> connecting(message.toString())})

    }

    fun connecting(Message: String){
        try {
            // socket = new Socket("localhost", 5001);
            Text.setText("Server Connecting..")
            socket.connect(InetSocketAddress("192.168.43.122", 2800))
            Text.setText("Server Connection OK!")

            var Is: InputStream = socket.getInputStream();
            var Os: OutputStream = socket.getOutputStream();

            var byteArr: ByteArray? = null
            var msg = Message;

            byteArr = msg.toByteArray(Charsets.UTF_8);
            Os.write(byteArr);
            Os.flush();

            Text.setText("Data Transmitted OK!");

            byteArr = ByteArray(512);
            var readByteCount = Is.read();

            if(readByteCount == -1)
                throw IOException();

            msg = String(byteArr, 0, readByteCount, Charsets.UTF_8);

            Text.setText("Data Received OK!");
            Text.setText("Message : " + msg);

            Is.close();
            Os.close();

        } catch (e: UnknownHostException) {
            // TODO Auto-generated catch block
            // IP Address가 잘못되었을 경우
            e.printStackTrace();
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            // 해당 Port의 Server로 연결할 수 없는 경우
            e.printStackTrace();
            try { socket.close(); } catch (e1: IOException) { e1.printStackTrace(); }
        }

        if(!socket.isClosed()) {
            try {
                socket.close();
            } catch (e :IOException) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

