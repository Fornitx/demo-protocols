package com.demo.quic

import com.demo.constants.QUIC.PROTOCOL
import com.demo.constants.TLS.PASSWORD
import com.demo.constants.TLS2
import io.netty.handler.ssl.ClientAuth
import io.netty.incubator.codec.quic.QuicSslContextBuilder

object NettyUtils {
    val SERVER_SSL_CONTEXT = QuicSslContextBuilder.forServer(TLS2.SERVER_KEYMANAGERFACTORY, PASSWORD)
        .trustManager(TLS2.SERVER_TRUSTMANAGERFACTORY)
        .applicationProtocols(PROTOCOL)
        .clientAuth(ClientAuth.REQUIRE)
        .build()
    val CLIENT_SSL_CONTEXT = QuicSslContextBuilder.forClient()
        .keyManager(TLS2.CLIENT_KEYMANAGERFACTORY, PASSWORD)
        .trustManager(TLS2.CLIENT_TRUSTMANAGERFACTORY)
        .applicationProtocols(PROTOCOL)
        .build()
}
