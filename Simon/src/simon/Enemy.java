package simon;

public class Enemy extends WorldObject {

    int touchdamage;
    int attack;
    WorldAction action;
    int lifestage;
    
    @Override
    public void update(float delta){
        super.update(delta);
        action.doAction(this, delta);
    }
    
    public void move(float speed){
        xvel = speed;
    }
    
    public void moveToPlayer(float speed){
        xvel = speed;
        if(Simon.player.pos.x < pos.x) xvel = -xvel;
    }
    
    public void jump(float speed){
        if(grounded ) yvel = 10;
    }
    
    @Override
    public void onCollision(WorldObject other){
        if(other instanceof Player) Simon.player.damage();
    }
}
