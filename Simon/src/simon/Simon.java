/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simon;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
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
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        new Thread(() -> javafx.application.Application.launch(JFXEmulator.class, args)).start();
        Thread.sleep(1000);
        while (!JFXEmulator.e.ready) {
            Thread.sleep(2);
        }
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
        ids.put("happy", 2);
        ids.put("", 0);
        
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
        GGOutputStream out = new GGOutputStream();
        out.write((int)0);
        out.write((int)(player.pos.x-50));
        out.write((int)0);
        out.write(renderables.size());
        for(Renderable r : renderables){
            SpriteData s = r.getSprite();
            out.write((int)s.pos.x);
            out.write((int)s.pos.y);
            out.write((int)s.rot);
            out.write(ids.getOrDefault(s.sprite, 0));
            
        }
        out.flush();
        byte[] bytes = ((ByteArrayOutputStream)out.getStream()).toByteArray();
        if(emulator){
            JFXEmulator.e.update(ByteBuffer.wrap(bytes));
        }
    }
}
