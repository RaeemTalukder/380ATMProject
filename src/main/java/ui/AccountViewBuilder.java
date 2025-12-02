package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class AccountViewBuilder extends ViewBuilder {

    private final Runnable withdrawHandler;
    private final Runnable depositHandler;
    private final Runnable logoutHandler;

    public AccountViewBuilder(Model model, Runnable withdrawHandler, Runnable depositHandler, Runnable logoutHandler) {
        super(model);
        this.withdrawHandler = withdrawHandler;
        this.depositHandler = depositHandler;
        this.logoutHandler = logoutHandler;
    }

    public Region build() {
        BorderPane result = new BorderPane();

        Node headingLabel = boundLabel(model.greetingProperty());
        result.setTop(headingLabel);

        result.setCenter(new VBox());

        Region accountInfoPanel = accountInfoPanel();
        result.setLeft(accountInfoPanel);

        Region accountOptionsPanel = accountOptionsPanel();
        result.setRight(accountOptionsPanel);

        Label txnSuccessMessage = new Label();
        txnSuccessMessage.textProperty().bindBidirectional(model.txnSuccessMessageProperty());
        result.setBottom(txnSuccessMessage);

        result.getCenter().maxWidth(0);
        result.getCenter().maxHeight(0);

        BorderPane.setAlignment(headingLabel, Pos.CENTER);
        BorderPane.setAlignment(accountInfoPanel, Pos.TOP_CENTER);
        BorderPane.setAlignment(accountOptionsPanel, Pos.TOP_CENTER);
        BorderPane.setAlignment(txnSuccessMessage, Pos.CENTER);

        BorderPane.setMargin(result.getTop(), new Insets(20, 0, 0, 0));
        BorderPane.setMargin(result.getLeft(), new Insets(50, 0, 0, 20));
        BorderPane.setMargin(result.getRight(), new Insets(50, 100, 0, 0));
        BorderPane.setMargin(result.getBottom(), new Insets(0, 0, 50, 0));

        return result;
    }

    public Region accountInfoPanel() {
        VBox result = new VBox(10,
                new Label("Card Number:"),
                boundLabel(model.cardNumberProperty()),
                new Label("Balance:"),
                boundLabel(model.balanceProperty()),
                new Label("Transactions:"),
                boundLabel(model.transactionsProperty())
                );
        result.setAlignment(Pos.TOP_CENTER);
        return result;
    }

    public Region accountOptionsPanel() {
        VBox result = new VBox(10, new Label("How may we help you?"), accountOptionsButtons());
        result.setAlignment(Pos.TOP_CENTER);
        return result;
    }

    public Region accountOptionsButtons() {
        VBox result = new VBox(20);

        Button withdrawButton = new Button("Withdraw");
        withdrawButton.setOnAction(evt -> withdrawHandler.run());

        Button depositButton = new Button("Deposit");
        depositButton.setOnAction(evt -> depositHandler.run());

        Button logoutButton = new Button("Log Out");
        logoutButton.setOnAction(evt -> logoutHandler.run());

        result.getChildren().addAll(withdrawButton, depositButton, logoutButton);

        result.setAlignment(Pos.CENTER);
        return result;
    }

}
