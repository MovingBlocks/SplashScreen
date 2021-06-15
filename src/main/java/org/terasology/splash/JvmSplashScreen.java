// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.splash;

import java.awt.Graphics2D;

import javax.swing.SwingUtilities;

import org.terasology.splash.overlay.Overlay;

/**
 * Uses the AWT splash screen.
 */

final class JvmSplashScreen extends AbstractSplashScreen {

    private final java.awt.SplashScreen splashScreen;

    /**
     * @throws UnsupportedOperationException - if the splash screen feature is not supported by the current toolkit
     * @throws java.awt.HeadlessException - if GraphicsEnvironment.isHeadless() returns true
     * @throws IllegalStateException - if the splash screen is not available or already closed
     */
    JvmSplashScreen() {
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
