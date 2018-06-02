package com.altumpoint.easypipe.core.stages;

import com.altumpoint.easypipe.core.meters.MetersStrategy;

/**
 * Interface for EasyPipe stage instance.
 *
 * @param <M> Type of messages.
 * @since 0.1.0
 */
public abstract class EasyPipeStage<M> {

    protected final MetersStrategy metersStrategy;

    protected EasyPipeStage nextStage;


    public EasyPipeStage(MetersStrategy metersStrategy) {
        this.metersStrategy = metersStrategy;
    }


    public abstract void handle(M message);

    public void setNextStage(EasyPipeStage pipeStage) {
        this.nextStage = pipeStage;
    }
}