package com.rrd.airmanager;

import com.rrd.airmanager.App.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.ResourceBundle;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    private static final Logger logger = LoggerFactory.getLogger(StageInitializer.class);
    @Value("classpath:/fxml/app_view.fxml")
    private Resource sceneResource;
    @Value("classpath:/icons/app_icon.png")
    private Resource appImage;
    private final String applicationTitle;
    private final AppFXMLLoader appFXMLLoader;

    public StageInitializer(@Value("${spring.application.ui.apptitle}") String applicationTitle, AppFXMLLoader fxmlLoader) {
        this.applicationTitle = applicationTitle;
        this.appFXMLLoader = fxmlLoader;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            Stage stage = event.getStage();
            Scene scene = appFXMLLoader.loadScene(sceneResource);
            stage.getIcons().add(new Image(appImage.getInputStream()));
            stage.setTitle(applicationTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error("Failed to initialize stage", e);
        }
    }
}
