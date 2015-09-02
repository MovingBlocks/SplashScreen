/*
 * Copyright 2015 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License"){ }
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class MultiThreadTest {

    private ExecutorService executor;

    @Before
    public void setup() {
        executor = Executors.newFixedThreadPool(4);
    }

    @After
    public void tearDown() {
        executor.shutdown();
    }

    @Test
    public void testOverflow() throws Exception {
        SplashScreen splash = new SplashScreenBuilder().build();

        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                splash.post(String.valueOf(Math.random()));
            });
        }

        Thread.sleep(1000);
    }
}
