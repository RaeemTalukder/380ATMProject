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

public class InitialAdminScreenViewBuilder extends ViewBuilder {

    private final Runnable setCashHandler;
    private final Runnable openAtmHandler;

    public InitialAdminScreenViewBuilder(Model model, Runnable setCashHandler, Runnable openAtmHandler) {
        super(model);
        this.setCashHandler = setCashHandler;
        this.openAtmHandler = openAtmHandler;
    }

    public Region build() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("admin-root");

        Label header = new Label("Dominion Bank Administrator");
        header.getStyleClass().add("admin-header");

        root.setTop(header);
        BorderPane.setAlignment(root.getTop(), Pos.CENTER);
        BorderPane.setMargin(root.getTop(), new Insets(30, 0, 0, 0));

        Node card = atmCashPane();
        BorderPane.setMargin(card, new Insets(0, 40, 0, 40));
        root.setCenter(card);

        Node errorLabel = boundLabel(model.adminErrorMessageProperty());
        errorLabel.getStyleClass().add("admin-error");
        root.setBottom(errorLabel);

        BorderPane.setAlignment(root.getBottom(), Pos.CENTER);
        BorderPane.setMargin(root.getBottom(), new Insets(20, 0, 30, 0));

        return root;
    }

    private Region atmCashPane() {
        VBox card = new VBox(18);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(24));
        card.getStyleClass().add("admin-card");

        Label cashTitle = new Label("ATM's Current Cash:");
        cashTitle.getStyleClass().add("admin-title");

        Node cashValue = boundLabel(model.atmCashProperty());
        cashValue.getStyleClass().add("admin-cash-value");

        card.getChildren().addAll(cashTitle, cashValue, adminButtonsPanel());

        return card;
    }

    private Region adminButtonsPanel() {
        HBox box = new HBox(14);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("admin-button-row");

        box.getChildren().addAll(atmCashSetButton(), openAtmButton());

        return box;
    }

    private Node atmCashSetButton() {
        Button b = new Button("Fill ATM Cash");
        b.setOnAction(evt -> setCashHandler.run());
        b.getStyleClass().add("primary-button");
        return b;
    }

    private Node openAtmButton() {
        Button b = new Button("Open ATM");
        b.setOnAction(evt -> openAtmHandler.run());
        b.getStyleClass().add("secondary-button");
        return b;
    }

}
