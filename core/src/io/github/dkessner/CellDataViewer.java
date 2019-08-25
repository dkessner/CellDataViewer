//
// CellDataViewer.java
//
// Darren Kessner
//


package io.github.dkessner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

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

    private Camera camera;
    private ArrayList<Cell> cells;

    @Override
    public void setup()
    {
        camera = new Camera(this);

        //initializeCells();
        //writeCellData();
        readCellData();
    }

    private void initializeCells()
    {
        cells = new ArrayList<Cell>();

        final int c1 = color(0xff226de5);
        final int c2 = color(0xff50e550);

        for (int i=0; i<400; i++)
        {
            Cell cell = new Cell();
            PVector step = PVector.random3D();

            while (intersectsExistingCell(cell))
                cell.position.add(step);

            cell.radius = random(5, 15);
            cell.color = lerpColor(c1, c2, cell.radius/15);

            cells.add(cell);
        }
    }

    private boolean intersectsExistingCell(Cell cell)
    {
        for (Cell c : cells)
            if (c.intersects(cell))
                return true;
        return false;
    }

    /*
    private void writeCellData()
    {
        ArrayList<String> cellStrings = new ArrayList<String>();

        for (Cell c : cells)
            cellStrings.add(c.toString());

        try
        {
            Path path = Paths.get("cells.txt");
            Files.write(path, cellStrings);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
    */

    private void readCellData()
    {
        try
        {
            FileHandle handle = Gdx.files.internal("cells.txt");
            BufferedReader reader = handle.reader(1024);

            cells = new ArrayList<Cell>();

            while (true)
            {
                String line = reader.readLine();
                if (line == null) break;
                cells.add(new Cell(line));
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }

    @Override
    public void draw()
    {
        if (cells == null || cells.size() == 0)
        {
            readCellData();
        }

        background(0);

        pushMatrix();

        camera.update();
        camera.transform();

        Axes.drawAxes(this);
        for (Cell c : cells)
            c.draw(this);

        popMatrix();

        fill(255);
        int x = 25, y = 25, step = 25;
        text("CellDataViewer", x, y);
        text("arrow keys: yaw/pitch", x, y+=step*2);
        text("shift + arrow keys: translate x/y", x, y+=step);
        text("asdw: translate y/z", x, y+=step);
        text("f: full screen", x, y+=step);
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
        else if (key == 'f')
        {
            fullScreen();
            perspective(); // TODO: fix
        }
        else if (key == 'F')
        {
            size(800, 600, P3D);
            perspective();
        }
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
}


