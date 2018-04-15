/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxemulator;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Javier
 */
public class JFXEmulator extends Application {
    public static JFXEmulator e;
    static Tile defaulttile = new Tile(' ', Color.BLACK, Color.BLACK);
    Tile[][] screen = new Tile[400][200];
    static HashMap<Integer, Tile[][]> spritesheet = new HashMap<>();
    Scene scene;
    Canvas canvas;
    GraphicsContext gc;
    AnimationTimer timer;
    Random random = new Random();
    World world = new World();
    int i = 0;
    Sprite sprite;
    int xvel = 0;
    Sprite psprite;
    Stage stage;
    public boolean ready = false;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        e = this;
        for(int i = 0; i < 400; i++){
            for(int j = 0; j < 200; j++){
                screen[i][j] = defaulttile;
            }
        }
        
        StackPane root = new StackPane();
        
        scene = new Scene(root, 1200, 1200);
        
        canvas = new Canvas(1200, 1200);
        gc = canvas.getGraphicsContext2D();
        
        root.getChildren().add(canvas);
        
        primaryStage.setTitle("Simon");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                render();
            }
        };
        timer.start();

        Tile[][] sprite1, sprite2, sprite3;
        try {
            sprite1 = Converter.load("C:\\res\\sprite.png");
            sprite2 = Converter.load("C:\\res\\dab\\char.png");
            sprite3 = Converter.load("C:\\res\\sprite1.png");
        } catch (IOException ex) {
            sprite1 = new Tile[32][16];
            sprite2 = new Tile[32][16];
            sprite3 = new Tile[32][16];

        }

        spritesheet.put(0, sprite1);
        spritesheet.put(1, sprite2);
        spritesheet.put(2, sprite3);
        
        try {
            world.background = new Background(Converter.load("C:\\res\\dab\\grass.png"));
        } catch (IOException ex) {
            Logger.getLogger(JFXEmulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ready = true;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
     @Override
    public void stop() throws Exception {
        timer.stop();
    }
    
    public Tile[][] swap(Tile[][] news){
        Tile[][] swap = screen;
        screen = news;
        return swap;
    }
    
    public void render(){
        
        for(int i = 0; i < screen.length; i++){
            for(int j = 0; j < screen[0].length; j++){
                Tile t = screen[i][j];
                gc.setFill(t.background);//)Color.rgb((int) (t.background.getRed() + t.color.getRed()), (int) (t.background.getGreen() + t.color.getGreen()), (int) (t.background.getBlue() + t.color.getBlue())));
                gc.fillRect(i * 6, j * 6, 6, 6);
                gc.setFill(t.color);
                //gc.fillText(t.character + "", i * 6, (j + 1) * 6, 6);
            }
        }
    }
    
    public void update(ByteBuffer buffer){
        world.update(buffer);
    }
}
