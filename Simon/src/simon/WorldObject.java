package simon;

import simon.CollisionManager.CollisionTuple;

public abstract class WorldObject implements Renderable {

    Point pos = new Point();

    float counter = 0;
    String sprite = "";
    boolean gravity = true;
    float grav = 100;
    float xvel = 0;
    float yvel = 0;
    int animstate = 0;
    Hitbox hitbox = new Hitbox(new Point(0,0), new Point(16,16),  new Point(), this);
   
    boolean grounded = true;

    public WorldObject(){
        Simon.renderables.add(this);
    }
    
    public void update(float delta) {
        counter += delta;
        Point tpos = new Point(pos);

        pos.x += delta * xvel;
        pos.y += delta * yvel;

        hitbox.recreate();
        CollisionTuple x = CollisionManager.collide(this);
        grounded = false;
        
        if (x.w1 != null) {
            Hitbox newh = this.hitbox.clone();
            pos = tpos;
            recreate();
            
            if(hitbox.topright.x < x.w1.botleft.x && newh.topright.x >= x.w1.botleft.x){
                if(xvel < 0) pos.x += delta * xvel;
                System.out.println("gloug");
            }else if(hitbox.botleft.x >= x.w1.topright.x && newh.botleft.x < x.w1.topright.x){
                if(xvel > 0) pos.x += delta * xvel;
            }else{
                pos.x += delta * xvel;
            }

            if(hitbox.botleft.y > x.w1.topright.y && newh.botleft.y <= x.w1.topright.y){
                if(yvel > 0) pos.y += delta * yvel;
                grounded = true;
            }else if(hitbox.topright.y <= x.w1.botleft.y && newh.topright.y > x.w1.botleft.y){
                if(yvel < 0) pos.y += delta * yvel;
            }else{
                pos.y += delta * yvel;
            }
            if(hitbox.topright.y < x.w1.botleft.y && newh.topright.y >= x.w1.botleft.y) grounded = true;
        }
        
        if (x.w2 != null) {
            pos = tpos;
        }
        tpos = new Point(pos);
        if (x.w3 != null) {
     
            Hitbox newh = this.hitbox.clone();
            pos = tpos;
            recreate();
            
            if(hitbox.topright.x < x.w3.botleft.x && newh.topright.x >= x.w3.botleft.x){
                if(xvel < 0) pos.x += delta * xvel;
                 System.out.println("yeast");
            }else if(hitbox.botleft.x >= x.w3.topright.x && newh.botleft.x < x.w3.topright.x){
                System.out.println("yeast");
                if(xvel > 0) pos.x += delta * xvel;
            }else{
                pos.x += delta * xvel;
            }

            if(hitbox.botleft.y > x.w3.topright.y && newh.botleft.y <= x.w3.topright.y){
                if(yvel > 0) pos.y += delta * yvel;
                grounded = true;
            }else if(hitbox.topright.y <= x.w3.botleft.y && newh.topright.y > x.w3.botleft.y){
                System.out.println("yeast");
                if(yvel < 0) pos.y += delta * yvel;
            }else{
                pos.y += delta * yvel;
            }
            if(hitbox.topright.y < x.w3.botleft.y && newh.topright.y >= x.w3.botleft.y) grounded = true;
        }
        
        if(gravity) yvel -= grav * delta;
        if(grounded) yvel = -0.01f;
        recreate();
    }

    public void recreate(){
        this.hitbox.center = pos;
        this.hitbox.recreate();
    }
    
    @Override
    public SpriteData getSprite(){
        SpriteData data = new SpriteData(sprite, pos, 0);
 
        return data;
    }
    
    public void onCollision(WorldObject other){
        
    }
    
    public float distanceToPlayer(){
        return pos.distanceTo(Simon.player.pos);
    }
}
