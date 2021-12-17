package component.panes;

import controller.Player;
import javafx.geometry.HPos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import view.Theme;

public class InfoPane extends GridPane {
    public static final int MIN_WIDTH = 200;

    public Theme theme;

    public InfoPane(Player player, Theme theme) {
        super();
        this.theme = theme;
        this.setMinWidth(MIN_WIDTH);

        Label nameLabel = new Label();
        nameLabel.textProperty().bind(player.nameProperty());
        nameLabel.setWrapText(true);
        theme.bindToTextFontFamily(nameLabel.fontProperty());
        theme.bindToTextFontPaint(nameLabel.textFillProperty());
        this.add(nameLabel, 0, 0);
        this.getColumnConstraints().add(new ColumnConstraints(0, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.SOMETIMES, HPos.CENTER, true));


        theme.bindToFrontPane(backgroundProperty());
    }
}
