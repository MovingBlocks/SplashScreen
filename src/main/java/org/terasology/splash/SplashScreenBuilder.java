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

import java.awt.EventQueue;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import javax.swing.SwingUtilities;

import org.terasology.splash.overlay.Overlay;

public final class SplashScreenBuilder {

    private ConfigurableSplashScreen splashScreen;

    private final URL resourceUrl;
    private final int windowWidth;
    private final int windowHeight;

    private Runnable createInstanceTask = new Runnable() {
        @Override
        public void run() {
            if (JvmSplashScreen.isAvailable()) {
                splashScreen = new JvmSplashScreen();
                return;
            }

            if (resourceUrl != null) {
                try {
                    splashScreen = new SwingSplashScreen(resourceUrl);
                } catch (IOException e) {
                    // eat silently - consider re-throwing or storing in a member field to throw later
                }
            } else if (windowWidth > 0 && windowHeight > 0) {
                splashScreen = new SwingSplashScreen(windowWidth, windowHeight);
            }
        }
    };

    public SplashScreenBuilder() {
        this(SwingSplashScreen.getDefaultSplashImageUrl(), 0, 0);
    }

    public SplashScreenBuilder(int width, int height) {
        this(null, width, height);
    }

    public SplashScreenBuilder(URL imageUrl) {
        this(imageUrl, 0, 0);
    }

    private SplashScreenBuilder(URL imageUrl, int width, int height) {
        this.resourceUrl = imageUrl;
        this.windowWidth = width;
        this.windowHeight = height;

        if (!java.awt.GraphicsEnvironment.isHeadless()) {

            if (!EventQueue.isDispatchThread()) {
                try {
                    SwingUtilities.invokeAndWait(createInstanceTask);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                createInstanceTask.run();
            }
        }

        if (splashScreen == null) {
            splashScreen = new SplashScreenStub();
        }
    }

    /**
     * @return a new stub instance that does nothing
     */
    public static SplashScreen createStub() {
        return new SplashScreenStub();
    }

    /**
     * Adds an overlay. <code>null</code> values are silently ignored.
     * @param overlay the overlay to add
     * @return this
     */
    public SplashScreenBuilder add(Overlay overlay) {
        if (overlay != null) {
            splashScreen.addOverlay(overlay);
        }
        return this;
    }

    /**
     * Sets the maximum length of the message queue. As soon as the length is reached,
     * the oldest messaged are dropped without being displayed. The default length is three.
     * @param maxQueueLength the new maximum length
     */
    public SplashScreenBuilder setMaxQueueLength(int maxQueueLength) {
        splashScreen.setMaxQueueLength(maxQueueLength);
        return this;
    }

    /**
     * Set the minimum time a message is visible. The default length is 100 milliseconds.
     * @param minVisTime the time in milli-seconds
     */
    public SplashScreenBuilder setMinVisTime(double minVisTime) {
        splashScreen.setMinVisTime(minVisTime);
        return this;
    }

    public SplashScreen build() {
        return splashScreen;
    }
}
