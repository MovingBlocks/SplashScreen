// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.splash.glfw.widgets;

import org.terasology.splash.glfw.graphics.Renderer;

import java.io.IOException;
import java.net.URL;

public class ActivatableImage extends Image {

    private final String triggerMsg;
    private boolean active;

    public ActivatableImage(URL texture, int x, int y, String triggerMsg) throws IOException {
        super(texture, x, y);
        this.triggerMsg = triggerMsg;
        active = false;
    }

    @Override
    public void render(Renderer renderer) {
        if (active) {
            super.render(renderer);
        }
    }

    public void post(String message) {
        if (message.equalsIgnoreCase(triggerMsg)) {
            active = true;
        }
    }

}
