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

import java.awt.Rectangle;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.terasology.splash.overlay.TextOverlay;

/**
 * Tests with the headless property set.
 */
public class HeadlessTest {

    @BeforeClass
    public static void setup() {
        System.setProperty("java.awt.headless", "true");
    }

    @AfterClass
    public static void cleanup() {
        System.clearProperty("java.awt.headless");
    }

    @Test
    public void testInvalidUrl() {
        new SplashScreenBuilder(this.getClass().getResource("/not-there.png")).build().close();
    }

    @Test
    public void testBrokenImage() {
        new SplashScreenBuilder(this.getClass().getResource("/splash/broken-image.png")).build().close();
    }

    @Test
    public void testDefault() {
        SplashScreen splash = new SplashScreenBuilder()
            .add(new TextOverlay(new Rectangle(0, 0, 200, 50)))
            .build();

        splash.close();
    }
}
