package com.biteit.rsocket.requester.configuration;

import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder;
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Mono;

@Configuration
public class RSocketConfiguration {
    private static final MimeType AUTH_MIME = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.toString());
    private static final UsernamePasswordMetadata METADATA = new UsernamePasswordMetadata("lpyrkosz", "pw");

    @Bean
    Mono<RSocketRequester> requesterMono(){
        return RSocketRequester.builder()
                .rsocketStrategies(strtegies -> strtegies
                    .decoder(new Jackson2JsonDecoder())
                    .encoder(new Jackson2JsonEncoder(), new SimpleAuthenticationEncoder()))
                .connect(TcpClientTransport.create("localhost", 7000));
    }

    @Bean
    Mono<RSocketRequester> securedRequesterMono(){
        return RSocketRequester.builder()
                .setupMetadata(METADATA, AUTH_MIME)
                .rsocketStrategies(strtegies -> strtegies
                        .decoder(new Jackson2JsonDecoder())
                        .encoder(new Jackson2JsonEncoder(), new SimpleAuthenticationEncoder()))
                .connect(TcpClientTransport.create("localhost", 7000));
    }

}
