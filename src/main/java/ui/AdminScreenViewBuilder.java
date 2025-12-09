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
        BorderPane root = new BorderPane();
        root.getStyleClass().add("admin-root");

        Label header = new Label("Dominion Bank Administrator");
        header.getStyleClass().add("admin-header");
        root.setTop(header);
        BorderPane.setAlignment(header, Pos.CENTER);
        BorderPane.setMargin(header, new Insets(30, 0, 0, 0));

        Region card = atmCashPane();
        root.setCenter(card);
        BorderPane.setMargin(card, new Insets(0, 40, 0, 40));

        Node errorLabel = boundLabel(model.adminErrorMessageProperty());
        errorLabel.getStyleClass().add("admin-error");
        root.setBottom(errorLabel);
        BorderPane.setAlignment(errorLabel, Pos.CENTER);
        BorderPane.setMargin(errorLabel, new Insets(20, 0, 30, 0));

        return root;
    }

    private Region atmCashPane() {
        VBox card = new VBox(18);
        card.setPadding(new Insets(24));
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("admin-card");

        Label cashTitle = new Label("ATM's Current Cash:");
        cashTitle.getStyleClass().add("admin-title");

        Node cashValue = boundLabel(model.atmCashProperty());
        cashValue.getStyleClass().add("admin-cash-value");

        Region buttonsRow = adminButtonsPanel();

        card.getChildren().addAll(cashTitle, cashValue, buttonsRow);
        return card;
    }

    private Region adminButtonsPanel() {
        HBox row = new HBox(14);
        row.setAlignment(Pos.CENTER);
        row.getStyleClass().add("admin-button-row");

        Button resetButton = new Button("Reset ATM Cash");
        resetButton.setOnAction(evt -> setCashHandler.run());
        resetButton.getStyleClass().add("primary-button");
        resetButton.setMinWidth(Region.USE_PREF_SIZE);

        Button reopenButton = new Button("Reopen ATM");
        reopenButton.setOnAction(evt -> openAtmHandler.run());
        reopenButton.getStyleClass().add("secondary-button");
        reopenButton.setMinWidth(Region.USE_PREF_SIZE);

        Button closeButton = new Button("Close ATM");
        closeButton.setOnAction(evt -> closeAtmHandler.run());
        closeButton.getStyleClass().add("danger-button");
        closeButton.setMinWidth(Region.USE_PREF_SIZE);

        row.getChildren().addAll(resetButton, reopenButton, closeButton);
        return row;
    }

}
