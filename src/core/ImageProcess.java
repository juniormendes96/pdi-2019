package core;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageProcess {
	
	//Escala de cinza
	public static Image calcgrayScale(Image image) {
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
	
	//Média ponderada
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
	
	//Limiarização
	public static Image calcThresholding(Image image, double intensity) {
		try {
			int w = (int)image.getWidth();
			int h = (int)image.getHeight();
			
			Image grayScaleImage = calcgrayScale(image);
			
			PixelReader pr = grayScaleImage.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					Color originalColor = pr.getColor(i, j);
					if(originalColor.getRed() < (intensity/255)) {
						Color newColor = new Color(0, 0, 0, originalColor.getOpacity());
						pw.setColor(i, j, newColor);
					} else {
						Color newColor = new Color(1, 1, 1, originalColor.getOpacity());
						pw.setColor(i, j, newColor);
					}
				}	
			}
			return wi;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	//Negativa
	public static Image calcNegative(Image image) {
		try {
			int w = (int)image.getWidth();
			int h = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					Color originalColor = pr.getColor(i, j);
					Color newColor = new Color(1-originalColor.getRed(), 1-originalColor.getGreen(), 1-originalColor.getBlue(), originalColor.getOpacity());
					pw.setColor(i, j, newColor);
				}
			}
			return wi;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Image challenge1(Image image) {
		try {
			int w = (int)image.getWidth();
			int h = (int)image.getHeight();
			
			Image grayScaleImage = calcgrayScale(image);
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			PixelReader grayScalePr = grayScaleImage.getPixelReader();
			
			int x = w/4;
			
			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					
					Color originalColor = pr.getColor(i, j);
					Color grayScaleColor = grayScalePr.getColor(i, j);
					
					//Primeira parte - imagem original
					if(i < x) {
						pw.setColor(i, j, originalColor);
					
					//Segunda parte - escala de cinza
					} else if(i > x && i < x*2) {
						double average = (originalColor.getRed()+originalColor.getGreen()+originalColor.getBlue())/3;
						Color newColor = new Color(average, average, average, originalColor.getOpacity());
						pw.setColor(i, j, newColor);
					
					//Terceira parte - limiarização
					} else if(i > x*2 && i < x*3) {
						if(grayScaleColor.getRed() < (127.00/255)) {
							Color newColor = new Color(0, 0, 0, grayScaleColor.getOpacity());
							pw.setColor(i, j, newColor);
						} else {
							Color newColor = new Color(1, 1, 1, grayScaleColor.getOpacity());
							pw.setColor(i, j, newColor);
						}
					
					//Quarta parte - negativa
					} else {
						Color newColor = new Color(1-originalColor.getRed(), 1-originalColor.getGreen(), 1-originalColor.getBlue(), originalColor.getOpacity());
						pw.setColor(i, j, newColor);
					}
					
				}
			}
			return wi;
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}


}
