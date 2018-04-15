/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxemulator;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;

/**
 *
 * @author warre
 */
public class Converter {

    /**
     * @param args the command line arguments
     */
    public static char FOREGROUND_RED = 0x0004;
    public static char FOREGROUND_GREEN = 0x0002;
    public static char FOREGROUND_BLUE = 0x0001;
    public static char FOREGROUND_INTENSITY= 0x0008;
    
    public static char BACKGROUND_RED = 0x0040;
    public static char BACKGROUND_GREEN = 0x0020;
    public static char BACKGROUND_BLUE = 0x0010;
    public static char BACKGROUND_INTENSITY = 0x0080;

    public static Tile[][] load(String string) throws FileNotFoundException, IOException {
        HashMap<String, Tile> bitwise = new HashMap<>();
        Color[] colors = new Color[]{
            Color.rgb(12, 12, 12), Color.rgb(0, 55, 218), Color.rgb(19, 161, 14),
            Color.rgb(58, 150, 221), Color.rgb(197, 15, 31), Color.rgb(136, 23, 152),
            Color.rgb(193, 156, 0), Color.rgb(204, 204, 204), Color.rgb(118, 118, 118),
            Color.rgb(59, 120, 255), Color.rgb(22, 198, 12), Color.rgb(97, 214, 214),
            Color.rgb(231, 72, 86), Color.rgb(180, 0, 158), Color.rgb(249, 241, 165),
            Color.rgb(242, 242, 242)

        };
        for (int i = 0; i < colors.length; i++) {
            bitwise.put(colors[i].toString(), new Tile((char) (219), colors[i], colors[i]));
        }
        
        int counter = 0;
        Color[] intermediates = new Color[(colors.length * colors.length * 2)];
        for (Color c1 : colors) {

            for (Color c2 : colors) {

                intermediates[counter] = Color.rgb(((int) ((c1.getRed() * 0.75f) + c2.getRed() * 0.25f)),
                        ((int) ((c1.getGreen() * 0.75f) + c2.getGreen() * 0.25f)),
                        ((int) ((c1.getBlue() * 0.75f) + c2.getBlue() * 0.25f)));
                bitwise.put(intermediates[counter].toString(), new Tile((char) (176), c1, c2));
                
                intermediates[counter + 1] = Color.rgb(((int) ((c1.getRed() * 0.66f) + c2.getRed() * 0.33f)),
                        ((int) ((c1.getGreen() * 0.66f) + c2.getGreen() * 0.33f)),
                        ((int) ((c1.getBlue() * 0.66f) + c2.getBlue() * 0.33f)));
                bitwise.put(intermediates[counter + 1].toString(), new Tile((char) (177), c1, c2));
                
                counter += 2;
            }
        }

        Color[] allcolors = concatenate(colors, intermediates);
        BufferedImage br = ImageIO.read(new FileInputStream(string));

        Tile[][] tiles = new Tile[br.getWidth()][br.getHeight()];
        for (int y = 0; y < br.getHeight(); y++) {
            for (int x = 0; x < br.getWidth(); x++) {
                int clr = br.getRGB(x, y);
                int red = (clr & 0x00ff0000) >> 16;
                int green = (clr & 0x0000ff00) >> 8;
                int blue = clr & 0x000000ff;
                
                final Color test = Color.rgb(red, green, blue);
                Arrays.sort(allcolors, (Color o1, Color o2) -> ColorsAreClose(test, o1) - ColorsAreClose(test, o2));
                
                Tile nonsense = bitwise.get(allcolors[0].toString());
                Tile actual = new Tile('f', test, test);
                tiles[x][y] = actual;
            }
        }
        return tiles;
    }

    public static int ColorsAreClose(Color a, Color z) {

        int r = (int) (a.getRed() * 255 - z.getRed() * 255),
                g = (int) (a.getGreen() * 255 - z.getGreen() * 255),
                b = (int) (a.getBlue() * 255 - z.getBlue() * 255);
        return (r * r + g * g + b * b);
    }

    public static <T> T[] concatenate(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
    
    public static Color bitwiseColor(int bits){
        int red = (bits & 0x00ff0000) >> 16;
        int green = (bits & 0x0000ff00) >> 8;
        int blue = bits & 0x000000ff;
        return Color.rgb(red, green, blue);
    }
}