// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.splash;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import org.terasology.splash.overlay.Overlay;

final class SwingSplashScreen extends AbstractSplashScreen {

    private final Window window;

    /**
     * Uses the default image URL
     * @throws IOException if the image is not found or cannot be loaded
     */
    SwingSplashScreen() throws IOException {
        this(getDefaultSplashImageUrl());
    }

    SwingSplashScreen(URL splashImageUrl) throws IOException {
        this(readImage(splashImageUrl));
    }

    SwingSplashScreen(int width, int height) {
        this(null, width, height);
    }

    SwingSplashScreen(BufferedImage image) {
        this(image, image.getWidth(), image.getHeight());
    }

    SwingSplashScreen(BufferedImage image, int width, int height) {
        window = new JWindow((Window) null);
        window.setBackground(new Color(0, 0, 0, 0));
        window.setSize(width, height);
        window.setLocationRelativeTo(null);

        // alwaysOnTop keeps the LWJGL2 Display window from popping up and it can't be triggered manually
//        window.setAlwaysOnTop(true);

        window.add(new Component() {

            private static final long serialVersionUID = 1717818903226627606L;

            @Override
            public void paint(Graphics g) {
                if (image != null) {
                    g.drawImage(image, 0, 0, width, height, null);
                }
                for (Overlay overlay : getOverlays()) {
                    overlay.render((Graphics2D) g);
                }
            }
        });

        window.setVisible(true);
    }

    /**
     * Searches the classpath for "/splash/splash.<all known file extensions>" in alphabetic order.
     * @return the image resource URL or <code>null</code>
     */
    public static URL getDefaultSplashImageUrl() {
        String[] readerFileSuffixes = ImageIO.getReaderFileSuffixes();
        Arrays.sort(readerFileSuffixes);
        for (String fileExt : readerFileSuffixes) {
            URL resource = SwingSplashScreen.class.getResource("/splash/splash." + fileExt);
            if (resource != null) {
                return resource;
            }
        }
        return null;
    }

    @Override
    protected boolean update(double dt) {
        if (!super.update(dt)) {
            return false;
        }

        window.repaint();

        return true;
    }

    @Override
    public void close() {
        super.close();
        SwingUtilities.invokeLater(() -> window.dispose());
    }

    private static BufferedImage readImage(URL splashImageUrl) throws IOException {
        BufferedImage img = ImageIO.read(splashImageUrl);
        if (img == null) {
            throw new IOException("Unable to load image " + splashImageUrl);
        }
        return img;
    }
}
