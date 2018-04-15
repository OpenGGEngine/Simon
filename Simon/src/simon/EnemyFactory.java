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
public abstract class EnemyFactory {
    static HashMap<String, EnemyFrame> frames = new HashMap<>();
    
    static WorldAction run = (o, t) -> ((Enemy) o).moveToPlayer(5);
    static WorldAction runner = (o, t) -> {
        if(o.distanceToPlayer() < 100)
            ((Enemy) o).moveToPlayer(60);
        ((Enemy) o).jump(80);
    };
    
    static WorldAction throwHammersAndRunBack = (o, t) -> {
        if(o.distanceToPlayer() < 100)
            ((Enemy) o).moveToPlayer(-20);
        else
            o.xvel = 0;
        if(o.counter > 3){
            if(o.distanceToPlayer() > 130) return;
            Attack attack = new Attack();
            attack.pos = new Point(o.pos).add(new Point(0, 20));
            attack.lifetime = 5;
            attack.damage = 1;
            attack.sprite = "meatball";
            attack.grounded = false;
            attack.yvel = 50;
            attack.xvel = (player.pos.x < o.pos.x) ? -50f : 50f; 
            attack.hitbox = new Hitbox(new Point(4, 0f), new Point(10, 10), attack.pos, attack);
            attack.hitbox.center = attack.pos;
            attack.hitbox.recreate();
            Simon.world.add(attack);
            o.counter = 0;
        }
    };
    static WorldAction moveAndFire = (o, t) -> {
        if(o.distanceToPlayer() < 120)
            ((Enemy) o).moveToPlayer(10);
        else
            o.xvel = 0;
        if(o.counter > 3){
            if(o.distanceToPlayer() > 150) return;
            Attack attack = new Attack();
            attack.pos = new Point(o.pos).add(new Point(0, 20));
            attack.lifetime = 8;
            attack.gravity = false;
            attack.damage = 1;
            attack.sprite = "meatball";
            attack.grounded = false;
            attack.yvel = 0;
            attack.xvel = (player.pos.x < o.pos.x) ? -50f : 50f; 
            attack.hitbox = new Hitbox(new Point(1, 0f), new Point(3, 3), attack.pos, attack);
            attack.hitbox.center = attack.pos;
            attack.hitbox.recreate();
            Simon.world.add(attack);
            o.counter = 0;
        }
    };
    
    public static void create(){
        EnemyFrame hambro = new EnemyFrame();
        hambro.hbbl = new Point(2,0);
        hambro.hbtr = new Point(10,10);
        hambro.sprite = "birdo";
        hambro.behavior = throwHammersAndRunBack;
        frames.put("hammerbro", hambro);
        
        EnemyFrame turret = new EnemyFrame();
        turret.hbbl = new Point(2,0);
        turret.hbtr = new Point(10,10);
        turret.sprite = "birdo";
        turret.behavior = moveAndFire;
        frames.put("turret", turret);
    }
    
    public static Enemy getEnemy(String name, Point pos){
        return getFromFrame(frames.get(name), pos);
    }
    
    public static Enemy getFromFrame(EnemyFrame name, Point pos){
        Enemy e = name.create();
        e.pos = pos;
        e.recreate();
        return e;
    }
}
