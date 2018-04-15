/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxemulator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author Javier
 */
public class GGInputStream extends InputStream{
    private InputStream in;
    
    public GGInputStream(InputStream bais){
        this.in = bais;
    }
    
    public GGInputStream(ByteBuffer buffer){
        if(buffer.hasArray()){
            this.in = new ByteArrayInputStream(buffer.array());
        }else{
            byte[] array = new byte[buffer.limit()];
            for(int i = 0; i < array.length; i++){
                array[i] = buffer.get();
            }
            this.in = new ByteArrayInputStream(array);
        }
    }

    public int readInt() throws IOException{
        ByteBuffer b = ByteBuffer.allocate(Integer.BYTES).put(readByteArray(Integer.BYTES));
        b.flip();
        return b.getInt();
    }
    
    public float readFloat() throws IOException{
        ByteBuffer b = ByteBuffer.allocate(Float.BYTES).put(readByteArray(Float.BYTES));
        b.flip();
        return b.getFloat();
    }
    public double readDouble() throws IOException{
        ByteBuffer b = ByteBuffer.allocate(Double.BYTES).put(readByteArray(Double.BYTES));
        b.flip();
        return b.getDouble();
    }
    
    public boolean readBoolean() throws IOException{
        int b = readInt();
        return b == 1;
    }
    
    public byte[] readByteArray(int size) throws IOException{
        byte[] b = new byte[size];
        in.read(b);
        return b;
    }
    
    public byte readByte() throws IOException{
        return (byte) read();
    }
    
    public char readChar() throws IOException{
        ByteBuffer b = ByteBuffer.allocate(Character.BYTES).put(readByteArray(Character.BYTES));
        b.flip();
        return (char)b.getShort();
    }
    
    public String readString() throws IOException{
        String s = "";
        int len = readInt();
        for(int i = 0; i < len; i++){
            s += readChar();
        }
        return s;
    }

    @Override
    public int read() throws IOException {
        return in.read();
    }
}
