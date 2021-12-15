package view.contentpages;

import component.TitleLabel;
import controller.GameSystem;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import view.Theme;

public class SettingsPage {
    public final GridPane root;

    public final GameSystem gameSystem;
    public final Theme theme;

    public SettingsPage(GameSystem gameSystem, Theme theme) {
        this.gameSystem = gameSystem;
        this.theme = theme;
        root = new GridPane();
        root.add(new TitleLabel("Settings", theme), 0, 0);

        //Test ColorPicker
        {
            HBox container = new HBox();
            container.setPrefWidth(HBox.USE_COMPUTED_SIZE);
            container.setPrefHeight(100);
            ColorPicker cp = new ColorPicker(Theme.defaultThemeColor);
            theme.themeColorPR.bind(cp.valueProperty());
            Label label = new Label("ThemeColor");
            theme.bindTextFontFamily(label.fontProperty());
            theme.bindTextFontPaint(label.textFillProperty());
            container.getChildren().addAll(label, cp);
            root.add(container, 0, 1);
        }
//        {
//            HBox container = new HBox();
//            container.setPrefWidth(HBox.USE_COMPUTED_SIZE);
//            container.setPrefHeight(100);
//            ColorPicker cp = new ColorPicker(Theme.defaultThemeColor);
//            theme.backPaneBackgroundPR.bind(Bindings.createObjectBinding(()->{
//                Background background = new Background(new BackgroundFill(cp.getValue(), null, null));
//                return background;
//            },cp.valueProperty()));
//            Label label = new Label("BackgroundColor");
//            theme.bindTextFontFamily(label.fontProperty());
//            theme.bindTextFontPaint(label.textFillProperty());
//            container.getChildren().addAll(label, cp);
//            root.add(container, 0, 2);
//        }
    }
}
