package com.rrd.airmanager;

import com.rrd.airmanager.controller.EditDialogController;
import com.rrd.airmanager.events.ErrorEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ResourceBundle;

@Component
public class AppFXMLLoader {
    private static final Logger logger = LoggerFactory.getLogger(StageInitializer.class);
    private final ApplicationContext context;

    @Autowired
    public AppFXMLLoader(ApplicationContext context) {
        this.context = context;
    }

    public void loadEditDialog(Resource resource, Object item) {
        try {
            ResourceBundle messagesResource = ResourceBundle.getBundle("messages");
            FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL(), messagesResource);
            fxmlLoader.setControllerFactory(context::getBean);
            DialogPane dialogPane = fxmlLoader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            EditDialogController<Object> controller = fxmlLoader.getController();
            if (item != null) {
                controller.setItemToEdit(item);
            }
            dialog.show();
        } catch (IOException e) {
            context.publishEvent(new ErrorEvent("Failed to open dialog", e));
        }
    }

    public void loadMenuSection(Resource resource, AnchorPane parent) {
        try {
            ResourceBundle messagesResource = ResourceBundle.getBundle("messages");
            FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL(), messagesResource);
            fxmlLoader.setControllerFactory(context::getBean);
            Pane pane = fxmlLoader.load();
            parent.getChildren().setAll(pane);
            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            AnchorPane.setTopAnchor(pane, 0.0);
        } catch (IOException e) {
            context.publishEvent(new ErrorEvent("Failed to open dialog", e));
        }
    }

    public Scene loadScene(Resource resource) {
        try {
            ResourceBundle messagesResource = ResourceBundle.getBundle("messages");
            FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL(), messagesResource);
            fxmlLoader.setControllerFactory(context::getBean);
            return new Scene(fxmlLoader.load(), 1280, 720);
        } catch (IOException e) {
            logger.error("Failed to load stage", e);
            return null;
        }
    }
}
