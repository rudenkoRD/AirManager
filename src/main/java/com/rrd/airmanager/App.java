package com.rrd.airmanager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class App extends Application {
    public static App instance;
    protected ConfigurableApplicationContext context;

    public App() {
        instance = this;
    }

    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
        context = new SpringApplicationBuilder(AirManagerApplication.class).run();
    }

    @Override
    public void start(Stage stage) {
        context.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }

    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Object source) {
            super(source);
        }

        public Stage getStage() {
            return ((Stage) getSource());
        }
    }

    public void showDocument(String url) {
        getHostServices().showDocument(url);
    }
}
