package com.ironijunior.diffbase64.transport.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * This class publishes the {@link DiffEvent} to the Spring Application Context.
 *
 */
@Component
public class DiffEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publish(DiffEvent event) {
        this.publisher.publishEvent(event);
    }
}
