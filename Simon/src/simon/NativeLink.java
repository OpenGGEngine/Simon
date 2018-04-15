/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simon;

/**
 *
 * @author Javier
 */
public class NativeLink {
    public static void render(byte[] data){
        //nativeRender(data);
    }
    
    public native void nativeRender(byte[] data);
}
