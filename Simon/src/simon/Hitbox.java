package simon;

public class Hitbox implements Cloneable{
    boolean test = false;
    WorldObject other;
    Point center = new Point();
    Point botleftf;
    Point botleft;
    
    Point toprightf;
    Point topright;

    public Hitbox(Point botleftf, Point toprightf, Point center, WorldObject other) {
        this(botleftf, toprightf, center, other,  true);
    }
    
    public Hitbox(Point botleftf, Point toprightf, Point center, WorldObject other, boolean test) {
        this.botleftf = new Point(botleftf);
        this.toprightf = new Point(toprightf);
        this.center = new Point(center);
        this.other = other;
        this.test = test;
        recreate();
    }
    
    public void recreate(){
        botleft = center.add(botleftf);
        topright = center.add(toprightf);
    }
    
    public boolean collide(Hitbox other){
        return !((this.botleft.x > other.topright.x || this.topright.x < other.botleft.x ||
            this.topright.y < other.botleft.y || this.botleft.y > other.topright.y)) ;
    }
    
    @Override
    public Hitbox clone(){
        return new Hitbox(botleftf, toprightf, center, other, test);
    }
}
