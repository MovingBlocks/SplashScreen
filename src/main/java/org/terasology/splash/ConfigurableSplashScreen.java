// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.splash;

import org.terasology.splash.overlay.Overlay;

/**
 * This is an abstract class, because the methods should be package private only.
 */
public abstract class ConfigurableSplashScreen implements SplashScreen {

    abstract void addOverlay(Overlay overlay);

    abstract void setMaxQueueLength(int maxQueueLength);

    abstract void setMinVisTime(double minVisTime);
}
