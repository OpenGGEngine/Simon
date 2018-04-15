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

        Tile[][] def, simon, bee, turt, bvr, wing, spag, spagb, bs, dew, dewred, ddos;
        try {
            def   = Converter.load("C:\\res\\dab\\char.png");
            simon = Converter.load("C:\\res\\dab\\simon.png");
            bee   = Converter.load("C:\\res\\dab\\bee.png");
            turt  = Converter.load("C:\\res\\dab\\turret.png");
            bvr   = Converter.load("C:\\res\\dab\\bvr.png");
            wing  = Converter.load("C:\\res\\dab\\wing.png");
            spag  = Converter.load("C:\\res\\dab\\spaghet.png");
            spagb = Converter.load("C:\\res\\dab\\spaghetballs.png");
            bs    = Converter.load("C:\\res\\dab\\balls.png");
            dew   = Converter.load("C:\\res\\dab\\dew.png");
            dewred= Converter.load("C:\\res\\dab\\dewred.png");
            ddos  = Converter.load("C:\\res\\dab\\ddos.png");

        } catch (IOException ex) {
            def   = new Tile[32][16];
            simon = new Tile[32][16];
            bee   = new Tile[32][16];
            turt  = new Tile[32][16];
            bvr   = new Tile[32][16];
            wing  = new Tile[32][16];
            spag  = new Tile[32][16];
            spagb = new Tile[32][16];
            bs    = new Tile[32][16];
            dew   = new Tile[32][16];
            dewred= new Tile[32][16];
            ddos  = new Tile[32][16];

        }

        spritesheet.put(0, def   );
        spritesheet.put(1, simon );
        spritesheet.put(2, bee   );
        spritesheet.put(3, turt  );
        spritesheet.put(4, bvr   );
        spritesheet.put(5, wing  );
        spritesheet.put(6, spag  );
        spritesheet.put(7, spagb );
        spritesheet.put(8, bs    );
        spritesheet.put(9, dew   );
        spritesheet.put(10, dewred);
        spritesheet.put(11, ddos  );
        
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
