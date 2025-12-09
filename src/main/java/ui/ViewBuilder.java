package ui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;
import javafx.util.Builder;
import javafx.util.converter.NumberStringConverter;

import java.util.function.UnaryOperator;

public abstract class ViewBuilder implements Builder<Region> {

    protected final Model model;

    public ViewBuilder(Model model) {
        this.model = model;
    }

    @Override
    public abstract Region build();

    public Node boundTextField(StringProperty boundField) {
        TextField result = new TextField();
        result.textProperty().bindBidirectional(boundField);
        return result;
    }

    public Node boundPasswordField(StringProperty boundField) {
        PasswordField result = new PasswordField();
        result.textProperty().bindBidirectional(boundField);
        return result;
    }

    public Node boundLabel(StringProperty boundField) {
        Label result = new Label();
        result.setWrapText(true);
        result.textProperty().bindBidirectional(boundField);
        result.setTextAlignment(TextAlignment.CENTER);
        return result;
    }

    public Region labeledCurrencyInputField(String name, IntegerProperty boundField) {
        HBox result = new HBox(5);

        Label label = new Label(name);

        TextField textField = new TextField();
        textField.setPrefColumnCount(2);
        textField.textProperty().bindBidirectional(boundField, new NumberStringConverter());
        textField.setTextFormatter(new TextFormatter<>(lengthLimiter(3)));
        result.getChildren().addAll(label, textField);
        result.setAlignment(Pos.CENTER);
        return result;
    }

    /**
     * A login text field has a binding to a StringProperty in Model,
     * and a max length: 16 for card number and 3 for PIN.
     */
    public Node loginTextField(StringProperty boundField, int maxLength, boolean isPassword) {
        Node result;

        if (isPassword) {
            result = boundPasswordField(boundField);
        } else {
            result = boundTextField(boundField);
        }

        UnaryOperator<TextFormatter.Change> inputFilter = lengthLimiter(maxLength);

        ((TextField) result).setTextFormatter(new TextFormatter<>(inputFilter));
        return result;
    }

    private UnaryOperator<TextFormatter.Change> lengthLimiter(int maxLength) {
        return change -> {
            String newText = change.getControlNewText();
            if (newText.length() > maxLength || !newText.matches("\\d*")) {
                return null;
            }
            return change;
        };
    }

}
