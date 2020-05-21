package com.biteit.rsocket.requester.service;

import com.biteit.rsocket.requester.cdm.Reading;
import com.biteit.rsocket.requester.cdm.ReadingRequest;
import io.rsocket.metadata.WellKnownMimeType;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Log4j2
@Service
public class ReadingService {
    private static final MimeType AUTH_MIME = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.toString());
    private static final UsernamePasswordMetadata METADATA = new UsernamePasswordMetadata("lpyrkosz", "pw");

    private final Mono<RSocketRequester> requesterMono;
    private final Mono<RSocketRequester> securedRequesterMono;

    public ReadingService(Mono<RSocketRequester> requesterMono, Mono<RSocketRequester> securedRequesterMono) {
        this.requesterMono = requesterMono;
        this.securedRequesterMono = securedRequesterMono;
    }

    public Flux<Reading> getReadingByWorks(String worksId) {
        return requesterMono.flatMapMany(requester -> requester
                    .route("com.biteit.rsocket.v0")
                    .metadata(METADATA, AUTH_MIME)
                    .data(Mono.just(new ReadingRequest(worksId)))
                    .retrieveFlux(Reading.class))
                .limitRate(2)
                .delayElements(Duration.ofSeconds(1));
    }

    public Flux<Reading> getReadingByWorksWithParam(String worksId) {
        return securedRequesterMono.flatMapMany(requester -> requester
                .route("com.biteit.rsocket.{worksId}.v0", worksId)
                .data(Mono.empty())
                .retrieveFlux(Reading.class)).limitRate(2).delayElements(Duration.ofSeconds(1));
    }
}
