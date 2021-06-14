// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.splash.overlay;

import java.awt.Graphics2D;

public interface Overlay {

    /**
     * @param dt delta time since the last update call in millisecs
     */
    default void update(double dt) {
        // do nothing by default
    }

    /**
     * @param message the current message text
     */
    default void setMessage(String message) {
        // ignore
    }

    void render(Graphics2D g);
}
