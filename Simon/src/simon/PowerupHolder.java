/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simon;

/**
 *
 * @author Javier
 */
public class PowerupHolder extends WorldObject{
    Powerup p;
    boolean collided = false;

    public PowerupHolder(){
        this.hitbox = new Hitbox(new Point(-1,-1), new Point(1,1), new Point(), this);
        recreate();
    }
    
    @Override
    public void onCollision(WorldObject other) {
       if(other instanceof Player && !collided){
           Simon.player.pups.add(p);
           p.activate.run();
           Simon.world.remove(this);
           collided = true;
       }
    }
    
    
}
