EasyPipe
======================

[![Download](https://api.bintray.com/packages/dmytro-winged/maven/easy-pipe/images/download.svg) ](https://bintray.com/dmytro-winged/maven/easy-pipe/_latestVersion)
[![Build Status](https://travis-ci.org/dmytro-winged/easy-pipe.svg?branch=master)](https://travis-ci.org/dmytro-winged/easy-pipe)
[![Code Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.altumpoint.easypipe%3Aeasypipe-parent&metric=coverage)](https://sonarcloud.io/component_measures?id=com.altumpoint.easypipe%3Aeasypipe-parent&metric=Coverage)
[![Quality Gates](https://sonarcloud.io/api/project_badges/measure?project=com.altumpoint.easypipe%3Aeasypipe-parent&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.altumpoint.easypipe%3Aeasypipe-parent)

Yet another data streaming engine, built on top of **Spring Boot**.

Web site: https://dmytro-winged.github.io/easy-pipe/

## Features
- implement all steps of your pipe separately;
- combine steps into EasyPipe;
- get control on your pipe out of the box;
- get main metrics of your pipe out of the box;

## Installation
Add EasyPipe dependency to you Spring Boot application.
```xml
<dependency>
    <groupId>com.altumpoint.easypipe</groupId>
    <artifactId>easypipe-core</artifactId>
    <version>0.2.1</version>
</dependency>
```

## Usage Instructions
Build your EasyPipe with builder and add to application context with `EasyPipeComponent` annotation.
```java
    @Autowired
    @EasyPipeline(name = "doubles-stream", autostart = false)
    public PipelineContext doublesStream(
            SimplePipeBuilder pipeBuilder,
            DoublesConsumer doublesConsumer,
            PercentsTransformer percentsTransformer,
            LogsPublisher logsPublisher
    ) {
        return pipeBuilder
                .startPipe("doubles-stream")
                .withSource("doubles-consumer", doublesConsumer)
                .transform("d-transformer", percentsTransformer)
                .publish("d-publisher", logsPublisher)
                .build();
    }
```
