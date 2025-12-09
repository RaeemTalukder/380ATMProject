package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

public class WithdrawViewBuilder extends ViewBuilder {

    private final Runnable cancelHandler;
    private final Runnable withdrawHandler;

    public WithdrawViewBuilder(Model model, Runnable cancelHandler, Runnable withdrawHandler) {
        super(model);
        this.cancelHandler = cancelHandler;
        this.withdrawHandler = withdrawHandler;
    }

    public Region build() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("account-root");

        Label header = new Label("Withdraw");
        header.getStyleClass().add("account-header");
        root.setTop(header);
        BorderPane.setAlignment(header, Pos.CENTER);
        BorderPane.setMargin(header, new Insets(30, 0, 20, 0));

        Region card = createCenter();
        root.setCenter(card);
        BorderPane.setMargin(card, new Insets(0, 40, 0, 40));

        Node withdrawalErrorMessage = boundLabel(model.withdrawalErrorMessageProperty());
        withdrawalErrorMessage.getStyleClass().add("account-error");

        root.setBottom(withdrawalErrorMessage);
        BorderPane.setAlignment(withdrawalErrorMessage, Pos.CENTER);
        BorderPane.setMargin(withdrawalErrorMessage, new Insets(20, 0, 30, 0));

        return root;
    }

    private Region createCenter() {
        VBox card = new VBox();
        card.setAlignment(Pos.TOP_CENTER);
        card.getStyleClass().add("account-card");
        card.setFillWidth(true);

        Label instructions = new Label(
                "Please enter the amount of cash and coins you would like to withdraw."
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

        Button withdrawButton = new Button("Withdraw");
        withdrawButton.setOnAction(evt -> withdrawHandler.run());
        withdrawButton.getStyleClass().add("primary-button");
        withdrawButton.setMinWidth(Region.USE_PREF_SIZE);

        buttons.getChildren().addAll(cancelButton, withdrawButton);

        card.getChildren().addAll(instructions, cashInputs, coinInputs, buttons);

        return card;
    }

}
