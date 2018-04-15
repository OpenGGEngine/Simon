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
public class EnemyFrame {
    String sprite;
    WorldAction behavior;
    Point hbtr;
    Point hbbl;
    
    public Enemy create(){
        Enemy e = new Enemy();
        e.action = behavior;
        e.hitbox = new Hitbox(hbbl, hbtr, new Point(), e);
        e.sprite = sprite;
        return e;
    }
}
