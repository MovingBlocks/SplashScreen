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

import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;

import org.terasology.splash.overlay.AnimatedBoxRowOverlay;
import org.terasology.splash.overlay.BreathingImageOverlay;
import org.terasology.splash.overlay.TriggerImageOverlay;
import org.terasology.splash.overlay.RectOverlay;
import org.terasology.splash.overlay.TextOverlay;

public final class SplashScreenTest {

    /**
     * Run with JVM argument: -splash:src/test/resources/splash.jpg
     * @param args (ignored)
     * @throws InterruptedException
     * @throws IOException
     */
    public static void main(String[] args) throws InterruptedException, IOException {
//        System.setProperty("java.awt.headless", "true");

        int imageHeight = 283;
        int maxTextWidth = 450;
        int width = 600;
        int height = 30;
        int left = 20;
        int top = imageHeight - height - 20;

        Rectangle rectRc = new Rectangle(left, top, width, height);
        Rectangle textRc = new Rectangle(left + 10, top + 5, maxTextWidth, height);
        Rectangle boxRc = new Rectangle(left + maxTextWidth + 10, top, width - maxTextWidth - 20, height);

        SplashScreenBuilder builder = new SplashScreenBuilder();

        String[] imgFiles = new String[] {
                "splash_1t.png",
                "splash_2t.png",
                "splash_3t.png",
                "splash_4t.png",
                "splash_5t.png"
        };

        Point[] imgOffsets = new Point[] {
                new Point(0, 0),
                new Point(150, 0),
                new Point(300, 0),
                new Point(450, 0),
                new Point(630, 0)
        };

        for (int index = 0; index < 5; index++) {
            URL resource = SplashScreenTest.class.getResource("/splash/" + imgFiles[index]);
            builder.add(new TriggerImageOverlay(resource)
                    .setTrigger("HELLO " + (index + 2))
                    .setPosition(imgOffsets[index].x, imgOffsets[index].y));
        }

        builder.add(new BreathingImageOverlay(SplashScreenTest.class.getResource("/splash/splash_text.png"))
                    .setAnimationRange(0.97, 1.00)
                    .setAnimationSpeed(1.5));

        SplashScreen instance = builder
                .add(new RectOverlay(rectRc))
                .add(new TextOverlay(textRc))
                .add(new AnimatedBoxRowOverlay(boxRc))
                .build();

        Thread.sleep(1000);

        instance.post("HELLO");
        instance.post(null);
        instance.post("HELLO 2");

        Thread.sleep(1000);
        Thread.sleep(1000);
        instance.post("HELLO 3");
        Thread.sleep(500);
        Thread.sleep(500);
        instance.post("HELLO 4");
        instance.post("HELLO 5");
        instance.post("HELLO 6");
        instance.post("HELLO 7 7 7 77777777777777777777777777777777777777777777777777");

        Thread.sleep(10000);

        instance.close();
    }
}
