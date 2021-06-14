// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.splash.overlay;

import java.io.IOException;
import java.net.URL;

/**
 * A static image overlay that can be triggered by a certain message.
 */
public class TriggerImageOverlay extends ImageOverlay {

    private String trigger;

    public TriggerImageOverlay(URL imgResource) throws IOException {
        super(imgResource);
    }

    public TriggerImageOverlay setTrigger(String newTrigger) {
        this.setVisible(newTrigger == null);
        this.trigger = newTrigger;
        return this;
    }

    @Override
    public void setMessage(String message) {
        if (trigger != null && trigger.equals(message)) {
            setVisible(true);
        }
    }
}

