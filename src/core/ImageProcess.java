package core;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageProcess {
	
	public static Image greyScale(Image image) {
		try {
			int w = (int)image.getWidth();
			int h = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					Color originalColor = pr.getColor(i,j);
					double average = (originalColor.getRed()+originalColor.getGreen()+originalColor.getBlue())/3;
					System.out.println(average);
					Color newColor = new Color(average, average, average, originalColor.getOpacity());
					pw.setColor(i, j, newColor);
				}
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Image calcWeightedAverage(Image image, int r, int g, int b) {
		try {
			int w = (int)image.getWidth();
			int h = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					Color originalColor = pr.getColor(i, j);
					double averageR = ((originalColor.getRed()*255+r)/2)/255;
					double averageG = ((originalColor.getGreen()*255+g)/2)/255;
					double averageB = ((originalColor.getBlue()*255+b)/2)/255;
					Color newColor = new Color(averageR, averageG, averageB, originalColor.getOpacity());
					pw.setColor(i, j, newColor);
				}
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}


}
