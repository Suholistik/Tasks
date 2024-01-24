package dominoes;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class Utils {
    public static void drawStringDomino(Graphics gr, Font font, int d1, int d2, boolean horizontal, boolean reversal, int x, int y, int width, int height) {
        FontRenderContext frc = new FontRenderContext(null, true, true);

        // Make font size bigger

        String s1 = String.valueOf(d1);
        String s2 = String.valueOf(d2);

        if (reversal) {
            s1 = String.valueOf(d2);
            s2 = String.valueOf(d1);
        }


        Rectangle2D r1 = font.getStringBounds(s1, frc);
        Rectangle2D r2 = font.getStringBounds(s2, frc);
        int r1Width = (int) Math.round(r1.getWidth());
        int r1Height = (int) Math.round(r1.getHeight());
        int r1X = (int) Math.round(r1.getX());
        int r1Y = (int) Math.round(r1.getY());

        int r2Width = (int) Math.round(r2.getWidth());
        int r2Height = (int) Math.round(r2.getHeight());
        int r2X = (int) Math.round(r2.getX());
        int r2Y = (int) Math.round(r2.getY());

        int a1, a2, b1, b2;

        if (horizontal) {
            a1 = (width / 4) - (r1Width / 2) - r1X;
            a2 = (width * 3 / 4) - (r2Width / 2) - r2X;
            b1 = (height / 2) - (r1Height / 2) - r1Y;
            b2 = (height / 2) - (r2Height / 2) - r2Y;
        } else {
            a1 = (width / 2) - (r1Width / 2) - r1X;
            a2 = (width / 2) - (r2Width / 2) - r2X;
            b1 = (height / 4) - (r1Height / 2) - r1Y;
            b2 = (height * 3 / 4) - (r2Height / 2) - r2Y;
        }
        gr.setFont(font);
        gr.setColor(Color.WHITE);
        gr.drawString(s1, x + a1, y + b1);
        gr.drawString(s2, x + a2, y + b2);
    }
}
