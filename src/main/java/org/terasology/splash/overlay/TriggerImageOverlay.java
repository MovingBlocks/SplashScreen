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

