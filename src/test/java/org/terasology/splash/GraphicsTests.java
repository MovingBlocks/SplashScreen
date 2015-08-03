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
import java.awt.GraphicsEnvironment;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class GraphicsTests {

    @Before
    public void setup() {
        Assume.assumeFalse("Not supported in headless (no graphics) environment", GraphicsEnvironment.isHeadless());
    }

    @Test
    public void testInEdt() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(this::createInstances);
    }

    @Test
    public void testOutsideEdt() {
        Assert.assertFalse(EventQueue.isDispatchThread());
        createInstances();
    }

    private void createInstances() {
        new SplashScreenBuilder().build().close();
        new SplashScreenBuilder(this.getClass().getResource("not-there.png")).build().close();
        new SplashScreenBuilder(this.getClass().getResource("/splash/broken-image.png")).build().close();
        new SplashScreenBuilder(this.getClass().getResource("/splash/splash.png")).build().close();
        new SplashScreenBuilder(700, 200).build().close();
    }
}
