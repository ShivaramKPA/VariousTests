/*
 * Code from https://www.tutorialspoint.com/java_dip/understand_image_pixels.htm
 */
package imageProcessing;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

class Pixel {
   BufferedImage image;
   int width;
   int height;
   
   public Pixel() {
      try {
          System.out.println("kp: Hi from Pixel.");
         File input = new File("src/imageProcessing/blackandwhite.jpg");
         //File input = new File("src/imageProcessing/CLAS12.jpg");
         //File input = new File("blackandwhite.jpg");
         
         image = ImageIO.read(input);
         System.out.println("kp2: Hi from Pixel.");
         width = image.getWidth();
         height = image.getHeight();
          System.out.println("kp: width = " + width + " height = " + height);
         int count = 0;
         
         for(int i=0; i<height; i++){
         
            for(int j=0; j<width; j++){
            
               count++;
               Color c = new Color(image.getRGB(j, i));
               System.out.println("S.No: " + count + " Red: " + c.getRed() +"  Green: " + c.getGreen() + " Blue: " + c.getBlue());
            }
         }
         
      } catch (Exception e) {}
   }
   
   static public void main(String args[]) throws Exception 
   {
      Pixel obj = new Pixel();
   }
}
