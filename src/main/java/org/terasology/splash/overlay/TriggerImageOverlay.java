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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * A static image overlay that can be triggered by a certain message.
 */
public class TriggerImageOverlay implements Overlay {

    private final BufferedImage img;
    private int x;
    private int y;
    private boolean visible = true;
    private String trigger = null;

    public TriggerImageOverlay(URL imgResource) throws IOException {
        img = ImageIO.read(imgResource);
    }

    public TriggerImageOverlay setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public TriggerImageOverlay setTrigger(String trigger) {
        this.visible = (trigger == null);
        this.trigger = trigger;
        return this;
    }

    @Override
    public void setMessage(String message) {
        if (trigger != null && trigger.equals(message)) {
            visible = true;
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (visible) {
            g.drawImage(img, x, y, null);
        }
    }
}

