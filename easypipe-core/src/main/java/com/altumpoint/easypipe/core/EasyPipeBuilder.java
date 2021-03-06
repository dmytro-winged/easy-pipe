package com.altumpoint.easypipe.core;

import com.altumpoint.easypipe.core.pipes.EasyFilter;
import com.altumpoint.easypipe.core.pipes.EasySource;
import com.altumpoint.easypipe.core.pipes.EasyPipe;
import com.altumpoint.easypipe.core.pipes.EasyDestination;
import com.altumpoint.easypipe.core.pipes.EasyTransformer;
import com.altumpoint.easypipe.core.pipes.TypedProperties;

/**
 * Main interface for EasyPipe builders.
 *
 * @since 0.2.0
 */
public interface EasyPipeBuilder {

    /**
     * Initialize builder with pipe entry point: consumer.
     *
     * @param pipeName name of pipe.
     * @return builder instance.
     */
    EasyPipeBuilder startPipe(String pipeName);

    /**
     * Adds source stage into the pipe.
     *
     * @param stageName name of pipe.
     * @param source pipe entry point.
     * @param <M> type of message.
     * @return builder instance.
     */
    default  <M> EasyPipeBuilder withSource(String stageName, EasySource<M> source) {
        return withSource(stageName, source, null);
    }

    /**
     * Adds source stage into the pipe.
     * Loads source properties.
     *
     * @param stageName name of pipe.
     * @param source pipe entry point.
     * @param properties source properties.
     * @param <M> type of message.
     * @return builder instance.
     */
    <M> EasyPipeBuilder withSource(String stageName, EasySource<M> source, TypedProperties properties);

    /**
     * Adds transformer stage into the pipe.
     *
     * @param stageName name of stage.
     * @param transformer stage component.
     * @param <M> type of message.
     * @param <R> type of transformation result.
     * @return builder instance.
     */
    default <M, R> EasyPipeBuilder transform(String stageName, EasyTransformer<M, R> transformer) {
        return transform(stageName, transformer, null);
    }

    /**
     * Adds transformer stage into the pipe.
     * Loads transformer properties.
     *
     * @param stageName name of stage.
     * @param transformer stage component.
     * @param properties transformer properties.
     * @param <M> type of message.
     * @param <R> type of transformation result.
     * @return builder instance.
     */
    <M, R> EasyPipeBuilder transform(
            String stageName, EasyTransformer<M, R> transformer, TypedProperties properties);

    /**
     * Adds filter into the pipe.
     *
     * @param stageName name of stage.
     * @param filter stage component.
     * @param <M> type of message.
     * @return builder instance.
     */
    default <M> EasyPipeBuilder filter(String stageName, EasyFilter<M> filter) {
        return filter(stageName, filter, null);
    }

    /**
     * Adds filter into the pipe.
     * Loads transformer properties.
     *
     * @param stageName name of stage.
     * @param filter stage component.
     * @param properties transformer properties.
     * @param <M> type of message.
     * @return builder instance.
     */
    <M> EasyPipeBuilder filter(
            String stageName, EasyFilter<M> filter, TypedProperties properties);

    /**
     * Adds destination into the pipe.
     *
     * @param stageName name of stage.
     * @param destination stage component.
     * @param <M> type of message.
     * @return builder instance.
     */
    default  <M> EasyPipeBuilder publish(String stageName, EasyDestination<M> destination) {
        return publish(stageName, destination, null);
    }

    /**
     * Adds destination into the pipe.
     * Loads destination properties.
     *
     * @param stageName name of stage.
     * @param destination stage component.
     * @param properties destination properties.
     * @param <M> type of message.
     * @return builder instance.
     */
    <M> EasyPipeBuilder publish(String stageName, EasyDestination<M> destination, TypedProperties properties);


    /**
     * Builds and returns simple easy pipe.
     *
     * @return instance of EasyPipe context.
     */
    PipelineContext build();
}
