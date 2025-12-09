package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

public class DepositViewBuilder extends ViewBuilder {

    private final Runnable cancelHandler;
    private final Runnable depositHandler;

    public DepositViewBuilder(Model model, Runnable cancelHandler, Runnable depositHandler) {
        super(model);
        this.cancelHandler = cancelHandler;
        this.depositHandler = depositHandler;
    }

    public Region build() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("account-root");

        Label header = new Label("Deposit");
        header.getStyleClass().add("account-header");

        root.setTop(header);
        BorderPane.setAlignment(header, Pos.CENTER);
        BorderPane.setMargin(header, new Insets(30, 0, 20, 0));

        Region card = createCenter();
        root.setCenter(card);
        BorderPane.setMargin(card, new Insets(0, 40, 0, 40));

        Node depositErrorMessage = boundLabel(model.depositErrorMessageProperty());
        depositErrorMessage.getStyleClass().add("account-error");

        root.setBottom(depositErrorMessage);
        BorderPane.setAlignment(depositErrorMessage, Pos.CENTER);
        BorderPane.setMargin(depositErrorMessage, new Insets(20, 0, 30, 0));

        return root;
    }

    private Region createCenter() {
        VBox card = new VBox();
        card.setAlignment(Pos.TOP_CENTER);
        card.getStyleClass().add("account-card");

        Label instructions = new Label(
                "Please enter the amount of cash and coins you would like to deposit."
        );
        instructions.getStyleClass().add("deposit-instructions");
        instructions.setWrapText(true);
        instructions.setTextAlignment(TextAlignment.CENTER);
        instructions.setAlignment(Pos.CENTER);
        instructions.setMaxWidth(Double.MAX_VALUE);

        HBox cashInputs = new HBox(10);
        cashInputs.setAlignment(Pos.CENTER);
        cashInputs.getStyleClass().add("deposit-row");

        Region ones = labeledCurrencyInputField("Ones:", model.onesProperty());
        Region fives = labeledCurrencyInputField("Fives:", model.fivesProperty());
        Region tens = labeledCurrencyInputField("Tens:", model.tensProperty());
        Region twenties = labeledCurrencyInputField("Twenties:", model.twentiesProperty());
        Region fifties = labeledCurrencyInputField("Fifties:", model.fiftiesProperty());
        Region hundreds = labeledCurrencyInputField("Hundreds:", model.hundredsProperty());

        cashInputs.getChildren().addAll(ones, fives, tens, twenties, fifties, hundreds);

        HBox coinInputs = new HBox(10);
        coinInputs.setAlignment(Pos.CENTER);
        coinInputs.getStyleClass().add("deposit-row");

        Region pennies = labeledCurrencyInputField("Pennies:", model.penniesProperty());
        Region nickels = labeledCurrencyInputField("Nickels:", model.nickelsProperty());
        Region dimes = labeledCurrencyInputField("Dimes:", model.dimesProperty());
        Region quarters = labeledCurrencyInputField("Quarters:", model.quartersProperty());

        coinInputs.getChildren().addAll(pennies, nickels, dimes, quarters);

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(evt -> cancelHandler.run());
        cancelButton.getStyleClass().add("secondary-button");
        cancelButton.setMinWidth(Region.USE_PREF_SIZE);

        Button depositButton = new Button("Deposit");
        depositButton.setOnAction(evt -> depositHandler.run());
        depositButton.getStyleClass().add("primary-button");
        depositButton.setMinWidth(Region.USE_PREF_SIZE);

        buttons.getChildren().addAll(cancelButton, depositButton);

        card.getChildren().addAll(instructions, cashInputs, coinInputs, buttons);

        return card;
    }

}
