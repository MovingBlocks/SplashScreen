// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.splash.glfw.widgets;

import org.terasology.splash.glfw.graphics.Renderer;

public interface Widget {
    void render(Renderer renderer);

    default void update(double dt) {
    }
}
