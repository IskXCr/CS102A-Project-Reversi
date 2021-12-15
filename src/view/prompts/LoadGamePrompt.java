package view.prompts;

import controller.GameSystem;
import javafx.beans.property.IntegerProperty;
import javafx.scene.layout.GridPane;

public class LoadGamePrompt {
    public GridPane root;

    public GameSystem gameSystem;

    public LoadGamePrompt(GameSystem gameSystem, IntegerProperty indexProperty){
        this.gameSystem = gameSystem;

        root = new GridPane();
    }
}
