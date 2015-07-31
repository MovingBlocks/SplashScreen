/*
 * Copyright 2015 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.terasology.splash.overlay;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class BreathingImageOverlay implements Overlay {

    private final BufferedImage img;
    private double centerX;
    private double centerY;
    private double speed = 1;
    private double time;
    private double minSize = 0.95;
    private double maxSize = 1.05;

    public BreathingImageOverlay(URL imgResource) throws IOException {
        img = ImageIO.read(imgResource);
        centerX = img.getWidth() * 0.5;
        centerY = img.getHeight() * 0.5;
    }

    public BreathingImageOverlay setCenter(double nx, double ny) {
        this.centerX = nx;
        this.centerY = ny;
        return this;
    }

    public BreathingImageOverlay setAnimationSpeed(double newSpeed) {
        this.speed = newSpeed;
        return this;
    }

    /**
     * A value of 1 indicates normal size. Typical values would be 0.95 and 1.05.
     * @param minSize the minimum relative size of the image
     * @param maxSize the maximum relative size of the image
     * @return this
     */
    public BreathingImageOverlay setAnimationRange(double minSize, double maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        return this;
    }

    @Override
    public void update(double dt) {
        time += dt;
    }

    @Override
    public void render(Graphics2D g) {

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
        g.drawImage(img, xform, null);
    }
}

