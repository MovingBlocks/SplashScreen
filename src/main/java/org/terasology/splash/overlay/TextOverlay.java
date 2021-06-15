// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0
package org.terasology.splash.overlay;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * An overlay that renders (shadowed) text within a given rectangle.
 */
public class TextOverlay implements Overlay {

    private final Font font = new Font("Serif", Font.BOLD, 14);
    private final String ellipsis = "..";
    private Color textColor = Color.BLACK;

    private final Rectangle rc;

    private boolean shadowVisible = true;
    private Point shadowOffset = new Point(1, 1);
    private Color shadowColor = new Color(224, 224, 224);

    private String text;

    public TextOverlay(Rectangle rc) {
        this.rc = rc;
    }

    /**
     * @param textColor the new text color to set. <code>null</code> is not allowed
     */
    public void setTextColor(Color textColor) {
        if (textColor == null) {
            throw new IllegalArgumentException();
        }

        this.textColor = textColor;
    }

    @Override
    public void setMessage(String message) {
        this.text = message;
    }

    @Override
    public void render(Graphics2D g) {

        if (text != null) {
            g.setFont(font);
//            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            FontMetrics fm = g.getFontMetrics();

            String printedText = truncateToWidth(fm, text, ellipsis, rc.width);
            int asc = g.getFontMetrics().getAscent();

            if (shadowVisible) {
                g.setColor(shadowColor);
                g.drawString(printedText, rc.x + shadowOffset.x, rc.y + shadowOffset.y + asc);
            }

            g.setColor(textColor);
            g.drawString(printedText, rc.x, rc.y + asc);
        }
    }

    private static String truncateToWidth(FontMetrics fm, String text, String ellipsis, int maxTextWidth) {
        if (text.length() <= 1) {
            return text;
        }

        int ellipsisWidth = fm.charsWidth(ellipsis.toCharArray(), 0, ellipsis.length());

        int fullWidth = fm.charsWidth(text.toCharArray(), 0, text.length());
        if (fullWidth <= maxTextWidth) {
            return text;
        }

        for (int len = 1; len <= text.length(); len++) {
            int truncWidth = fm.charsWidth(text.toCharArray(), 0, len);

            if (truncWidth + ellipsisWidth >= maxTextWidth) {
                return text.substring(0, len - ellipsis.length()) + ellipsis;
            }
        }

        return text;
    }

}
