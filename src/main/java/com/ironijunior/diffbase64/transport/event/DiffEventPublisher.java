package com.ironijunior.diffbase64.transport.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * This class publishes the {@link DiffEvent} to the Spring Application Context.
 *
 * @author Ironi Junior Medina
 */
@Component
public class DiffEventPublisher implements ApplicationEventPublisherAware {

    private static final Logger logger = LoggerFactory.getLogger(DiffEventPublisher.class);
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publish(DiffEvent event) {
        this.publisher.publishEvent(event);
        logger.info("Event published");
    }
}
