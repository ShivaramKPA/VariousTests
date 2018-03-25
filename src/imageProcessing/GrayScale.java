/*
 * Code from https://www.tutorialspoint.com/java_dip/grayscale_conversion.htm
 *  The following example demonstrates the use of Java BufferedImage class that 
   converts an image to Grayscale:
 */
package imageProcessing;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GrayScale {

   BufferedImage  image;
   int width;
   int height;
   
   public GrayScale() {
   
      try {
         File input = new File("src/imageProcessing/digital_image_processing.jpg");
         image = ImageIO.read(input);
         width = image.getWidth();
         height = image.getHeight();
          System.out.println("width, height: " + width + "," + height);
         
         for(int i=0; i<height; i++){
         
            for(int j=0; j<width; j++){
            
               Color c = new Color(image.getRGB(j, i));
               int red = (int)(c.getRed() * 0.299);
               int green = (int)(c.getGreen() * 0.587);
               int blue = (int)(c.getBlue() *0.114);
               Color newColor = new Color(red+green+blue,
               
               red+green+blue,red+green+blue);
               
               image.setRGB(j,i,newColor.getRGB());
            }
         }
         
         File ouptut = new File("src/imageProcessing/output_grayscaledImage.jpg");
         ImageIO.write(image, "jpg", ouptut);
         
      } catch (Exception e) {}
   }
   
   static public void main(String args[]) throws Exception 
   {
      GrayScale obj = new GrayScale();
   }
}