package pixelsEdit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String fileName = "vinnie";
		BufferedImage img = null;
		File f = null;
		List<Integer> reds = new ArrayList<Integer>();
		List<Integer> greens = new ArrayList<Integer>();
		List<Integer> blues = new ArrayList<Integer>();
		List<Integer> alphas = new ArrayList<Integer>();
		
		//read image
		try{
          f = new File(fileName + ".jpg");
		  img = ImageIO.read(f);
		}catch(IOException e){
		  System.out.println(e);
		}
		
		
		//get image width and height
		int width = img.getWidth();
		int height = img.getHeight();
		
		// Add pixels to an array
		int total_pixels = (height * width);
	    Color[] colors = new Color[total_pixels];
	    int i = 0;
	    System.out.println("Creating Colour List...");
	    for (int y = 0; y < height; y++)
	    {
	      for (int x = 0; x < width; x++)
	      {
	        colors[i] = new Color(img.getRGB(x, y));
	        i++;
	      }
	    }
	    System.out.println("Adding Colours To List...");
	    for (int i1 = 0; i1 < total_pixels; i1++)
	    {
	      Color c = colors[i1];
	      alphas.add(c.getAlpha());
	      reds.add(c.getRed());
	      greens.add(c.getGreen());
	      blues.add(c.getBlue());
	    }
	    Collections.sort(alphas);
	    System.out.print("Sorting A, ");
	    Collections.sort(reds);
	    System.out.print("R, ");
	    Collections.sort(greens);
	    System.out.print("G, ");
	    Collections.sort(blues);
	    System.out.println("B...");
	    
		int fHigh = (int) Math.round(total_pixels * 0.95);
		int fLow = (int) Math.round(total_pixels * 0.05);
		int gMax = 255;
		int gMin = 0;
		int a=0, r=0, g=0, b=0;

		int c = 0;
		System.out.println("Rendering Image. Nearly Done...");
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				// alpha
		    	a = colors[c].getAlpha();
		    	// red
		    	int sOut = (colors[c].getRed() - reds.get(fLow)) * ((gMax - gMin) / (reds.get(fHigh) - reds.get(fLow))) - gMin;
		    	if (sOut < gMin) {
		    		r = gMin;
		    	} else if (gMin <= sOut && sOut <= gMax) {
		    		r = sOut;
		    	} else if (sOut > gMax) {
		    		r = gMax;
		    	}
		    	// green
		    	sOut = (colors[c].getGreen() - greens.get(fLow)) * ((gMax - gMin) / (greens.get(fHigh) - greens.get(fLow))) - gMin;
		    	if (sOut < gMin) {
		    		g = gMin;
		    	} else if (gMin <= sOut && sOut <= gMax) {
		    		g = sOut;
		    	} else if (sOut > gMax) {
		    		g = gMax;
		    	}
		    	// blue
		    	sOut = (colors[c].getBlue() - blues.get(fLow)) * ((gMax - gMin) / (blues.get(fHigh) - blues.get(fLow))) - gMin;
		    	if (sOut < gMin) {
		    		b = gMin;
		    	} else if (gMin <= sOut && sOut <= gMax) {
		    		b = sOut;
		    	} else if (sOut > gMax) {
		    		b = gMax;
		    	}
		    	// set img pixel
		    	int p = (a<<24) | (r<<16) | (g<<8) | b;
		    	img.setRGB(x, y, p);
		    	//System.out.println(c);
		    	c++;
			}
		}

		/*
		// Negate the image
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
		  	//get pixel value
			int p = img.getRGB(x,y);
			//get alpha
			int a = (p>>24)&0xff;
			//get red
			int r = (p>>16)&0xff;
			//get green
			int g = (p>>8)&0xff;
			//get blue
			int b = p&0xff;
			p = (a<<24) | ((255-r)<<16) | ((255-g)<<8) | (255-b); // Negate the image
			img.setRGB(x, y, p);
		    }
		 }
		 */
		
		//write image
		try{
		  f = new File(fileName + "2.jpg");
		  ImageIO.write(img, "jpg", f);
		    }catch(IOException e){
		      System.out.println(e);
		    }
		System.out.println("\"" + fileName + "2.jpg\"" + " Normalised Successfully.");
	}
	
}