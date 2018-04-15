package simon;

import java.util.ArrayList;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

public class Player extends WorldObject {
    int lifestage = 1;
    int lives=3;
    ArrayList<Powerup> pups = new ArrayList<>();
    Weapon weapon;
    int speed = 2;
    int jumpheight = 5;
    float damagemult = 1.0f;
    float vel = 50f;
    boolean invincible = false;
    boolean doublejump = false;
    boolean diddob = false;
    
    public Player(){
        grav = 250;
        this.sprite = "player";
        pos = new Point(40,50);
        hitbox = new Hitbox(new Point(0,0), new Point(16,32), pos, this);
        recreate();
    }
    
    @Override
    public void update(float delta){
        super.update(delta);
        ArrayList<Powerup> rpups = new ArrayList<>();
        for(Powerup p : pups){
            p.time += delta;
            if(p.time > p.length){
                rpups.add(p);
                p.deactivate.run();
            }
        }
        pups.removeAll(rpups);
        if(grounded) diddob = false;
    }
    
    public void damage(){
        if(lifestage == 1){
            System.exit(0);
        }else{
            lifestage--;
        }
    }
    
    public void keyPress(GlobalKeyEvent event){
        if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_LEFT){
            xvel -= vel;
        }
        if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_RIGHT){
            xvel += vel;
        }
        if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_UP){
            if(grounded){
                yvel += 200;
            }if(!grounded && doublejump && !diddob){
                yvel += 200;
                diddob = true;
            }
        }
    }
    
    public void keyRelease(GlobalKeyEvent event){
        if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_LEFT){
            xvel += vel;
        }
        if(event.getVirtualKeyCode() == GlobalKeyEvent.VK_RIGHT){
            xvel -= vel;
        }
    }
}
