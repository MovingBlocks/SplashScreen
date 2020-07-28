// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

package org.terasology.splash.glfw.widgets;

import org.terasology.splash.glfw.graphics.Renderer;
import org.terasology.splash.glfw.graphics.Texture;

import java.io.IOException;
import java.net.URL;

public class Image implements Widget {
    protected final Texture texture;
    protected int x;
    protected int y;

    public Image(URL texture, int x, int y) throws IOException {
        this.texture = Texture.loadTexture(texture);
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(Renderer renderer) {
        texture.bind();
        renderer.begin();
        renderer.drawTexture(texture, x, y);
        renderer.end();
    }

    public void delete() {
        texture.delete();
    }
}
