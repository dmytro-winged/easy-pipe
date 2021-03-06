package com.altumpoint.easypipe.core.pipes;

import java.util.function.Consumer;

/**
 * Interface for EasyPipe message consumers.
 *
 * @param <M> Type of messages.
 * @since 0.1.0
 */
public interface EasySource<M> extends StageComponent {

    void setMessageConsumer(Consumer<M> messageConsumer);

    /**
     * Starts consuming.
     */
    void start();

    /**
     * Stops consuming.
     */
    void stop();
}
