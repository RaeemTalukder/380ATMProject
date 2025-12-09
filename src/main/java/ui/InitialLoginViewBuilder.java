package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class InitialLoginViewBuilder extends ViewBuilder {

    private final Runnable loginHandler;

    public InitialLoginViewBuilder(Model model, Runnable loginHandler) {
        super(model);
        this.loginHandler = loginHandler;
    }

    public Region build() {
        BorderPane result = new BorderPane();
        result.getStyleClass().add("login-root"); // overall background

        result.setTop(buildHeader());

        Node loginCard = loginPanel();
        BorderPane.setMargin(loginCard, new Insets(0, 40, 0, 40));
        result.setCenter(loginCard);

        Node errorLabel = boundLabel(model.loginErrorMessageProperty());
        errorLabel.getStyleClass().add("login-error");
        result.setBottom(errorLabel);

        BorderPane.setAlignment(result.getTop(), Pos.CENTER);
        BorderPane.setAlignment(result.getBottom(), Pos.CENTER);
        BorderPane.setMargin(result.getTop(), new Insets(30, 0, 0, 0));
        BorderPane.setMargin(result.getBottom(), new Insets(20, 0, 30, 0));

        return result;
    }

    private Node buildHeader() {
        VBox header = new VBox(6);
        header.setAlignment(Pos.CENTER);
        header.getStyleClass().add("login-header");

        // Adjust the resource path to where you place Dominion_Bank_logo.png
        ImageView logoView = new ImageView(
                new Image(getClass().getResource("/images/logo.png").toExternalForm())
        );
        logoView.setFitWidth(80);
        logoView.setPreserveRatio(true);
        logoView.getStyleClass().add("bank-logo");

        Label bankName = new Label("Dominion Bank");
        bankName.getStyleClass().add("login-title");

        Label subtitle = new Label("Admin login required to start system");
        subtitle.getStyleClass().add("login-subtitle");

        header.getChildren().addAll(logoView, bankName, subtitle);
        return header;
    }

    public Node loginPanel() {
        VBox result = new VBox(12);
        result.setAlignment(Pos.CENTER);
        result.setPadding(new Insets(20));
        result.getStyleClass().add("login-card"); // white card

        HBox cardNumberQuery = new HBox(10, new Label("Card number: "), loginTextField(model.cardNumberInputProperty(), 16, false));
        cardNumberQuery.setAlignment(Pos.CENTER);
        cardNumberQuery.getStyleClass().add("login-row");

        HBox pinQuery = new HBox(10, new Label("PIN: "), loginTextField(model.pinInputProperty(), 3, true));
        pinQuery.setAlignment(Pos.CENTER);
        pinQuery.getStyleClass().add("login-row");

        result.getChildren().addAll(cardNumberQuery, pinQuery, loginSubmitButton());

        return result;
    }

    public Node loginSubmitButton() {
        Button result = new Button("Login");
        result.setOnAction(evt -> loginHandler.run());
        result.getStyleClass().add("primary-button");
        return result;
    }

}
