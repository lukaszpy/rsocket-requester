package com.biteit.rsocket.requester.adapter.output.web;

import com.biteit.rsocket.requester.cdm.Reading;
import com.biteit.rsocket.requester.service.ReadingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("reading")
public class ReadingController {
    private final ReadingService readingService;

    public ReadingController(ReadingService readingService) {
        this.readingService = readingService;
    }

    @GetMapping("works/{worksId}")
    public Flux<Reading> getReadingsByWorks(@PathVariable String worksId){
        return readingService.getReadingByWorks(worksId);
    }

    @GetMapping("works/{worksId}/param")
    public Flux<Reading> getReadingsByWorksWithParam(@PathVariable String worksId){
        return readingService.getReadingByWorksWithParam(worksId);
    }

}
