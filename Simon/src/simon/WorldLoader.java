/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simon;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Javier
 */
public class WorldLoader {
    ArrayList<Hitbox> hitboxes = new ArrayList<>();
    
    ArrayList<WorldObject> obs = new ArrayList<>();
    
    public void load(String source) throws FileNotFoundException, IOException{
        BufferedImage br = ImageIO.read(new FileInputStream(source));
        Zimri[][] zims = new Zimri[br.getWidth()][br.getHeight()];
        HashMap<Integer, ZimriTuple> zimtup = new HashMap<>();
        for(int i = 0; i < zims.length; i++){
            for(int j = 0; j < zims[0].length; j++){
                zims[i][j] = new Zimri();
                Color clr = new Color(br.getRGB(i, j));
                zims[i][j].r = clr.getRed();
                zims[i][j].g = clr.getGreen();
                zims[i][j].b = clr.getBlue();
                zims[i][j].a = clr.getAlpha();
                zims[i][j].val = new Point(i,j);
            }
        }
        
        for(int i = 0; i < zims.length; i++){
            for(int j = 0; j < zims[0].length; j++){
                if(zims[i][j].a < 2) continue;
                if(zims[i][j].r != 0 && zims[i][j].r != 255){
                    int v = zims[i][j].r;
                    if(zimtup.containsKey(v)){
                        zimtup.get(v).y = zims[i][j];                       
                    }else{
                        zimtup.put(v, new ZimriTuple(zims[i][j], null));
                    }
                }else if(zims[i][j].g != 0 && zims[i][j].g != 255){
                    System.out.println("sonic heroes");
                    switch(zims[i][j].g){
                        case 10:
                            Enemy e = EnemyFactory.getEnemy("hammerbro", new Point(i, zims[0].length-j));
                            obs.add(e);
                            break;
                        case 10 + 128:
                            EnemySpawner as = new EnemySpawner(EnemyFactory.frames.get("hammerbro"), new Point(i, zims[0].length-j));
                            obs.add(as);
                            break;
                        case 50:
                            PowerupHolder holder = new PowerupHolder();
                            holder.p = PowerupFactory.getPowerup("bee");
                            holder.pos = new Point(i, zims[0].length-j);
                            holder.sprite = "beewing";
                            obs.add(holder);
                            break;
                    }
                }
            }
        }
        
        for(ZimriTuple t : zimtup.values()){
            Point p1 = t.x.val;
            p1.y = zims[0].length-p1.y;
            Point p2 = t.y.val;
            p2.y = zims[0].length-p2.y;
            hitboxes.add(new Hitbox(p1, p2, new Point(), null, true));
        }
        
        CollisionManager.world = hitboxes;
        HashMap<Float, WorldObject> finobs = new HashMap<>();
        for(WorldObject o : obs){
            if(!finobs.containsKey(o.pos.x)) finobs.put(o.pos.x, o);
            else Simon.world.remove(o);
        }
        System.out.println(finobs.values().size());
        Simon.world.add(finobs.values());
    }
    
    class Zimri{
        int r;
        int g;
        int b;
        int a;
        Point val;
    }
    
    class ZimriTuple{
        Zimri x = null, y = null;

        public ZimriTuple() {
            
        }
        
        public ZimriTuple(Zimri x, Zimri y) {
            this.x = x;
            this.y = y;
        }
    }
}
