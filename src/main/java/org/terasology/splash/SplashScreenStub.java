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

