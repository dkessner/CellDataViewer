//
// Axes.java
//
// Darren Kessner
//


package io.github.dkessner;


public class Axes
{
    public static void drawAxes(PGraphics p)
    {
        final float length = 200;

        p.colorMode(PConstants.RGB, 255);

        // red positive x-axis
        p.stroke(255, 0, 0);
        p.line(0, 0, 0, length, 0, 0);
        p.stroke(255);
        p.line(-length, 0, 0, 0, 0, 0);

        // green positive y-axis
        p.stroke(0, 255, 0);
        p.line(0, 0, 0, 0, length, 0);
        p.stroke(255);
        p.line(0, -length, 0, 0, 0, 0);

        // blue positive z-axis
        p.stroke(0, 0, 255);
        p.line(0, 0, 0, 0, 0, length);
        p.stroke(255);
        p.line(0, 0, -length, 0, 0, 0);

        drawTicks(p);

        p.fill(0, 255, 0);
        p.box(20);
    }

    private static void drawTicks(PGraphics p)
    {
        final int red = p.color(255, 0, 0);
        final int green = p.color(0, 255, 0);
        final int blue = p.color(0, 0, 255);
        final int white = p.color(255);

        // red boxes on x-axis

        p.stroke(0);

        for (int i=-4; i<=4; i++)
        {
            if (i==0) continue;
            p.fill(i>0 ? red : white);
            p.pushMatrix();
            p.translate(25*i, 0, 0);
            p.box(i%4==0 ? 10 : 5);
            p.popMatrix();
        }

        // green boxes on y-axis

        for (int i=-4; i<=4; i++)
        {
            if (i==0) continue;
            p.fill(i>0 ? green : white);
            p.pushMatrix();
            p.translate(0, 25*i, 0);
            p.box(i%4==0 ? 10 : 5);
            p.popMatrix();
        }

        // blue boxes on z-axis

        for (int i=-4; i<=4; i++)
        {
            if (i==0) continue;
            p.fill(i>0 ? blue : white);
            p.pushMatrix();
            p.translate(0, 0, i*25);
            p.box(i%4==0 ? 10 : 5);
            p.popMatrix();
        }
    }
}


