// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.splash;

public interface SplashScreen {

    /**
     * Adds a new message in the display queue. <code>null</code> values are silently ignored.
     * This method is <b>thread-safe</b>.
     * @param message the message to be queued
     */
    void post(String message);

    void close();
}
