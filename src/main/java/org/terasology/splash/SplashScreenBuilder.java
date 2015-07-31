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

import java.io.IOException;
import java.net.URL;

import org.terasology.splash.overlay.Overlay;

public final class SplashScreenBuilder {

    private AbstractSplashScreen splashScreen;

    public SplashScreenBuilder(URL splashImageUrl) throws IOException {
        splashScreen = new AwtSplashScreen(splashImageUrl);
    }

    public SplashScreenBuilder() {
        if (!java.awt.GraphicsEnvironment.isHeadless()) {

            if (JvmSplashScreen.isAvailable()) {
                splashScreen = new JvmSplashScreen();
            }

            if (AwtSplashScreen.getDefaultSplashImageUrl() != null) {
                try {
                    splashScreen = new AwtSplashScreen();
                } catch (IOException e) {
                    // TODO: maybe re-throw?
                    // TODO: maybe forward to slf4j or jul ?
                }
            }
        }
    }

    public SplashScreenBuilder add(Overlay overlay) {
        if (splashScreen != null) {
            splashScreen.addOverlay(overlay);
        }
        return this;
    }

    public SplashScreen build() {
        if (splashScreen != null) {
            splashScreen.startTimer();  // TODO: this is just awful
            return splashScreen;
        }

        return new SplashScreenStub();
    }
}
