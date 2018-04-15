package simon;

public class Weapon implements Renderable {
    Player user;
    String sprite;

    @Override
    public SpriteData getSprite() {
        if(user == null) return null;
        return new SpriteData(sprite, user.pos, 0);
    }
}
