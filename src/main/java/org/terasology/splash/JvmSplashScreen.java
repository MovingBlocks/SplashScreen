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

package org.terasology.splash;

import java.awt.Graphics2D;
import java.awt.HeadlessException;

import javax.swing.SwingUtilities;

import org.terasology.splash.overlay.Overlay;

/**
 * Uses the AWT splash screen.
 */

final class JvmSplashScreen extends AbstractSplashScreen {

    private final java.awt.SplashScreen splashScreen;

    /**
     * @throws UnsupportedOperationException - if the splash screen feature is not supported by the current toolkit
     * @throws HeadlessException - if GraphicsEnvironment.isHeadless() returns true
     * @throws IllegalStateException - if the splash screen is not available or already closed
     */
    public JvmSplashScreen() {
        splashScreen = java.awt.SplashScreen.getSplashScreen();
        if (splashScreen == null) {
            throw new IllegalStateException("splash screen is not available or already closed");
        }
    }

    /**
     * @return true if the AWT splash screen is available
     */
    public static boolean isAvailable() {
        return java.awt.SplashScreen.getSplashScreen() != null;
    }

    @Override
    public void close() {
        super.close();

        SwingUtilities.invokeLater(() -> {
            if (splashScreen.isVisible()) {
                splashScreen.close();
            }
        });
    }

    @Override
    protected boolean update(double dt) {
        if (!splashScreen.isVisible()) {
            return false;
        }

        if (!super.update(dt)) {
            return false;
        }

        Graphics2D g = splashScreen.createGraphics();
        try {
            for (Overlay overlay : getOverlays()) {
                overlay.render(g);
            }
            splashScreen.update();
        } finally {
            g.dispose();
        }
        return true;
    }
}
