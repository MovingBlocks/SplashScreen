// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.splash.overlay;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.URL;

public class BreathingImageOverlay extends ImageOverlay {

    private double speed = 1;
    private double time;
    private double minSize = 0.95;
    private double maxSize = 1.05;

    public BreathingImageOverlay(URL imgResource) throws IOException {
        super(imgResource);
    }

    public BreathingImageOverlay setAnimationSpeed(double newSpeed) {
        this.speed = newSpeed;
        return this;
    }

    /**
     * A value of 1 indicates normal size. Typical values would be 0.95 and 1.05.
     * @param newMinSize the minimum relative size of the image
     * @param newMaxSize the maximum relative size of the image
     * @return this
     */
    public BreathingImageOverlay setAnimationRange(double newMinSize, double newMaxSize) {
        this.minSize = newMinSize;
        this.maxSize = newMaxSize;
        return this;
    }

    @Override
    public void update(double dt) {
        time += dt;
    }

    @Override
    public void render(Graphics2D g) {

        Rectangle bounds = getBounds();

        double centerX = bounds.getCenterX();
        double centerY = bounds.getCenterY();

        // use a sine wave to animate (use -cos to start at -1)
        double wave = -Math.cos(time * 0.001 * Math.PI * speed);

        // adjust range from [-1..1] to [0..1]
        double normWave = wave * 0.5 + 0.5;

        // adjust range to [minSize..maxSize]
        double scale = minSize + normWave * (maxSize - minSize);

        // scale at image center
        AffineTransform xform = new AffineTransform();
        xform.translate(centerX, centerY);
        xform.scale(scale, scale);
        xform.translate(-centerX, -centerY);

        // activate bi-linear filtering
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(getImage(), xform, null);
    }
}

