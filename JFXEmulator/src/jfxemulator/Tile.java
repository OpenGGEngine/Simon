/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfxemulator;

import javafx.scene.paint.Color;



/**
 *
 * @author Javier
 */
public class Tile {
    
    Color color;
    Color background;
    char character;

    public Tile(char character, Color color, Color back) {
        this.character = character;
        this.color = color; 
        this.background = back;
    }
    
}
