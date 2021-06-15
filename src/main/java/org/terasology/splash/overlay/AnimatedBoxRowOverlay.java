// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.splash.overlay;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class AnimatedBoxRowOverlay implements Overlay {

    private double time;
    private Rectangle boxRc;

    private int boxHeight = 18;
    private int boxWidth = 10;
    private int horzSpace = 8;
    private int dx = boxWidth + horzSpace;
    private double animSpeed = 0.5;
    private float hue = 0.6f;

    /**
     * In seconds
     */
    private double delayPerBox = 0.1;

    public AnimatedBoxRowOverlay(Rectangle boxRc) {
        this.boxRc = boxRc;
    }

    @Override
    public void update(double dt) {
        time += dt;
    }

    /**
     * @param hue the new hue value. Only the fractional part is respected.
     */
    public void setHue(float hue) {
        this.hue = hue;
    }

    /**
     * @param animSpeed the new animation speed (iterations per second)
     */
    public void setAnimSpeed(double animSpeed) {
        this.animSpeed = animSpeed;
    }

    @Override
    public void render(Graphics2D g) {

        // full width equals n * boxWidth + (n - 1) * horzSpace
        int boxCount = (boxRc.width - boxWidth) / (boxWidth + horzSpace) + 1;

        // align right
        int left = boxRc.x + boxRc.width - boxCount * dx + horzSpace;

        for (int i = 0; i < boxCount; i++) {
            float sat = (float) Math.sin((time * 0.001 - i * delayPerBox) * Math.PI * animSpeed);
            sat = sat * sat;
            float bright = 1.0f;
            int rgb = Color.HSBtoRGB(hue, sat, bright);
            Color animColor = new Color(rgb);

            int sizeDiff = (int) (Math.abs(1.0 - 2 * sat) * 1.1);
            int x = left + i * dx - sizeDiff / 2;
            int y = boxRc.y + (boxRc.height - boxHeight - sizeDiff) / 2;

            g.setColor(animColor);
            g.fillRect(x, y, boxWidth + sizeDiff, boxHeight + sizeDiff);

            g.setColor(Color.BLACK);
            g.drawRect(x, y, boxWidth + sizeDiff, boxHeight + sizeDiff);
        }
    }

}
