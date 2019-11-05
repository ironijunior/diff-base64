package com.ironijunior.diffbase64.api.event;

import com.ironijunior.diffbase64.api.service.DiffProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * This class acts as a Listener for the {@link DiffEvent} object.
 *
 * When the event is received by this listener it will send to the
 * process responsible for the comparison between left and right values.
 */
@Component
public class DiffEventListener implements ApplicationListener<DiffEvent> {

    private final Executor threadPool = Executors.newCachedThreadPool();
    private DiffProcessorService diffProcessorService;

    @Autowired
    public DiffEventListener(DiffProcessorService service) {
        this.diffProcessorService = service;
    }

    @Override
    public void onApplicationEvent(DiffEvent event) {
        CompletableFuture.runAsync(
                () -> diffProcessorService.diffSides(event.getDiffData()),
                threadPool);

    }
}
