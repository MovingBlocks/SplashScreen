// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
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
            messageQueue.add(message);
        }
    }

    @Override
    public void setMaxQueueLength(int maxQueueLength) {
        this.maxQueueLength = maxQueueLength;
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
        // the isEmpty() check is unsynchronized -> lock only if chances are realistic
        // only this method removes entries, so isEmpty() cannot be true later
        if (lastUpdate > minVisTime && !messageQueue.isEmpty()) {
            lastUpdate = 0;
            synchronized (lock) {
                do {
                    String message = messageQueue.poll();
                    for (Overlay overlay : overlays) {
                        overlay.setMessage(message);
                    }
                } while (messageQueue.size() > maxQueueLength);
            }
        }

        for (Overlay overlay : overlays) {
            overlay.update(dt);
        }

        return true;
    }
}
