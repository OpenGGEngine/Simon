/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simon;

import java.util.ArrayList;

/**
 *
 * @author Javier
 */
public class CollisionManager {
    static ArrayList<WorldObject> obs = new ArrayList<>();
    static ArrayList<Hitbox> world = new ArrayList<>();
    
    public static CollisionTuple collide(WorldObject object){
        CollisionTuple tt = new CollisionTuple();
        int numa = 0;
        for(Hitbox x : world){
            if(x.collide(object.hitbox) && numa == 0){
                tt.w1 =  x;
                numa+=1;
            
            }else if(x.collide(object.hitbox)){
                tt.w3 =  x;
                break;
            }
            
        }
        
        for(WorldObject t : obs){
            if(t == object) continue;
            if (object.hitbox.collide(t.hitbox)){
                object.onCollision(t);
                t.onCollision(object);
                tt.w2 = t.hitbox;
            }
            
        }
        return tt;
    }
    
    static public class CollisionTuple{
        Hitbox w1 = null, w2 = null,w3 = null;
    }
}
