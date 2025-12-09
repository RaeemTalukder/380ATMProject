package ui;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Controller {

    private final Model model;
    private final Interactor interactor;
    private ViewBuilder viewBuilder;
    private final Stage stage;
    private static final int dimensions = 800;
    private final String stylesheet = getClass().getResource("/css/styles.css").toExternalForm();

    public Controller(Stage stage) {
        this.model = new Model();
        this.interactor = new Interactor(model, this);
        this.viewBuilder = new InitialLoginViewBuilder(model, interactor::processInitialLoginRequest);
        this.stage = stage;
        stageSetup();
    }

    private void stageSetup() {
        Image icon = new Image(getClass().getResourceAsStream("/images/logo.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Dominion Bank ATM");
    }

    public void setInitialLoginScreen() {
        stage.setScene(buildScene());
    }

    public void setInitialAdminScreen() {
        viewBuilder = new InitialAdminScreenViewBuilder(model, interactor::setAtmCash, interactor::processAdminOpen);
        stage.setScene(buildScene());
    }

    public void setMainLoginScreen() {
        viewBuilder = new MainLoginScreenViewBuilder(model, interactor::processLoginRequest);
        stage.setScene(buildScene());
    }

    public void setAccountScreen() {
        viewBuilder = new AccountViewBuilder(model, interactor::processWithdrawButton,
                interactor::processDepositButton, interactor:: processLogoutButton);
        stage.setScene(buildScene());
    }

    public void setAdminAccountScreen() {
        viewBuilder = new AdminAccountViewBuilder(model, interactor::processWithdrawButton,
                interactor::processDepositButton, interactor::processAdminOptionsButton, interactor:: processLogoutButton);
        stage.setScene(buildScene());
    }

    public void setAdminScreen() {
        viewBuilder = new AdminScreenViewBuilder(model, interactor::setAtmCash, interactor::processAdminOpen, interactor::closeAtm);
        stage.setScene(buildScene());
    }

    public void setWithdrawScreen() {
        viewBuilder = new WithdrawViewBuilder(model, interactor::processCancelButton, interactor::processWithdrawal);
        stage.setScene(buildScene());
    }

    public void setDepositScreen() {
        viewBuilder = new DepositViewBuilder(model, interactor::processCancelButton, interactor::processDeposit);
        stage.setScene(buildScene());
    }

    private void setStylesheet(Scene scene) {
        scene.getStylesheets().add(stylesheet);
    }

    private Scene buildScene() {
        Scene scene = new Scene(viewBuilder.build(), dimensions, dimensions);
        setStylesheet(scene);
        return scene;
    }
}
