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

public class MainLoginScreenViewBuilder extends ViewBuilder {

    private final Runnable loginHandler;

    public MainLoginScreenViewBuilder(Model model, Runnable loginHandler) {
        super(model);
        this.loginHandler = loginHandler;
    }

    public Region build() {
        BorderPane result = new BorderPane();

        result.setTop(new Label("Welcome to Dominion Bank"));
        result.setCenter(loginPanel());
        result.setBottom(boundLabel(model.loginErrorMessageProperty()));

        BorderPane.setAlignment(result.getTop(), Pos.CENTER);
        BorderPane.setAlignment(result.getBottom(), Pos.CENTER);
        BorderPane.setMargin(result.getTop(), new Insets(30, 0, 0, 0));
        BorderPane.setMargin(result.getBottom(), new Insets(0, 0, 30, 0));

        return result;
    }

    public Node loginPanel() {
        VBox result = new VBox(10);

        HBox cardNumberQuery = new HBox(10, new Label("Card number: "), loginTextField(model.cardNumberInputProperty(), 16, false));
        cardNumberQuery.setAlignment(Pos.CENTER);

        HBox pinQuery = new HBox(10, new Label("PIN: "), loginTextField(model.pinInputProperty(), 3, true));
        pinQuery.setAlignment(Pos.CENTER);

        result.getChildren().add(cardNumberQuery);
        result.getChildren().add(pinQuery);
        result.getChildren().add(loginSubmitButton());
        result.setAlignment(Pos.CENTER);

        return result;
    }

    public Node loginSubmitButton() {
        Button result = new Button("Login");
        result.setOnAction(evt -> loginHandler.run());
        return result;
    }

}
