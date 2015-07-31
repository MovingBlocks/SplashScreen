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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * A simple overlay that draws a (filled) rectangle.
 */
public class RectOverlay implements Overlay {

    private Color fillColor = Color.WHITE;
    private Color frameColor = Color.GRAY;

    private final Rectangle rc;

    public RectOverlay(Rectangle rc) {
        this.rc = rc;
    }

    /**
     * Sets a new fill color. Use <code>null</code> to disable.
     * @param fillColor the new fill color
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * Sets a new frame color. Use <code>null</code> to disable.
     * @param frameColor the new frame color
     */
    public void setFrameColor(Color frameColor) {
        this.frameColor = frameColor;
    }

    @Override
    public void render(Graphics2D g) {
        if (fillColor != null) {
            g.setColor(fillColor);
            g.fillRect(rc.x, rc.y, rc.width, rc.height);
        }

        if (frameColor != null) {
            g.setColor(frameColor);
            g.drawRect(rc.x, rc.y, rc.width, rc.height);
        }
    }

}
