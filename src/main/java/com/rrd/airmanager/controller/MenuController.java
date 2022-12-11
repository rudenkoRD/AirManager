package com.rrd.airmanager.controller;

import com.rrd.airmanager.App;
import com.rrd.airmanager.AppFXMLLoader;
import com.rrd.airmanager.events.ErrorEvent;
import com.rrd.airmanager.events.InfoEvent;
import com.rrd.airmanager.models.documentation.Documentation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MenuController implements Initializable {
    private static final Logger logger = LogManager.getLogger(MenuController.class);

    final ApplicationContext context;
    final AppFXMLLoader fxmlLoader;

    @FXML
    public AnchorPane bodyPane;

    @FXML
    public Label versionLabel;

    @FXML
    public Label statusLabel;

    @Value("classpath:/fxml/airports/airports.fxml")
    private Resource airportsMenu;
    @FXML
    public Button airportsButton;

    @Value("classpath:/fxml/schedule/schedule.fxml")
    private Resource scheduleMenu;
    @FXML
    public Button scheduleButton;

    @Value("classpath:/fxml/planes/planes.fxml")
    private Resource planesMenu;
    @FXML
    public Button planesButton;

    @Value("classpath:/fxml/pilots/pilots.fxml")
    private Resource pilotsMenu;
    @FXML
    public Button pilotsButton;

    @Value("${app.version}")
    private String versionText;

    public MenuController(ApplicationContext context, AppFXMLLoader fxmlLoader) {
        this.context = context;
        this.fxmlLoader = fxmlLoader;
    }

    public void showScheduleActions() {
        clearButtonStyles();
        scheduleButton.getStyleClass().add("selected");
        fxmlLoader.loadMenuSection(scheduleMenu, bodyPane);
    }

    public void showPlanesActions() {
        clearButtonStyles();
        planesButton.getStyleClass().add("selected");
        fxmlLoader.loadMenuSection(planesMenu, bodyPane);
    }

    public void showAirportActions() {
        clearButtonStyles();
        airportsButton.getStyleClass().add("selected");
        fxmlLoader.loadMenuSection(airportsMenu, bodyPane);
    }

    public void showPilotsActions() {
        clearButtonStyles();
        pilotsButton.getStyleClass().add("selected");
        fxmlLoader.loadMenuSection(pilotsMenu, bodyPane);
    }

    public void showDocumentation() {
        App.instance.showDocument(Documentation.documentationUrl);
    }

    private void clearButtonStyles() {
        airportsButton.getStyleClass().remove("selected");
        scheduleButton.getStyleClass().remove("selected");
        planesButton.getStyleClass().remove("selected");
        pilotsButton.getStyleClass().remove("selected");
    }

    @EventListener
    public void listenInfoEvents(InfoEvent event) {
        statusLabel.getStyleClass().remove("invalid");
        statusLabel.setText(event.getMessage());
        logger.info(event.getMessage());
    }

    @EventListener
    public void listenErrorEvents(ErrorEvent event) {
        statusLabel.getStyleClass().add("invalid");
        statusLabel.setText(event.getMessage());
        logger.error(event.getMessage(), event.getException());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        versionLabel.setText("Version: " + versionText);
    }
}
