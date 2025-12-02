package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class WithdrawViewBuilder extends ViewBuilder {

    private final Runnable cancelHandler;
    private final Runnable withdrawHandler;

    public WithdrawViewBuilder(Model model, Runnable cancelHandler, Runnable withdrawHandler) {
        super(model);
        this.cancelHandler = cancelHandler;
        this.withdrawHandler = withdrawHandler;
    }

    public Region build() {
        BorderPane result = new BorderPane();

        Label heading = new Label("Please enter the amount of cash and coins you would like to withdraw.");
        result.setTop(heading);

        Region center = createCenter();
        result.setCenter(center);

        Node withdrawalErrorMessage = boundLabel(model.withdrawalErrorMessageProperty());
        result.setBottom(withdrawalErrorMessage);

        BorderPane.setAlignment(heading, Pos.CENTER);
        BorderPane.setAlignment(center, Pos.CENTER);
        BorderPane.setAlignment(withdrawalErrorMessage, Pos.CENTER);

        BorderPane.setMargin(result.getTop(), new Insets(20, 0, 0, 0));
        BorderPane.setMargin(result.getTop(), new Insets(0, 0, 20, 0));

        return result;
    }

    public Region createCenter() {
        VBox result = new VBox(20);

        HBox cashInputs = new HBox(10);
        cashInputs.setAlignment(Pos.CENTER);

        Region ones = labeledCurrencyInputField("Ones:", model.onesProperty());
        Region fives = labeledCurrencyInputField("Fives:", model.fivesProperty());
        Region tens = labeledCurrencyInputField("Tens:", model.tensProperty());
        Region twenties = labeledCurrencyInputField("Twenties:", model.twentiesProperty());
        Region fifties = labeledCurrencyInputField("Fifties:", model.fiftiesProperty());
        Region hundreds = labeledCurrencyInputField("Hundreds:", model.hundredsProperty());
        cashInputs.getChildren().addAll(ones, fives, tens, twenties, fifties, hundreds);

        HBox coinInputs = new HBox(10);
        coinInputs.setAlignment(Pos.CENTER);

        Region pennies = labeledCurrencyInputField("Pennies:", model.penniesProperty());
        Region nickels = labeledCurrencyInputField("Nickels:", model.nickelsProperty());
        Region dimes = labeledCurrencyInputField("Dimes:", model.dimesProperty());
        Region quarters = labeledCurrencyInputField("Quarters:", model.quartersProperty());
        coinInputs.getChildren().addAll(pennies, nickels, dimes, quarters);

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(evt -> cancelHandler.run());
        Button withdrawButton = new Button("Withdraw");
        withdrawButton.setOnAction(evt -> withdrawHandler.run());

        buttons.getChildren().addAll(cancelButton, withdrawButton);

        result.getChildren().addAll(cashInputs, coinInputs, buttons);

        result.setAlignment(Pos.CENTER);

        return result;
    }

}
