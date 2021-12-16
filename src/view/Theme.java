package view;

import controller.logger.Log0j;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.*;
import java.net.URISyntaxException;

public class Theme {

    //Fields below are all default settings.
    //They are all base on experience.
    public static final double DEFAULT_CORNER_RADII = 10;

    public static final int DEFAULT_MAIN_WINDOW_PREF_WIDTH = 1280;
    public static final int DEFAULT_MAIN_WINDOW_PREF_HEIGHT = 800;
    public static final int DEFAULT_MAIN_WINDOW_MIN_WIDTH = 600;
    public static final int DEFAULT_MAIN_WINDOW_MIN_HEIGHT = 450;

    public static final String defaultBGMSource = "/res/bgm.mp3";
    public static final String defaultChessSoundSource = "/res/soundeffects/Chess.mp3";
    public static final double defaultBGMVolume = 0.4;
    public static final double defaultEffectVolume = 0.4;

    public static final Background defaultBackPaneBKGND = new Background(new BackgroundImage(new Image("/res/background.jpg"),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));

    public static final Background defaultFrontPaneBKGND = new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.15),
            new CornerRadii(MainView.VIEWCOVER_CORNER_RADII), null));

    public static final Color defaultThemeColor = Color.rgb(29, 31, 44);
    public static final Paint defaultThemePaint = Color.rgb(29, 31, 44);
    public static final Font defaultTitleFontFamily = new Font("Garamond", 25);
    public static final Paint defaultTitleFontPaint = Color.WHITE;
    public static final Font defaultMenuFontFamily = new Font("Segoe UI", 14);
    public static final Paint defaultMenuFontPaint = Color.WHITE;
    public static final Font defaultTextFontFamily = new Font("Segoe UI", 16);
    public static final Paint defaultTextFontPaint = Color.WHITE;

    public static final Paint defaultPlayerChessPaint1 = Color.WHITE;
    public static final Paint defaultPlayerChessPaint2 = Color.BLACK;
    public static final Paint defaultChessBoardPaint1 = Color.rgb(29, 31, 44);
    public static final Paint defaultChessBoardPaint2 = Color.rgb(55, 58, 84);
    public static final Paint defaultChessBoardGridPaint = Color.rgb(255, 255, 255, 0.50);
    public static final Background defaultChessBoardBackground = new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(DEFAULT_CORNER_RADII), null));

    //Below are properties

    //Stage related
    public final SimpleDoubleProperty mainWindowPrefWidth;
    public final SimpleDoubleProperty mainWindowPrefHeight;
//    public int mainWindowMinWidth;
//    public int mainWindowMinHeight;


    //Audio related
    public MediaPlayer bgmPlayer;
    public final StringProperty bgmSourcePR;
    public final StringProperty chessSoundSourcePR;
    public final DoubleProperty bgmVolumePR;
    public final DoubleProperty effectVolumePR;

    //Theme related
    public final ObjectProperty<Background> backPaneBackgroundPR;
    public final ObjectProperty<Background> frontPaneBackgroundPR;
//    public final BooleanProperty isBackgroundPureColor;

    public final ObjectProperty<Color> themeColorPR;
    public final ObjectProperty<Paint> themePaintPR;

    public final ObjectProperty<Font> titleFontFamilyPR;
    public final ObjectProperty<Paint> titleFontPaintPR;

    public final ObjectProperty<Font> menuFontFamilyPR;
    public final ObjectProperty<Paint> menuFontPaintPR;

    public final ObjectProperty<Font> textFontFamilyPR;
    public final ObjectProperty<Paint> textFontPaintPR;

    //Chessboard Color
    public final ObjectProperty<Paint> playerChessPaintPR1;
    public final ObjectProperty<Paint> playerChessPaintPR2;
    public final ObjectProperty<Paint> chessBoardPaintPR1;
    public final ObjectProperty<Paint> chessBoardPaintPR2;
    public final ObjectProperty<Paint> chessBoardGridPaintPR;
    public final ObjectProperty<Background> chessBoardBackgroundPR;



    public final Stage primaryStage;

    //todo: finish chessboard paint default
    //Default Theme
    public Theme(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainWindowPrefWidth = new SimpleDoubleProperty();
        mainWindowPrefHeight = new SimpleDoubleProperty();

        bgmVolumePR = new SimpleDoubleProperty();
        bgmSourcePR = new SimpleStringProperty();
        chessSoundSourcePR = new SimpleStringProperty();
        effectVolumePR = new SimpleDoubleProperty();

        backPaneBackgroundPR = new SimpleObjectProperty<>();
        frontPaneBackgroundPR = new SimpleObjectProperty<>();

        themeColorPR = new SimpleObjectProperty<>();
        themePaintPR = new SimpleObjectProperty<>();
        titleFontFamilyPR = new SimpleObjectProperty<>();
        titleFontPaintPR = new SimpleObjectProperty<>();
        menuFontFamilyPR = new SimpleObjectProperty<>();
        menuFontPaintPR = new SimpleObjectProperty<>();
        textFontFamilyPR = new SimpleObjectProperty<>();
        textFontPaintPR = new SimpleObjectProperty<>();

        playerChessPaintPR1 = new SimpleObjectProperty<>();
        playerChessPaintPR2 = new SimpleObjectProperty<>();
        chessBoardPaintPR1 = new SimpleObjectProperty<>();
        chessBoardPaintPR2 = new SimpleObjectProperty<>();
        chessBoardGridPaintPR = new SimpleObjectProperty<>();
        chessBoardBackgroundPR = new SimpleObjectProperty<>();

        applyDefaultTheme();
        initRelations();
        Log0j.writeLog("Theme initialized.");

        //Audio Applied.

        loadTheme();

        try {
            Media media = new Media(getClass().getResource(bgmSourcePR.getValue()).toURI().toString());
            bgmPlayer = new MediaPlayer(media);
            bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            bgmPlayer.volumeProperty().bind(bgmVolumePR);
            Log0j.writeLog("BGM Player Initialized.");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log0j.writeLog("Failed to initialize BGM.");
        }
    }

    public void applyDefaultTheme() {
        primaryStage.setWidth(DEFAULT_MAIN_WINDOW_PREF_WIDTH);
        primaryStage.setHeight(DEFAULT_MAIN_WINDOW_PREF_HEIGHT);

        bgmVolumePR.setValue(defaultBGMVolume);
        bgmSourcePR.setValue(defaultBGMSource);
        chessSoundSourcePR.setValue(defaultChessSoundSource);
        effectVolumePR.setValue(defaultEffectVolume);

        backPaneBackgroundPR.setValue(defaultBackPaneBKGND);
        frontPaneBackgroundPR.setValue(defaultFrontPaneBKGND);

        themeColorPR.setValue(defaultThemeColor);
//        themePaintPR.setValue(defaultThemePaint);

        titleFontFamilyPR.setValue(defaultTitleFontFamily);
        titleFontPaintPR.setValue(defaultTitleFontPaint);
        menuFontFamilyPR.setValue(defaultMenuFontFamily);
        menuFontPaintPR.setValue(defaultMenuFontPaint);
        textFontFamilyPR.setValue(defaultTextFontFamily);
        textFontPaintPR.setValue(defaultTextFontPaint);

        playerChessPaintPR1.setValue(defaultPlayerChessPaint1);
        playerChessPaintPR2.setValue(defaultPlayerChessPaint2);
        chessBoardPaintPR1.setValue(defaultChessBoardPaint1);
        chessBoardPaintPR2.setValue(defaultChessBoardPaint2);
        chessBoardGridPaintPR.setValue(defaultChessBoardGridPaint);
        chessBoardBackgroundPR.setValue(defaultChessBoardBackground);

        Log0j.writeLog("Default Theme Applied.");

    }

    public void initRelations() {
        themePaintPR.bind(Bindings.createObjectBinding(() -> {
            return themeColorPR.getValue();
        }, themeColorPR));
        Log0j.writeLog("Relation initialized.");
    }

    public void bindToStage(Stage stage) {
        bindToStageWidth(stage.widthProperty());
        bindToStageHeight(stage.heightProperty());
    }

    public void bindToStageWidth(ReadOnlyDoubleProperty widthProperty) {
        mainWindowPrefWidth.bind(widthProperty);
    }

    public void bindToStageHeight(ReadOnlyDoubleProperty heightProperty) {
        mainWindowPrefHeight.bind(heightProperty);
    }

    public MediaPlayer getBgmPlayer(){
        return bgmPlayer;
    }

    public StringProperty getChessSoundSource(){
        return chessSoundSourcePR;
    }

    public double getEffectVolume(){
        return effectVolumePR.getValue();
    }

    public void bindBGMVolumeTo(DoubleProperty volumePR){
        this.bgmVolumePR.bind(volumePR);
    }

    public void unbindBGMVolume(){
        bgmVolumePR.unbind();
    }

    public void bindEffectVolumeTo(DoubleProperty volumePR){
        this.effectVolumePR.bind(volumePR);
    }

    public void unbindEffectVolume(){
        effectVolumePR.unbind();
    }


    public void bindToBackPane(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(() -> {
            Background newBackground = backPaneBackgroundPR.getValue();
            return newBackground;
        }, backPaneBackgroundPR));
    }

    public void bindBackPanePRTo(ObjectProperty<Background> background) {
        backPaneBackgroundPR.bind(Bindings.createObjectBinding(() -> {
            Background newBackground = background.getValue();
            return newBackground;
        }, background));
    }

    public void unbindBackPane() {
        backPaneBackgroundPR.unbind();
    }

    public void bindToFrontPane(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(() -> {
            Background newBackground = frontPaneBackgroundPR.getValue();
            return newBackground;
        }, frontPaneBackgroundPR));
    }

    public void bindFrontPanePRTo(ObjectProperty<Background> background) {
        frontPaneBackgroundPR.bind(Bindings.createObjectBinding(() -> {
            Background newBackground = background.getValue();
            return newBackground;
        }, background));
    }

    public void unbindFrontPane() {
        frontPaneBackgroundPR.unbind();
    }


    public void bindToThemePaint(ObjectProperty<Paint> paint) {
        paint.bind(Bindings.createObjectBinding(() -> {
            Paint newPaint = themePaintPR.getValue();
            return newPaint;
        }, themePaintPR));
    }

    public void bindToPaintBackground(ObjectProperty<Background> background) {
        background.bind(Bindings.createObjectBinding(() -> {
            Background newBackground = new Background(new BackgroundFill(themePaintPR.getValue(), null, null));
            return newBackground;
        }, themePaintPR));
    }

    public void bindToTitleFontFamily(ObjectProperty<Font> fontFamily) {
        fontFamily.bind(Bindings.createObjectBinding(() -> {
            return titleFontFamilyPR.getValue();
        }, titleFontFamilyPR));
    }

    public void bindToTitleFontPaint(ObjectProperty<Paint> fontPaint) {
        fontPaint.bind(Bindings.createObjectBinding(() -> {
            return titleFontPaintPR.getValue();
        }, titleFontPaintPR));
    }
    public void bindToMenuFontFamily(ObjectProperty<Font> fontFamily) {
        fontFamily.bind(Bindings.createObjectBinding(() -> {
            return menuFontFamilyPR.getValue();
        }, menuFontFamilyPR));
    }

    public void bindToMenuFontPaint(ObjectProperty<Paint> fontPaint) {
        fontPaint.bind(Bindings.createObjectBinding(() -> {
            return menuFontPaintPR.getValue();
        }, menuFontPaintPR));
    }

    public void bindToTextFontFamily(ObjectProperty<Font> fontFamily) {
        fontFamily.bind(Bindings.createObjectBinding(() -> {
            return textFontFamilyPR.getValue();
        }, textFontFamilyPR));
    }

    public void bindToTextFontPaint(ObjectProperty<Paint> fontPaint) {
        fontPaint.bind(Bindings.createObjectBinding(() -> {
            return textFontPaintPR.getValue();
        }, textFontPaintPR));
    }

    public void bindToChessBoardPaint1(ObjectProperty<Background> background){
        background.bind(Bindings.createObjectBinding(()->{
            return new Background(new BackgroundFill(chessBoardPaintPR1.getValue(), null, null));
        }, chessBoardPaintPR1));
    }

    public void bindToChessBoardPaint2(ObjectProperty<Background> background){
        background.bind(Bindings.createObjectBinding(()->{
            return new Background(new BackgroundFill(chessBoardPaintPR2.getValue(), null, null));
        }, chessBoardPaintPR2));
    }

    public void bindToBorderPaint(ObjectProperty<Border> borderProperty){
        borderProperty.bind(Bindings.createObjectBinding(()->{
            return new Border(new BorderStroke(chessBoardGridPaintPR.getValue(), BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT));
        }, chessBoardGridPaintPR));
    }


    public void loadTheme() {
        try{
            loadTheme(getClass().getResource("/res/theme.json").toURI().toString());
        }catch (URISyntaxException e){
            e.printStackTrace();
            Log0j.writeLog("Error occurred because cannot found theme.json. No theme is changed.");
        }
    }

    public void loadTheme(String srcPath) {
        try {
            //Try to read the configuration file
            FileReader themeFileSrc = new FileReader(srcPath);
            JSONObject jsonObject = new JSONObject(themeFileSrc);

            themeFileSrc.close();
            Log0j.writeLog("Theme loaded.");
        } catch (IOException e) {
            e.printStackTrace();
            Log0j.writeLog("Error occurred because theme source specified does not exist. No theme is changed.");
        }
    }

    public void saveTheme() {

        Log0j.writeLog("Theme saved.");
    }

}
