package com.demo.rsocket

import com.demo.constants.NET
import com.demo.data.StringData.asResponse
import com.demo.logging.ServerLogger
import io.rsocket.SocketAcceptor
import io.rsocket.core.RSocketServer
import io.rsocket.transport.netty.server.TcpServerTransport
import io.rsocket.util.ByteBufPayload
import reactor.core.publisher.Flux
import reactor.netty.tcp.TcpServer

fun main() {
    val socketAcceptor = SocketAcceptor.forRequestChannel { requests ->
        Flux.from(requests).map { payload ->
            val dataUtf8 = payload.dataUtf8
            ServerLogger.log(dataUtf8)
            ByteBufPayload.create(dataUtf8.asResponse())
        }
    }

    val serverTransport = TcpServerTransport.create(
        TcpServer.create().port(NET.PORT).secure {
            it.sslContext(NettyUtils.SERVER_SSL_CONTEXT)
        }
    )

    RSocketServer.create(socketAcceptor).bind(serverTransport).block()!!.onClose().block()
}
