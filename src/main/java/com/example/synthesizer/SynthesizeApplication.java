package com.example.synthesizer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.awt.*;
import java.util.ArrayList;

public class SynthesizeApplication extends Application {
    private AnchorPane mainCanvas_;
    protected static SpeakerWidget speaker_;
    public static ArrayList<Widget> widgets_ = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        Scene scene = new Scene(root, 800, 500);

        // set right panel
        VBox rightPanel = new VBox();
        rightPanel.setStyle("-fx-background-color: lightblue");
        rightPanel.setAlignment(Pos.CENTER);
        rightPanel.setSpacing(50);
        rightPanel.setPadding(new Insets(10));
        Button sineWaveBtn = new Button("Sine Wave");
        sineWaveBtn.setMaxWidth(100);
        sineWaveBtn.setOnMousePressed(e -> createSineWaveWidget());
        Button squareWaveBtn = new Button("Square Wave");
        squareWaveBtn.setMaxWidth(100);
        squareWaveBtn.setOnMousePressed(e -> creatSquareWaveWidget());
        Button whiteNoiseBtn = new Button("White Noise");
        whiteNoiseBtn.setMaxWidth(100);
        whiteNoiseBtn.setOnMousePressed(e -> createWhiteNoiseWidget());
        Button linearRampBtn = new Button("Linear Ramp");
        linearRampBtn.setMaxWidth(100);
        linearRampBtn.setOnMousePressed(e -> createLinearRampWidget());
        rightPanel.getChildren().add(sineWaveBtn);
        rightPanel.getChildren().add(squareWaveBtn);
        rightPanel.getChildren().add(whiteNoiseBtn);
        rightPanel.getChildren().add(linearRampBtn);

        // set center panel
        mainCanvas_ = new AnchorPane();
//        mainCanvas_.setStyle("-fx-background-color: ");
        speaker_ = new SpeakerWidget(mainCanvas_);
        widgets_.add(speaker_);

        // set bottom panel
        HBox bottomPanel = new HBox();
        bottomPanel.setStyle("-fx-background-color: lightblue");
        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.setSpacing(50);
        bottomPanel.setPadding(new Insets(10));
        Button vfSineWaveBtn = new Button("VF Sine Wave");
        vfSineWaveBtn.setMinWidth(100);
        vfSineWaveBtn.setOnMousePressed(e -> createVFSineWaveWidget());
        Button filterBtn = new Button("Volume");
        filterBtn.setMinWidth(100);
        filterBtn.setOnMousePressed(e -> createFilterWidget());
        Button playBtn = new Button("Play Sound");
        playBtn.setMinWidth(100);
        // set the glowing effect for play button
        int depth = 70;
        DropShadow borderGlow= new DropShadow();
        borderGlow.setOffsetY(0f);
        borderGlow.setOffsetX(0f);
        borderGlow.setColor(Color.PINK);
        borderGlow.setWidth(depth);
        borderGlow.setHeight(depth);
        playBtn.setEffect(borderGlow);
//        playBtn.setStyle("-fx-background-color: pink; -fx-text-fill: white; -fx-font-weight: 20px");
        playBtn.setOnMousePressed(e -> playSound());
        bottomPanel.getChildren().add(filterBtn);
        bottomPanel.getChildren().add(vfSineWaveBtn);
        bottomPanel.getChildren().add(playBtn);

        root.setRight(rightPanel);
        root.setCenter(mainCanvas_);
        root.setBottom(bottomPanel);

        stage.setTitle("Sonia's Audio Synthesizer");
        stage.setScene(scene);
        stage.show();
    }

    private void createVFSineWaveWidget() {
        VFSineWaveWidget vfSineWaveWidget = new VFSineWaveWidget(mainCanvas_);
        widgets_.add(vfSineWaveWidget);
    }

    private void creatSquareWaveWidget() {
        SquareWaveWidget squareWaveWidget = new SquareWaveWidget(mainCanvas_);
        widgets_.add(squareWaveWidget);
    }

    private void createFilterWidget() {
        FilterWidget filterWidget = new FilterWidget(mainCanvas_);
        widgets_.add(filterWidget);
    }

    private void createSineWaveWidget() {
        SineWaveWidget sineWaveWidget = new SineWaveWidget(mainCanvas_);
        widgets_.add(sineWaveWidget);
    }

    private void createWhiteNoiseWidget() {
        WhiteNoiseWidget whiteNoiseWidget = new WhiteNoiseWidget(mainCanvas_);
        widgets_.add(whiteNoiseWidget);
    }

    private void createLinearRampWidget() {
        LinearRampWidget linearRampWidget = new LinearRampWidget(mainCanvas_);
        widgets_.add(linearRampWidget);
    }

    private void playSound(){
        if (!widgets_.isEmpty()) {
            try {
                Clip c = AudioSystem.getClip();
                AudioListener listener = new AudioListener(c);
                AudioClip clip = speaker_.getAudioComponent().getClip();
                AudioFormat format16 = new AudioFormat( 44100, 16, 1, true, false );
                c.open(format16, clip.getData(), 0, clip.getLength());
                c.start();
                c.addLineListener(listener);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}