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
public class EnemySpawner extends WorldObject{
    EnemyFrame frame;
    boolean spawned = false;
    
    public EnemySpawner(EnemyFrame frame, Point pos){
        this.pos = pos;
        this.frame = frame;
        this.hitbox = new Hitbox(new Point(-10,-2), new Point(10,5), new Point(), this);
        recreate();
    }
    
    @Override
    public void onCollision(WorldObject other){
        if(other instanceof Player && !spawned){
            Simon.world.add(EnemyFactory.getFromFrame(frame, pos.add(new Point(0,60))));
            Simon.world.remove(this);
            spawned = true;
        }
    }
}
