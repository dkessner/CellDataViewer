//
// Cell.java
//
// Darren Kessner
//


package io.github.dkessner;


public class Cell
{
    public PVector position;
    public float radius;
    public int color;

    public Cell()
    {
        position = new PVector();
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
}


