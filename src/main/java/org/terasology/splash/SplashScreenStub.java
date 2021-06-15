// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.splash;

import org.terasology.splash.overlay.Overlay;

/**
 * A dummy implementation that does nothing
 */
class SplashScreenStub extends ConfigurableSplashScreen {

    @Override
    public void post(String message) {
        // do nothing
    }

    @Override
    public void close() {
        // do nothing
    }

    @Override
    void addOverlay(Overlay overlay) {
        // do nothing
    }

    @Override
    void setMaxQueueLength(int maxQueueLength) {
        // do nothing
    }

    @Override
    void setMinVisTime(double minVisTime) {
        // do nothing
    }
}

