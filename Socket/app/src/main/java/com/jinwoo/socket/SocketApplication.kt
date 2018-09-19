package com.jinwoo.socket

import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class SocketApplication() {

    companion object {
        lateinit var socket : Socket
        fun get(): Socket {
            try {
                socket = IO.socket("https://192.168.137.149:3880")
            } catch (e: URISyntaxException) {
                e.printStackTrace();
            }
            return socket
        }
    }
}