/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxemulator;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier
 */
public class World {
    final int WIDTH = 400, HEIGHT = 200;
    final Object monitor = new Object();
    Background background;
    Tile[][] screen = new Tile[WIDTH][HEIGHT];
    List<Sprite> sprites = new LinkedList<>();
    
    
    
    int xpos = 0;
    
    public World(){
        for(int i = 0; i < 400; i++){
            for(int j = 0; j < 200; j++){
                screen[i][j] = JFXEmulator.defaulttile;
            }
        }
    }
    
    public Tile[][] get(){
            return screen;
    }
    
    public void render(){
        if(!JFXEmulator.e.stage.isShowing()) System.exit(0);
        screen = JFXEmulator.e.swap(screen);
        
        if(xpos < 0) xpos = 0;
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                screen[i][j] = background.entries[i+xpos][j];
            }
        }

        for(Sprite sprite : sprites){
            Tile[][] sdata = JFXEmulator.spritesheet.get(sprite.spriteid);
            int xp = sprite.xp;
            int yp = sprite.yp + sdata[0].length;
            for (int i = 0; i < sdata.length; i++) {
                for (int j = 0; j < sdata[0].length; j++) {
                    if (((i + xp) - xpos < 0
                            || (i + xp) - xpos >= WIDTH)
                            || ((j + (HEIGHT - yp)) < 0
                            || (j + (HEIGHT - yp)) >= HEIGHT)) {
                    }else{
                        screen[(i + xp) - xpos][j + (HEIGHT - yp)] = sdata[i][j];
                    }
                }
            }
        }
    }
    
    public void update(ByteBuffer buffer){
        try {
            GGInputStream in = new GGInputStream(buffer);
            int len = in.readInt();
            xpos = in.readInt();
            in.readInt();
            
            int spritequant = in.readInt();
            sprites.clear();
            
            //System.out.println(spritequant + " sprites rendered");
            for(int i = 0; i < spritequant; i++){
                Sprite sprite = new Sprite();
                sprite.xp = in.readInt();
                sprite.yp = in.readInt();
                sprite.rot = in.readInt();
                sprite.spriteid = in.readInt();
                sprites.add(sprite);
            }
           
            render();
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
