package com.demo.reversi.view.contentpages;

import com.demo.reversi.component.MetroButton;
import com.demo.reversi.component.TextLabel;
import com.demo.reversi.component.TitleLabel;
import com.demo.reversi.component.panes.SmoothishScrollPane;
import com.demo.reversi.component.switches.IndicatedToggleSwitch;
import com.demo.reversi.controller.local.SimpleGameSystem;
import com.demo.reversi.logger.Log0j;
import com.demo.reversi.res.lang.LiteralConstants;
import com.demo.reversi.themes.Theme;
import com.demo.reversi.view.Updatable;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Page that contains overall settings
 * <p>
 * Layout::
 * root
 * TitleLabel
 * scrollPane
 * contentWrapper
 * containers
 * SettingItems
 * configWrapper
 * discardButton
 * loadButton
 * saveButton
 * resetButton
 */
public class SettingsPage implements Updatable {
    public static final double ITEM_GAP = 20;
    public static final double PREF_CONTAINER_HEIGHT = 40;
    public final GridPane root;
    public final SmoothishScrollPane scrollPane;
    public final GridPane contentWrapper;
    public final GridPane configWrapper;

    public final MetroButton discardButton;
    public final MetroButton loadButton;
    public final MetroButton saveButton;
    public final MetroButton resetButton;

    public final SimpleGameSystem gameSystem;
    public final Theme theme;


    public SettingsPage(SimpleGameSystem gameSystem, Theme theme) {
        this.gameSystem = gameSystem;
        this.theme = theme;
        root = new GridPane();
        contentWrapper = new GridPane();
        scrollPane = new SmoothishScrollPane(contentWrapper);
        configWrapper = new GridPane();

        {
            {
                RowConstraints[] constraints = new RowConstraints[3];
                for (int i = 0; i < 3; i++) {
                    constraints[i] = new RowConstraints();
                    root.getRowConstraints().add(constraints[i]);
                }
                constraints[1].setVgrow(Priority.ALWAYS);
            }
            {
                ColumnConstraints[] constraints = new ColumnConstraints[3];
                for (int i = 0; i < 3; i++) {
                    constraints[i] = new ColumnConstraints();
                    constraints[i].setHgrow(Priority.ALWAYS);
                    root.getColumnConstraints().add(constraints[i]);
                }
            }
        }


        //Finally
        root.add(new TitleLabel(LiteralConstants.SettingsPageTitle.toString(), theme), 0, 0);
        root.add(scrollPane, 0, 1);
        root.add(configWrapper, 0, 2);

        //Test ColorPicker
        discardButton = new MetroButton("Discard Changes", theme);
        loadButton = new MetroButton("Load Theme", theme);
        saveButton = new MetroButton("Save Theme", theme);
        resetButton = new MetroButton("Reset Theme", theme);

        initContent();
        initConfig();

    }


    private void initConfig() {
        configWrapper.add(discardButton, 0, 0);
        configWrapper.add(loadButton, 1, 0);
        configWrapper.add(saveButton, 2, 0);
        configWrapper.add(resetButton, 3, 0);
        discardButton.setOnAction(ActionEvent -> {
            theme.applyDefaultTheme();
            refreshContent();
        });
        loadButton.setOnAction(ActionEvent ->
        {   //todo: add file picker
            theme.loadThemeFromFileExplorer();
            refreshContent();
        });
        saveButton.setOnAction(ActionEvent -> {
            theme.saveTheme();
        });
        resetButton.setOnAction(ActionEvent -> {
            theme.applyDefaultTheme();
            refreshContent();
        });
    }

    public void refreshContent() {
        contentWrapper.getChildren().clear();
        initContent();
    }

    /**
     * Init all the settings
     */
    private void initContent() {

        /*
         * Following is the list of the included items
         *
         * Colors Settings::
         * Dark Mode
         * Theme Color Picker
         * Player 1 Chess Color
         * Player 2 Chess Color
         * ChessBoard Color 1
         * ChessBoard Color 2
         * ChessBoard Grid Color
         *
         *
         * Background Settings::
         * Use Pure Color Background?
         * Background Image Source
         * ChessBoard Background Image Source
         *
         *
         * In-Game Settings::
         * PlayerIcon Source
         *
         *
         * Font Settings::
         * Title Font
         * Info Title Font
         * Menu Font
         * Text Font
         *
         *
         * Volume Settings::
         * BGM Volume Picker
         * Effect Volume Picker
         *
         *
         * Audio Settings::
         * MainView BGM Source
         * PlayPage BGM Source
         * GamePage BGM Source
         * GameFinish BGM Source
         * Chess Down Sound Source
         * Chess Up Sound Source
         * Grid Sound Source
         */

        //Colors
        addToContentWrapper(new TitleLabel("Colors", theme));

        {
            //Init Mode Color Settings
            IndicatedToggleSwitch toggleSwitch = new IndicatedToggleSwitch(theme, "Dark Mode Enabled", "Light Mode Enabled");
            toggleSwitch.switchedOnProperty().setValue(theme.modeSwitchPR().getValue());
            toggleSwitch.switchedOnProperty().addListener(((observable, oldValue, newValue) -> {
                theme.setDarkMode(newValue);
            }));

            addToContentWrapper(createItemContainer("Theme Color", toggleSwitch));

        }


        {
            //Init Theme ColorPicker

            ColorPicker cp = new ColorPicker(theme.themeColorPR().getValue());
            cp.valueProperty().addListener(((observable, oldValue, newValue) -> {
                theme.themeColorPR().setValue(newValue);
            }));

            addToContentWrapper(createItemContainer("Theme Color", cp));
        }

        {
            //Init Player 1 Chess Color

            ColorPicker cp = new ColorPicker(theme.player1ChessColorPR().getValue());
            cp.valueProperty().addListener(((observable, oldValue, newValue) -> {
                theme.player1ChessColorPR().setValue(newValue);
            }));

            addToContentWrapper(createItemContainer("Player 1 Chess Color", cp));
        }

        {
            //Init Player 2 Chess Color

            ColorPicker cp = new ColorPicker(theme.player2ChessColorPR().getValue());
            cp.valueProperty().addListener(((observable, oldValue, newValue) -> {
                theme.player2ChessColorPR().setValue(newValue);
            }));

            addToContentWrapper(createItemContainer("Player 2 Chess Color", cp));
        }

        {
            //Init Chess Board Color 1

            ColorPicker cp = new ColorPicker(theme.chessBoardColor1PR().getValue());
            cp.valueProperty().addListener(((observable, oldValue, newValue) -> {
                theme.chessBoardColor1PR().setValue(newValue);
            }));

            addToContentWrapper(createItemContainer("ChessBoard Color 1", cp));
        }

        {
            //Init Chess Board Color 2

            ColorPicker cp = new ColorPicker(theme.chessBoardColor2PR().getValue());
            cp.valueProperty().addListener(((observable, oldValue, newValue) -> {
                theme.chessBoardColor2PR().setValue(newValue);
            }));

            addToContentWrapper(createItemContainer("ChessBoard Color 2", cp));
        }

        {
            //Init Chess Board Grid Color

            ColorPicker cp = new ColorPicker(theme.chessBoardGridColorPR().getValue());
            cp.valueProperty().addListener(((observable, oldValue, newValue) -> {
                theme.chessBoardGridColorPR().setValue(newValue);
            }));

            addToContentWrapper(createItemContainer("Chess Grid Color", cp));
        }

        //Backgrounds
        addToContentWrapper(new TitleLabel("Backgrounds", theme));

        {
            //Init Background Image Loader
            MetroButton loadButton = new MetroButton("Load from File", theme);
            loadButton.setOnAction(ActionEvent -> {
                FileChooser fileChooser = createImageFileChooser("Select Background Image");
                File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

                if (selectedFile != null) {
                    theme.backPanePR().setValue(new Background(new BackgroundImage(new Image(selectedFile.toURI().toString()),
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                } else {
                    Log0j.writeError("Image file is null. Cannot load background image.");
                }
            });

            //Init Background Pure Color Settings
            IndicatedToggleSwitch toggleSwitch = new IndicatedToggleSwitch(theme, "Theme Color Background", "Image Background");
            toggleSwitch.switchedOnProperty().addListener(((observable, oldValue, newValue) -> {
                if (newValue) {
                    theme.bindToThemeColorBackground(theme.backPanePR());
                    loadButton.setDisable(true);
                } else {
                    theme.unbindBackPane();
                    theme.backPanePR().setValue(Theme.defaultBackPaneBKGND);
                    loadButton.setDisable(false);
                }
            }));

            addToContentWrapper(createItemContainer("Is background pure color?", toggleSwitch));
            addToContentWrapper(createItemContainer("Background Image Source", loadButton));
        }

        {
            //Init ChessBoard Image Loader
            MetroButton loadButton = new MetroButton("Load from File", theme);
            loadButton.setOnAction(ActionEvent -> {
                FileChooser fileChooser = createImageFileChooser("Select ChessBoard Background Image");

                File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

                if (selectedFile != null) {
                    theme.chessBoardBackgroundPR().setValue(new Background(new BackgroundImage(new Image(selectedFile.toURI().toString()),
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
                } else {
                    Log0j.writeError("Image file is null. Cannot load ChessBoard image.");
                }
            });
            addToContentWrapper(createItemContainer("ChessBoard Image Source", loadButton));
        }

        //Playing
        addToContentWrapper(new TitleLabel("In-game", theme));

        {
            //Init PlayerIcon Image Loader
            MetroButton loadButton = new MetroButton("Load from File", theme);
            loadButton.setOnAction(ActionEvent -> {
                FileChooser fileChooser = createImageFileChooser("Select PlayerIcon Image");

                File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

                if (selectedFile != null) {
                    theme.playerIconPR().setValue(new Image(selectedFile.toURI().toString()));
                } else {
                    Log0j.writeError("Image file is null. Cannot load PlayerIcon image.");
                }
            });
            addToContentWrapper(createItemContainer("Player Icon Image Source", loadButton));
        }

        //todo: Add font settings
//        {
//            ComboBox comboBox = new ComboBox();
//            comboBox.backgroundProperty().bind(theme.backPanePR());
//            addToContentWrapper(createItemContainer("Font Select", comboBox));
//        }

        //Audio Settings
        addToContentWrapper(new TitleLabel("Audio", theme));

        {
            //Init MainView BGM Loader
            MetroButton loadButton = new MetroButton("Load from File", theme);
            loadButton.setOnAction(ActionEvent -> {
                FileChooser fileChooser = createAudioFileChooser("Select MainView BGM");

                File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

                if (selectedFile != null) {
                    theme.mainViewBGMSourcePR().setValue(selectedFile.toPath());
                } else {
                    Log0j.writeError("Audio file is null. Cannot load audio file.");
                }
            });
            addToContentWrapper(createItemContainer("MainView BGM Source", loadButton));
        }

        {
            //Init PlayPage BGM Loader
            MetroButton loadButton = new MetroButton("Load from File", theme);
            loadButton.setOnAction(ActionEvent -> {
                FileChooser fileChooser = createAudioFileChooser("Select PlayPage BGM");

                File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

                if (selectedFile != null) {
                    theme.playPageBGMSourcePR().setValue(selectedFile.toPath());
                } else {
                    Log0j.writeError("Audio file is null. Cannot load audio file.");
                }
            });
            addToContentWrapper(createItemContainer("PlayPage BGM Source", loadButton));
        }

        {
            //Init GamePage BGM Loader
            MetroButton loadButton = new MetroButton("Load from File", theme);
            loadButton.setOnAction(ActionEvent -> {
                FileChooser fileChooser = createAudioFileChooser("Select GamePage BGM");

                File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

                if (selectedFile != null) {
                    theme.gamePageBGMSourcePR().setValue(selectedFile.toPath());
                } else {
                    Log0j.writeError("Audio file is null. Cannot load audio file.");
                }
            });
            addToContentWrapper(createItemContainer("GamePage BGM Source", loadButton));
        }

        {
            //Init GameFinish BGM Loader
            MetroButton loadButton = new MetroButton("Load from File", theme);
            loadButton.setOnAction(ActionEvent -> {
                FileChooser fileChooser = createAudioFileChooser("Select GameFinish BGM");

                File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

                if (selectedFile != null) {
                    theme.gameFinishBGMSourcePR().setValue(selectedFile.toPath());
                } else {
                    Log0j.writeError("Audio file is null. Cannot load audio file.");
                }
            });
            addToContentWrapper(createItemContainer("GameFinish BGM Source", loadButton));
        }

        {
            //Init Chess Down Audio Loader
            MetroButton loadButton = new MetroButton("Load from File", theme);
            loadButton.setOnAction(ActionEvent -> {
                FileChooser fileChooser = createAudioFileChooser("Select Chess Down Audio");

                File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

                if (selectedFile != null) {
                    theme.chessDownSoundSourcePR().setValue(selectedFile.toPath());
                } else {
                    Log0j.writeError("Audio file is null. Cannot load audio file.");
                }
            });
            addToContentWrapper(createItemContainer("Chess Down Audio", loadButton));
        }

        {
            //Init Chess Up Audio Loader
            MetroButton loadButton = new MetroButton("Load from File", theme);
            loadButton.setOnAction(ActionEvent -> {
                FileChooser fileChooser = createAudioFileChooser("Select Chess Up Audio");

                File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

                if (selectedFile != null) {
                    theme.chessUpSoundSourcePR().setValue(selectedFile.toPath());
                } else {
                    Log0j.writeError("Audio file is null. Cannot load audio file.");
                }
            });
            addToContentWrapper(createItemContainer("Chess Up Audio", loadButton));
        }




    }

    private HBox createItemContainer(String text, Node node) {
        HBox container = createItemBaseContainer();
        TextLabel label = new TextLabel(text, theme);
        container.getChildren().addAll(label, node);
        return container;
    }

    private HBox createItemBaseContainer() {
        HBox container = new HBox(ITEM_GAP);
        container.setPrefHeight(PREF_CONTAINER_HEIGHT);
        return container;
    }


    private void addToContentWrapper(Node node) {
        contentWrapper.add(node, 0, contentWrapper.getRowCount());
    }

    @Override
    public void update() {

    }

    private FileChooser createImageFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image File",
                "*.bmp", "*.jpg", "*.jpeg", "*.png", "*.gif"));

        return fileChooser;
    }

    private FileChooser createAudioFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 File",
                "*.mp3", "*.wav", "*.aac"));

        return fileChooser;
    }
}
