package chess;
import java.awt.image.*;

//color filter for hueing the color of the chess pieces
class HueFilter extends RGBImageFilter {
     private int red;
     private int green;
     private int blue;

     public HueFilter(int red,int green,int blue) {
                this.red = red;
                this.green = green;
                this.blue = blue;
     }

     public int filterRGB(int x, int y, int rgb) {
                int originalAlpha = (rgb & 0xff000000) >> 24;
                int originalRed = (rgb & 0x00ff0000) >> 16;
                int originalGreen = (rgb & 0x0000ff00) >> 8;
                int originalBlue = (rgb & 0x000000ff);

                // mod colours here
                originalRed += red;
								//if we've gone over the max, we don't want it to loop back around
								if(originalRed > 255) originalRed = 255;		
                originalGreen += green;
								if(originalGreen > 255) originalGreen = 255;
                originalBlue += blue;
								if(originalBlue > 255) originalBlue = 255;

                return (originalAlpha << 24) | (originalRed << 16) | (originalGreen << 8) | originalBlue;
     }
}