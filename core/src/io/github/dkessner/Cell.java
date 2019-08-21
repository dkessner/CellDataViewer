//
// Cell.java
//
// Darren Kessner
//


package io.github.dkessner;


import java.util.*;


public class Cell
{
    public PVector position;
    public float radius;
    public int color;

    public Cell()
    {
        position = new PVector();
        color = 0xffffffff;
    }

    public void draw(PGraphics p)
    {
        p.pushMatrix();
        p.translate(position.x, position.y, position.z);
        p.fill(color);
        p.box(radius);
        p.popMatrix();
    }

    public boolean intersects(Cell that)
    {
        PVector d = that.position.copy();
        d.sub(this.position);
        return d.magSq() < this.radius + that.radius;
    }

    public String toString()
    {
        return position.x + " " + position.y + " " + position.z + " " + radius + " " + color;
    }

    public void fromString(String s)
    {
        String[] tokens = s.split(" ");

        if (tokens.length >= 3)
        {
            position.set(Float.parseFloat(tokens[0]), 
                         Float.parseFloat(tokens[1]), 
                         Float.parseFloat(tokens[2])); 
        }

        if (tokens.length >= 4)
            radius = Float.parseFloat(tokens[3]); 

        if (tokens.length >= 5)
            color = Integer.parseInt(tokens[4]); 
    }
}


