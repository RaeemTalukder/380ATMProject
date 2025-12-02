package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class AdminScreenViewBuilder extends ViewBuilder {

    private final Runnable setCashHandler;
    private final Runnable openAtmHandler;
    private final Runnable closeAtmHandler;

    public AdminScreenViewBuilder(Model model, Runnable setCashHandler, Runnable openAtmHandler, Runnable closeAtmHandler) {
        super(model);
        this.setCashHandler = setCashHandler;
        this.openAtmHandler = openAtmHandler;
        this.closeAtmHandler = closeAtmHandler;
    }

    public Region build() {
        BorderPane result = new BorderPane();

        result.setTop(new Label("Dominion Bank Administrator"));
        result.setCenter(atmCashPane());
        result.setBottom(boundLabel(model.adminErrorMessageProperty()));

        BorderPane.setAlignment(result.getTop(), Pos.CENTER);
        BorderPane.setAlignment(result.getBottom(), Pos.CENTER);
        BorderPane.setMargin(result.getTop(), new Insets(30, 0, 0, 0));
        BorderPane.setMargin(result.getBottom(), new Insets(0, 0, 30, 0));

        return result;
    }

    private Region adminButtonsPanel() {
        HBox result = new HBox(10, atmCashSetButton(), openAtmButton(), closeAtmButton());
        result.setAlignment(Pos.CENTER);
        return result;
    }

    private Region atmCashPane() {
        VBox center = new VBox(20, new Label("ATM's Current Cash:\n\n"),
                boundLabel(model.atmCashProperty()), adminButtonsPanel());
        center.setAlignment(Pos.CENTER);
        return center;
    }

    private Node atmCashSetButton() {
        Button result = new Button("Reset ATM Cash");
        result.setOnAction(evt -> setCashHandler.run());
        return result;
    }

    private Node openAtmButton() {
        Button result = new Button("Repen ATM");
        result.setOnAction(evt -> openAtmHandler.run());
        return result;
    }

    private Node closeAtmButton() {
        Button result = new Button("Close ATM");
        result.setOnAction(evt -> closeAtmHandler.run());
        return result;
    }

}
