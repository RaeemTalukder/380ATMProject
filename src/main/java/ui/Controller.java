package ui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {

    private final Model model;
    private final Interactor interactor;
    private ViewBuilder viewBuilder;
    private final Stage stage;
    private static final int dimensions = 800;

    public Controller(Stage stage) {
        this.model = new Model();
        this.interactor = new Interactor(model, this);
        this.viewBuilder = new InitialLoginViewBuilder(model, interactor::processInitialLoginRequest);
        this.stage = stage;
    }

    public void setInitialLoginScreen() {
        stage.setScene(new Scene(viewBuilder.build(), dimensions, dimensions));
    }

    public void setInitialAdminScreen() {
        viewBuilder = new InitialAdminScreenViewBuilder(model, interactor::setAtmCash, interactor::processAdminOpen);
        stage.setScene(new Scene(viewBuilder.build(), dimensions, dimensions));
    }

    public void setMainLoginScreen() {
        viewBuilder = new MainLoginScreenViewBuilder(model, interactor::processLoginRequest);
        stage.setScene(new Scene(viewBuilder.build(), dimensions, dimensions));
    }

    public void setAccountScreen() {
        viewBuilder = new AccountViewBuilder(model, interactor::processWithdrawButton,
                interactor::processDepositButton, interactor:: processLogoutButton);
        stage.setScene(new Scene(viewBuilder.build(), dimensions, dimensions));
    }

    public void setAdminAccountScreen() {
        viewBuilder = new AdminAccountViewBuilder(model, interactor::processWithdrawButton,
                interactor::processDepositButton, interactor::processAdminOptionsButton, interactor:: processLogoutButton);
        stage.setScene(new Scene(viewBuilder.build(), dimensions, dimensions));
    }

    public void setAdminScreen() {
        viewBuilder = new AdminScreenViewBuilder(model, interactor::setAtmCash, interactor::processAdminOpen, interactor::closeAtm);
        stage.setScene(new Scene(viewBuilder.build(), dimensions, dimensions));
    }

    public void setWithdrawScreen() {
        viewBuilder = new WithdrawViewBuilder(model, interactor::processCancelButton, interactor::processWithdrawal);
        stage.setScene(new Scene(viewBuilder.build(), dimensions, dimensions));
    }

    public void setDepositScreen() {
        viewBuilder = new DepositViewBuilder(model, interactor::processCancelButton, interactor::processDeposit);
        stage.setScene(new Scene(viewBuilder.build(), dimensions, dimensions));
    }
}
