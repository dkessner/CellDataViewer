//
// CellDataViewer.java
//
// Darren Kessner
//


package io.github.dkessner;


import java.util.*;
import java.io.*;
import java.nio.file.*;


public class CellDataViewer extends PApplet
{
    @Override
    public void settings()
    {
        size(800, 600, P3D);
    }

    @Override
    public void setup()
    {
        initializePoints();

        camera = new Camera(this);

       //writeFile();
        //readFile();
    }

    /*
    public static void readFile()
    {
        try
        {
            Path path = Paths.get("output.txt");
            List<String> lines = Files.readAllLines(path);

            for (String line : lines)
            {
                System.out.println("> " + line);
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }

    public static void writeFile()
    {
        try
        {
            Path path = Paths.get("output.txt");

            ArrayList<String> lines = new ArrayList<String>();
            lines.add("Hello.");
            lines.add("My name is Inigo Montoya.");
            lines.add("You killed my father.");
            lines.add("Prepare to die.");

            Files.write(path, lines);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
    */

    private void initializePoints()
    {
        points = new ArrayList<PVector>();
        points2 = new ArrayList<PVector>();
        points3 = new ArrayList<PVector>();

        PVector current = new PVector(-100, -100, -100);
        while (current.mag() <= 200)
        {
            points.add(current.copy());
            current.add(random(5), random(5), random(5));
        }

        current.set(-100, 100, 100);
        while (current.mag() <= 200)
        {
            points2.add(current.copy());
            current.add(random(20), random(-20, 0), random(-20, 0));
        }

        current.set(100, -100, 100);
        while (current.mag() <= 200)
        {
            points3.add(current.copy());
            current.add(random(-5, 0), random(5), random(-3, -2));
        }
    }

    @Override
    public void draw()
    {
        background(0);

        camera.update();
        camera.transform();
        // draw RGB ellipses

        colorMode(RGB, 255);
        fill(255, 0, 0);
        stroke(255);
        ellipse(100, 100, 100, 50);

        colorMode(RGB, 1.0f);
        noFill();
        stroke(0, 1.0f, 0);
        ellipse(200, 200, 100, 50);

        colorMode(HSB, 360, 100, 100);
        fill(240, 100, 100); // blue HSV 
        noStroke();
        ellipse(300, 300, 100, 50);

        pushMatrix();
        Axes.drawAxes(this);
        drawPoints();
        drawPoints2();
        drawPoints3();
        popMatrix();
    }

    void drawPoints()
    {
        final int c1 = color(0xff226de5);
        final int c2 = color(0xff50e550);

        for (PVector p : points)
        {
            pushMatrix();
            translate(p.x, p.y, p.z);
            fill(lerpColor(c1, c2, (p.x+100)/200));
            box(3);
            popMatrix();
        }
    }

    void drawPoints2()
    {
        final int c1 = color(0xfff77111);
        final int c2 = color(0xffcfea20);

        noFill();
        beginShape();
        for (PVector p : points2)
        {
            stroke(lerpColor(c1, c2, (p.x+100)/200));
            vertex(p.x, p.y, p.z);
        }
        endShape();
    }


    void drawPoints3()
    {
        final int c1 = color(0xff000000);
        final int c2 = color(0xff888888);
        final int n = 20;

        for (int i=0; i<points3.size()-1; i++)
        {
            PVector p1 = points3.get(i);
            PVector p2 = points3.get(i+1);
            final float r = 10;

            fill(lerpColor(c1, c2, (p1.x+100)/200));
            stroke(200);
            beginShape();
            for (int j=0; j<=n; j++)
            {
                float t = 2*PI*j/n;
                vertex(p1.x + r*cos(t), p1.y + r*sin(t), p1.z);
                vertex(p2.x + r*cos(t), p2.y + r*sin(t), p2.z);
            }
            endShape();
        }
    }

    private boolean shift = false;

    @Override
    public void keyPressed()
    {
        if (keyCode == SHIFT)
            shift = true;
        else if (keyCode == RIGHT)
        {
            if (shift) 
                camera.moveX(Camera.speed);
            else 
                camera.moveYaw(Camera.angularSpeed);
        }
        else if (keyCode == LEFT)
        {
            if (shift)
                camera.moveX(-Camera.speed);
            else
                camera.moveYaw(-Camera.angularSpeed);
        }
        else if (keyCode == UP)
        {
            if (shift)
                camera.moveY(-Camera.speed);
            else
                camera.movePitch(Camera.angularSpeed);
        }
        else if (keyCode == DOWN)
        {
            if (shift)
                camera.moveY(Camera.speed);
            else
                camera.movePitch(-Camera.angularSpeed);
        }
        else if (key == 'w')
            camera.moveZ(-Camera.speed);
        else if (key == 's')
            camera.moveZ(Camera.speed);
        else if (key == 'a')
            camera.moveX(-Camera.speed);
        else if (key == 'd')
            camera.moveX(Camera.speed);
        else if (key == 'o')
            ortho();
        else if (key == 'p')
            perspective();
    }

    @Override
    public void keyReleased()
    {
        if (keyCode == SHIFT)
            shift = false;
        else if (keyCode == RIGHT || keyCode == LEFT)
        {
            if (shift)
                camera.moveX(0);
            else
                camera.moveYaw(0);
        }
        else if (keyCode == UP || keyCode == DOWN)
        {
            if (shift)
                camera.moveY(0);
            else
                camera.movePitch(0);
        }
        else if (key == 'w' || key == 's')
            camera.moveZ(0);
        else if (key == 'a' || key == 'd')
            camera.moveX(0);
    }

    ArrayList<PVector> points;
    ArrayList<PVector> points2;
    ArrayList<PVector> points3;

    private Camera camera;
}


