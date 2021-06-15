// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.splash.overlay;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * A static image overlay.
 */
public class ImageOverlay implements Overlay {

    private final BufferedImage img;
    private int x;
    private int y;
    private boolean visible = true;

    public ImageOverlay(URL imgResource) throws IOException {
        img = ImageIO.read(imgResource);
    }

    /**
     * Sets new coordinates for the top-left corner of the image.
     * @param nx the new x position
     * @param ny the new y position
     * @return this
     */
    public ImageOverlay setPosition(int nx, int ny) {
        this.x = nx;
        this.y = ny;
        return this;
    }

    /**
     * Sets new coordinates for the top-left corner of the image.
     * @param nx the new x position
     * @param ny the new y position
     * @return this
     */
    public ImageOverlay setCenter(int nx, int ny) {
        this.x = nx - img.getWidth() / 2;
        this.y = ny - img.getHeight() / 2;
        return this;
    }

    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, img.getWidth(), img.getHeight());
    }

    protected BufferedImage getImage() {
        return img;
    }

    @Override
    public void render(Graphics2D g) {
        if (visible) {
            g.drawImage(img, x, y, null);
        }
    }

}

