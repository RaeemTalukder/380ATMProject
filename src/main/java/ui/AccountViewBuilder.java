package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

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
        BorderPane root = new BorderPane();
        root.getStyleClass().add("account-root");

        Node headingLabel = boundLabel(model.greetingProperty());
        headingLabel.getStyleClass().add("account-header");
        root.setTop(headingLabel);
        BorderPane.setAlignment(headingLabel, Pos.CENTER);
        BorderPane.setMargin(headingLabel, new Insets(30, 0, 0, 0));

        Region card = accountContentCard();
        root.setCenter(card);
        BorderPane.setMargin(card, new Insets(0, 40, 0, 40)); // consistent side margin

        Label txnSuccessMessage = new Label();
        txnSuccessMessage.textProperty().bindBidirectional(model.txnSuccessMessageProperty());
        txnSuccessMessage.getStyleClass().add("account-success");
        root.setBottom(txnSuccessMessage);
        BorderPane.setAlignment(txnSuccessMessage, Pos.CENTER);
        BorderPane.setMargin(txnSuccessMessage, new Insets(20, 0, 30, 0));

        return root;
    }

    private Region accountContentCard() {
        VBox card = new VBox(18);
        card.setPadding(new Insets(8, 24,  24, 24));
        card.setAlignment(Pos.TOP_CENTER);
        card.getStyleClass().add("account-card");


        HBox mainRow = new HBox(90, accountInfoPanel(), accountOptionsPanel());
        mainRow.setAlignment(Pos.TOP_CENTER);
        mainRow.getStyleClass().add("account-main-row");

        Region bottomSpacer = new Region();
        bottomSpacer.setPrefHeight(200);

        card.getChildren().addAll(mainRow, bottomSpacer);
        return card;
    }

    public Region accountInfoPanel() {
        VBox box = new VBox(10);
        box.setAlignment(Pos.TOP_LEFT);
        box.getStyleClass().add("account-info-panel");

        Label cardLabel = new Label("Card Number:");
        cardLabel.getStyleClass().add("account-label");

        Node cardValue = boundLabel(model.cardNumberProperty());
        cardValue.getStyleClass().add("account-value");

        Label balanceLabel = new Label("Balance:");
        balanceLabel.getStyleClass().add("account-label");

        Node balanceValue = boundLabel(model.balanceProperty());
        balanceValue.getStyleClass().add("account-value");

        Label txnLabel = new Label("Transactions:");
        txnLabel.getStyleClass().add("account-label");

        Label txnValue = (Label) boundLabel(model.transactionsProperty());
        txnValue.getStyleClass().add("account-transactions");
        txnValue.setMaxWidth(260);
        txnValue.setMinHeight(Region.USE_PREF_SIZE);

        ScrollPane txnScroll = new ScrollPane(txnValue);
        txnScroll.setFitToWidth(true);
        txnScroll.setPrefViewportHeight(300);
        txnScroll.setMaxHeight(300);
        txnScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        txnScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        txnScroll.getStyleClass().add("account-transactions-scroll");

        box.getChildren().addAll(cardLabel, cardValue, balanceLabel, balanceValue, txnLabel, txnValue);

        return box;
    }

    public Region accountOptionsPanel() {
        VBox box = new VBox(12);
        box.setAlignment(Pos.TOP_LEFT);
        box.getStyleClass().add("account-options-panel");

        Label title = new Label("How may we help you?");
        title.getStyleClass().add("account-options-title");

        Region buttons = accountOptionsButtons();

        box.getChildren().addAll(title, buttons);
        return box;
    }

    public Region accountOptionsButtons() {
        HBox topRow = new HBox(20);
        topRow.setAlignment(Pos.CENTER_LEFT);

        Button withdrawButton = new Button("Withdraw");
        withdrawButton.setOnAction(evt -> withdrawHandler.run());
        withdrawButton.getStyleClass().add("primary-button");

        Button depositButton = new Button("Deposit");
        depositButton.setOnAction(evt -> depositHandler.run());
        depositButton.getStyleClass().add("primary-button");

        topRow.getChildren().addAll(withdrawButton, depositButton);

        Button logoutButton = new Button("Log Out");
        logoutButton.setOnAction(evt -> logoutHandler.run());
        logoutButton.getStyleClass().add("danger-button");

        VBox box = new VBox(16);
        box.setAlignment(Pos.CENTER_LEFT);
        box.getStyleClass().add("account-button-column");

        box.getChildren().addAll(topRow, logoutButton);

        return box;
    }

}
