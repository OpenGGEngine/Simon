package simon;

public class Attack extends WorldObject {
    int damage = 0;
    int lifetime = 0;
    float fasdfasdfasdf = 0;
    
    @Override
    public void update(float delta){
        super.update(delta);
        fasdfasdfasdf += delta;
        if(fasdfasdfasdf > lifetime){
            Simon.world.remove(this);
        }
    }
    
    public void onCollision(WorldObject other){
        if(other instanceof Player){
            Simon.player.damage();
        }
        
        Simon.world.remove(this);
    }
}
