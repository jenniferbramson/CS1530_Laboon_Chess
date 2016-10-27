package chess;
import java.awt.image.*;

class HueFilter extends RGBImageFilter {
     private int red;
     private int green;
     private int blue;
		 boolean canFilterIndexColorModel;

     public HueFilter(int red,int green,int blue) {
								canFilterIndexColorModel = true;
                this.red = red;
                this.green = green;
                this.blue = blue;
     }

     public int filterRGB(int x, int y, int rgb) {
                int originalAlpha = (rgb & 0xFF000000) >> 24;
                int originalRed = (rgb & 0x00FF0000) >> 16;
                int originalGreen = (rgb & 0x0000FF00) >> 8;
                int originalBlue = (rgb & 0x000000FF);

                // mod colours here
                originalRed += red;
								if(originalRed > 255) originalRed = 255;
                originalGreen += green;
								if(originalGreen > 255) originalGreen = 255;
                originalBlue += blue;
								if(originalBlue > 255) originalBlue = 255;

                return (originalAlpha << 24) | (originalRed << 16) | (originalGreen << 8) | originalBlue;
     }
}