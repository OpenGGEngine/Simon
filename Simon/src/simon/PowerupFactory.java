/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simon;

import java.util.HashMap;
import static simon.Simon.player;

/**
 *
 * @author Javier
 */
public class PowerupFactory {
    static HashMap<String, PowerupFrame> frames = new HashMap<>();
    
    public static void create(){
        PowerupFrame djump = new PowerupFrame();
        djump.activate = () -> Simon.player.doublejump = true;
        djump.deactivate = () -> Simon.player.doublejump = false;
        djump.time = 10;
        frames.put("doublejump", djump);
        
        PowerupFrame onelife = new PowerupFrame();
        onelife.activate = () -> Simon.player.lives++;
        onelife.time = 0;
        frames.put("onelife", onelife);
        
        PowerupFrame bee = new PowerupFrame();
        bee.activate = () -> {
            Simon.player.grav = 20;
            Simon.player.jumpspeed = 40;
            Simon.player.sprite = "bee";
        };
        
        bee.deactivate = () -> {
            Simon.player.grav = 200;
            Simon.player.jumpspeed = 150;
            Simon.player.sprite = "player";
        };
        
        bee.time = 8;
        frames.put("bee", bee);
    }
    
    public static Powerup getPowerup(String name){
        return getFromFrame(frames.get(name));
    }
    
    public static Powerup getFromFrame(PowerupFrame name){
        Powerup e = name.getPowerup();
        return e;
    }
}