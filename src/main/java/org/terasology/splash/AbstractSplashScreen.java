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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.Timer;

import org.terasology.splash.overlay.Overlay;

/**
 * The actual implementation of {@link SplashScreen} that
 * animates through a list of messages.
 */

abstract class AbstractSplashScreen extends ConfigurableSplashScreen {

    /**
     * In milli-seconds
     */
    private final int timerFreq = 40;

    /**
     * Minimum time a message is visible (in milli-seconds)
     */
    private double minVisTime = 100;

    private int maxQueueLength = 3;

    private double lastUpdate;

    private final Timer timer;

    private final Queue<String> messageQueue = new ArrayDeque<>();
    private final Object lock = new Object();

    private final List<Overlay> overlays = new CopyOnWriteArrayList<>();

    AbstractSplashScreen() {
        if (!EventQueue.isDispatchThread()) {
            throw new IllegalStateException("must be called on AWT's event dispatch thread");
        }

        ActionListener timerAction = new ActionListener() {

            private long nanoTime;

            @Override
            public void actionPerformed(ActionEvent e) {
                long newTime = System.nanoTime();
                if (nanoTime == 0) {
                    nanoTime = newTime;
                }
                double dt = (newTime - nanoTime) * 1e-6;  // nano-seconds to milli-seconds

                if (!update(dt)) {
                    timer.stop();
                }

                nanoTime = newTime;
            }
        };

        timer = new Timer(timerFreq, timerAction);
        timer.setInitialDelay(0);

        // We can start the time now, because the timer adds an event to the EDT queue and will not run
        // until this method call returns control. The first timer update has a delta time value of 0.

        // If not run on EDT, the timer cannot be started in the constructor since the constructor of
        // the derived classes is called afterwards. Thus the timer could trigger before the constructor has run.
        timer.start();
    }

    @Override
    public void close() {
        timer.stop();
    }

    @Override
    public void post(String message) {
        if (message == null) {
            return;
        }

        synchronized (lock) {
            while (messageQueue.size() >= maxQueueLength) {
                messageQueue.remove();
            }
            messageQueue.add(message);
        }
    }

    @Override
    public void setMaxQueueLength(int maxQueueLength) {
        this.maxQueueLength = maxQueueLength;
        synchronized (lock) {
            while (messageQueue.size() > maxQueueLength) {
                messageQueue.remove();
            }
        }
    }

    @Override
    public void setMinVisTime(double minVisTime) {
        this.minVisTime = minVisTime;
    }

    public final List<Overlay> getOverlays() {
        return Collections.unmodifiableList(overlays);
    }

    @Override
    void addOverlay(Overlay overlay) {
        overlays.add(overlay);
    }

    /**
     * This method is run on the AWT event dispatch thread.
     * @return <code>true</code> if the cycle should continue. <code>false</code> to terminate.
     */
    protected boolean update(double dt) {

        lastUpdate += dt;
        // the first isEmpty() check is unsynchronized -> lock only if chances are realistic
        if (lastUpdate > minVisTime && !messageQueue.isEmpty()) {
            synchronized (lock) {
                if (!messageQueue.isEmpty()) {
                    String message = messageQueue.poll();
                    lastUpdate = 0;
                    for (Overlay overlay : overlays) {
                        overlay.setMessage(message);
                    }
                }
            }
        }

        for (Overlay overlay : overlays) {
            overlay.update(dt);
        }

        return true;
    }
}
