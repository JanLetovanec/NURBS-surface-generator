package jl2119.nurbs;

import jl2119.Vector.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Renderer {

    private BufferedImage img;
    private String filePath;
    private Vector camera;

    private double scaleFactor; // Gives how many pixels is unit vector

    public Renderer(final long width, long height, String filePath, Vector cameraPose) {
        camera = cameraPose;
        img = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_3BYTE_BGR);
        this.filePath = filePath;

        scaleFactor = width / 10.0;
    }

    public void RenderPoint(Vector point) {
        point = point.Sub(camera);
        Vector forward = new Vector(0,0,1);
        Vector component = point.GetComponentVector(forward);
        double scale = 5 / component.Length();
        //double scale = 1 / component.Length();
        Vector screenVector = point.ScaleByScalar(scale).Flatten(forward);

        Vector topLeft = new Vector(-5, 5, 0.0); //TODO: refactor box bounds
        Vector botRight = new Vector(5, -5, 0.0);
        // If vector is inside screen, render it
        if(isVectorWithinBox(screenVector, topLeft, botRight)) {
            screenVector = screenVector.Sub(topLeft);               //Top left coords
            screenVector = screenVector.ScaleByScalar(scaleFactor); //Scale to appropriate

            long x = (long) screenVector.getX();
            long y = - (long) screenVector.getY();  // Minus becaus screen positive y is towards the top of screen
            SetPixelColour(x, y);
        }
    }

    public void GenerateImage() throws IOException {
        File target = new File(filePath);
        ImageIO.write(img, "png", target);
    }

    private void SetPixelColour(long x, long y) {
        img.setRGB((int) x, (int) y, Color.white.getRGB());
    }

    private boolean isVectorWithinBox(Vector testedVector, Vector topLeft, Vector botRight) {
        return testedVector.getX() > topLeft.getX()
                && testedVector.getX() < botRight.getX()
                && testedVector.getY() < topLeft.getY()
                && testedVector.getY() > botRight.getY();
    }
}
