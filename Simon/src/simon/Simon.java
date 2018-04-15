/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simon;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import jfxemulator.GGOutputStream;
import jfxemulator.JFXEmulator;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
/**
 *
 * @author Javier
 */
public class Simon {
    static long time = 0;
    static boolean emulator = true;
    static ArrayList<Renderable> renderables = new ArrayList<>();
    static HashMap<String, Integer> ids = new HashMap<>();
    static World world = new World();
    static Player player;
    static GlobalKeyboardHook hook;
    static boolean[] chars = new boolean[2048];
    
    
   static char[] spritelist = new char[(16*18)+4]; 
   static clipboardData clipboardData;
static void printStack(int index,  int value) {
	int digit1 = value / 100;
	int digit2 = (value % 100) / 10;
	int digit3 = (value % 100) % 10;
	spritelist[index] = (char)('0' + digit1);
	spritelist[index+1] = (char)('0' + digit2);
	spritelist[index+2] = (char)('0' + digit3);
}
static void printStack4(int index,  int value) {
	int digit1 = value / 1000;
	int digit2 = (value % 1000) / 100;
	int digit3 = (value % 1000) % 100/10;
        int digit4 = (value % 1000) % 100%10;
	spritelist[index] = (char)('0' + digit1);
	spritelist[index+1] = (char)('0' + digit2);
	spritelist[index+2] = (char)('0' + digit3);
        spritelist[index+3] = (char)('0' + digit4);
}
static void updateSprite(int index,short x,short y, short width,short height, short scrollx, short scrolly) {
	int initialindex = (index * 18)+4;
	printStack(initialindex, x);
	printStack(initialindex+3, y);
	printStack(initialindex+6,width);
	printStack(initialindex+9, height);
	printStack(initialindex+12, scrollx);
	printStack(initialindex+15, scrolly);



}
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        new Thread(() -> {
            try {
                start();
            } catch (Exception ex) {
                Logger.getLogger(Simon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }
    
    public static void start() throws Exception{
        new Thread(() -> javafx.application.Application.launch(JFXEmulator.class, new String[0])).start();
        Thread.sleep(1000);
        while (!JFXEmulator.e.ready) {
            Thread.sleep(2);
        }
                clipboardData = new clipboardData();
        hook = new GlobalKeyboardHook(true);

        hook.addKeyListener(new GlobalKeyAdapter() {
            @Override
            public void keyPressed(GlobalKeyEvent event) {
                if (event.getVirtualKeyCode() == GlobalKeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                if(chars[event.getVirtualKeyCode()] != true){
                    chars[event.getVirtualKeyCode()] = true;
                    player.keyPress(event);
                }      
            }

            @Override
            public void keyReleased(GlobalKeyEvent event) {
                if(chars[event.getVirtualKeyCode()] != false){
                    chars[event.getVirtualKeyCode()] = false;
                    player.keyRelease(event);
                }                   
            }
        });

        EnemyFactory.create();
        PowerupFactory.create();
        
        player = new Player();
        world.objects.add(player);
        ids.put("player", 1);
        ids.put("bee", 2);
        ids.put("turt", 3);
        ids.put("beaver", 4);
        ids.put("beewing", 5);
        
        
        
        
        ids.put("meatball", 8);
        ids.put("birdo", 0);
        ids.put("", 0);
        PowerupHolder ho = new PowerupHolder();
        ho.p = PowerupFactory.getPowerup("onelife");
        ho.pos = new Point(100, 50);
        world.objects.add(ho);
        
        //world.objects.add(EnemyFactory.getEnemy("hammerbro", new Point(60,30)));
        WorldLoader coll = new WorldLoader();
        coll.load("C:\\res\\dab\\grasscollider.png");
        time = System.currentTimeMillis();
        run();
        
    }
    
    public static void run() throws Exception{
        while(true){
            update();
            updateRender();
            Thread.sleep(2);
        }
    }
    
    public static void update() throws Exception{
        float diff = System.currentTimeMillis() - time;
        time = System.currentTimeMillis();
        world.update(diff/1000f);
        
    }
    
    public static void updateRender() throws IOException{
        if(emulator){
            GGOutputStream out = new GGOutputStream();
            out.write((int)0);
            out.write((int)(player.pos.x-50));
            out.write((int)0);
            out.write(renderables.size());
            List<SpriteData> spritest = new ArrayList<>();
            printStack4(0,(int)(player.pos.x-50));
            for(Renderable r : renderables){
                SpriteData s = r.getSprite();
                spritest.add(s);
                //updateSprite(ids.getOrDefault(s.sprite, 0),(short)s.pos.x,(short)s.pos.y,(short)32,(short)32,(short)0,(short)0);
                out.write((int)s.pos.x);
                out.write((int)s.pos.y);
                out.write((int)s.rot);
                
                out.write(ids.getOrDefault(s.sprite, 0));

            }

            spritest.stream()
                    .sorted((a,b) -> ids.getOrDefault(a.sprite, 0) - ids.getOrDefault(b.sprite, 0))
                    .filter(a -> a.pos.x < player.pos.x-50+210 && a.pos.x < player.pos.x - 50 - 50)
                    .forEach(s -> updateSprite(ids.getOrDefault(s.sprite, 0), (short)s.pos.x,(short)s.pos.y,(short)32,(short)32,(short)0,(short)0));
                    
            
            out.flush();
            byte[] bytes = ((ByteArrayOutputStream)out.getStream()).toByteArray();
            JFXEmulator.e.update(ByteBuffer.wrap(bytes));
            clipboardData.setData(new String(spritelist));
        }else{
          /*  IntBuffer b = IntBuffer.allocate(2048);
            b.put((int)0);
            b.put((int)(player.pos.x-50));
            b.put((int)0);
            b.put(renderables.size());
            for(Renderable r : renderables){
                SpriteData s = r.getSprite();
                b.put((int)s.pos.x);
                b.put((int)s.pos.y);
                b.put((int)s.rot);
                b.put(ids.getOrDefault(s.sprite, 0));
            }
            NativeLink.render(b.array());*/
        }
        
    }
}
