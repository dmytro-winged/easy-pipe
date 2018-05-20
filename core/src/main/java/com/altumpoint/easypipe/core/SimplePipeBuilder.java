package com.altumpoint.easypipe.core;

import com.altumpoint.easypipe.core.meters.DefaultMetersStrategy;
import com.altumpoint.easypipe.core.steps.ConsumerStep;
import com.altumpoint.easypipe.core.steps.EasyConsumer;
import com.altumpoint.easypipe.core.steps.EasyPipeStep;
import com.altumpoint.easypipe.core.steps.EasyPublisher;
import com.altumpoint.easypipe.core.steps.EasyTransformer;
import com.altumpoint.easypipe.core.steps.PublisherStep;
import com.altumpoint.easypipe.core.steps.TransformerStep;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Builder class for EasyPipes instances.
 *
 * @since 0.1.0
 */
@Component
@Scope("prototype")
public final class SimplePipeBuilder {

    private MeterRegistry meterRegistry;

    private String pipeName;

    private Deque<EasyPipeStep> steps;

    @Autowired
    private SimplePipeBuilder(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.steps = new ArrayDeque<>();
    }

    public <M> SimplePipeBuilder startPipe(String pipeName, EasyConsumer<M> consumer) {
        this.pipeName = pipeName;
        ConsumerStep<M> consumerStep = new ConsumerStep<>(
                consumer, new DefaultMetersStrategy(stepFullName("consumer"), meterRegistry));
        steps.add(consumerStep);
        return this;
    }

    public <M, R> SimplePipeBuilder addTransformer(String stepName, EasyTransformer<M, R> transformer) {
        steps.add(new TransformerStep<>(
                transformer, new DefaultMetersStrategy(stepFullName(stepName), meterRegistry)));
        return this;
    }

    public <M> SimplePipeBuilder addPublisher(String stepName, EasyPublisher<M> publisher) {
        PublisherStep<M> publisherStep = new PublisherStep<>(
                publisher, new DefaultMetersStrategy(stepFullName(stepName), meterRegistry));
        steps.add(publisherStep);
        return this;
    }


    public EasyPipe build() {
        if (this.steps.isEmpty()) {
            throw new IllegalStateException("Cannot build pipe with no steps.");
        }
        Iterator<EasyPipeStep> iterator = this.steps.descendingIterator();
        EasyPipeStep prevStep = iterator.next();
        while (iterator.hasNext()) {
            EasyPipeStep currentStep = iterator.next();
            currentStep.setNextStep(prevStep);
            prevStep = currentStep;
        }

        return new SimplePipe((ConsumerStep) prevStep);
    }

    private String stepFullName(String stepName) {
        return pipeName + "." + stepName;
    }
}
