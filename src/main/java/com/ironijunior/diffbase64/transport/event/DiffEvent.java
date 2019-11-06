package com.ironijunior.diffbase64.transport.event;

import com.ironijunior.diffbase64.domain.DifferedData;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Class that represents an event of completed Diff.
 *
 * When this event is published the listener will
 * process the diff between left and right side
 * of {@code diffData} object.
 */
@Getter
public class DiffEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private DifferedData diffData;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public DiffEvent(Object source, DifferedData diffData) {
        super(source);
        this.diffData = diffData;
    }
}
