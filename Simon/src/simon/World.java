package simon;

import java.util.ArrayList;
import java.util.Collection;

public class World{
    ArrayList<WorldObject> objects = new ArrayList<>();
    ArrayList<WorldObject> remove = new ArrayList<>();
    ArrayList<WorldObject> add = new ArrayList<>();
    int currentscroll;
    
    public void remove(WorldObject object){
        remove.add(object);
        if(object instanceof Renderable) Simon.renderables.remove(object);
       
    }
    public void add(WorldObject object){
        add.add(object);
    }
    
    public void add(Collection<WorldObject> object){
        add.addAll(object);
    }
    
    public void update(float delta){
        objects.removeAll(remove);
        CollisionManager.obs.removeAll(remove);
        remove.clear();
        CollisionManager.obs.addAll(add);
        objects.addAll(add);
        add.clear();
        objects.forEach(c -> c.update(delta));
    }
}
