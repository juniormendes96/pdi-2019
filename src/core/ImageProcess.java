package core;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import utils.AlertMessage;

public class ImageProcess {
	
	// Adição
	public static Image calcAddition(Image img1, Image img2, double percentImg1, double percentImg2) {
		int w1 = (int)img1.getWidth();
		int h1 = (int)img1.getHeight();
		int w2 = (int)img2.getWidth();
		int h2 = (int)img2.getHeight();
		
		int w = Math.min(w1, w2);
		int h = Math.min(h1, h2);
		
		PixelReader prImg1 = img1.getPixelReader();
		PixelReader prImg2 = img2.getPixelReader();
		
		WritableImage wi = new WritableImage(w, h);
		PixelWriter pw = wi.getPixelWriter();
		
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				Color originalColorImg1 = prImg1.getColor(i, j);
				Color originalColorImg2 = prImg2.getColor(i, j);
				double r = (originalColorImg1.getRed() * percentImg1) + (originalColorImg2.getRed() * percentImg2);
				double g = (originalColorImg1.getGreen() * percentImg1) + (originalColorImg2.getGreen() * percentImg2);
				double b = (originalColorImg1.getBlue() * percentImg1) + (originalColorImg2.getBlue() * percentImg2);
				r = r > 1 ? 1 : r;
				g = g > 1 ? 1 : g;
				b = b > 1 ? 1 : b;
				Color newColor = new Color(r, g, b, 1);
				pw.setColor(i, j, newColor);
			}
		}
		return wi;
	}
	
	// Subtração
	public static Image calcSubtraction(Image img1, Image img2) {
		int w1 = (int)img1.getWidth();
		int h1 = (int)img1.getHeight();
		int w2 = (int)img2.getWidth();
		int h2 = (int)img2.getHeight();
		
		int w = Math.min(w1, w2);
		int h = Math.min(h1, h2);
		
		PixelReader prImg1 = img1.getPixelReader();
		PixelReader prImg2 = img2.getPixelReader();
		
		WritableImage wi = new WritableImage(w, h);
		PixelWriter pw = wi.getPixelWriter();
		
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				Color originalColorImg1 = prImg1.getColor(i, j);
				Color originalColorImg2 = prImg2.getColor(i, j);
				double r = originalColorImg1.getRed() - originalColorImg2.getRed();
				double g = originalColorImg1.getGreen() - originalColorImg2.getGreen();
				double b = originalColorImg1.getBlue() - originalColorImg2.getBlue();
				r = r < 0 ? 0 : r;
				g = g < 0 ? 0 : g;
				b = b < 0 ? 0 : b;
				Color newColor = new Color(r, g, b, 1);
				pw.setColor(i, j, newColor);
			}
		}
		return wi;
	}
	
	//Escala de cinza
	public static Image calcGrayScale(Image image) {
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
			if(e instanceof NullPointerException) {
				AlertMessage.showMsg("Erro", "Nenhuma imagem foi selecionada", "Por favor, selecione uma imagem", AlertType.ERROR);
				return null;
			}
			e.printStackTrace();
			return null;
		}
	}
	
	// Média ponderada
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
					double average = (originalColor.getRed() * r
									+ originalColor.getGreen() * g
									+ originalColor.getBlue() * b)/100;
					Color newColor = new Color(average, average, average, originalColor.getOpacity());
					pw.setColor(i, j, newColor);
				}
			}
			return wi;
		} catch (Exception e) {
			if(e instanceof NullPointerException) {
				AlertMessage.showMsg("Erro", "Nenhuma imagem foi selecionada", "Por favor, selecione uma imagem", AlertType.ERROR);
				return null;
			}
			e.printStackTrace();
			return null;
		}
	}
	
	// Limiarização
	public static Image calcThresholding(Image image, double intensity) {
		try {
			int w = (int)image.getWidth();
			int h = (int)image.getHeight();
			
			Image grayScaleImage = calcGrayScale(image);
			
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
		} catch (Exception e) {
			if(e instanceof NullPointerException) {
				AlertMessage.showMsg("Erro", "Nenhuma imagem foi selecionada", "Por favor, selecione uma imagem", AlertType.ERROR);
				return null;
			}
			e.printStackTrace();
			return null;
		}
		
	}
	
	// Negativa
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
		} catch (Exception e) {
			if(e instanceof NullPointerException) {
				AlertMessage.showMsg("Erro", "Nenhuma imagem foi selecionada", "Por favor, selecione uma imagem", AlertType.ERROR);
				return null;
			}
			e.printStackTrace();
			return null;
		}
	}
	
	public static Image challenge1(Image image) {
		try {
			int w = (int)image.getWidth();
			int h = (int)image.getHeight();
			
			Image grayScaleImage = calcGrayScale(image);
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			PixelReader grayScalePr = grayScaleImage.getPixelReader();
			
			// divide a imagem em 4 partes
			int x = w/4;
			
			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					
					Color originalColor = pr.getColor(i, j);
					Color grayScaleColor = grayScalePr.getColor(i, j);
					
					// Primeira parte - imagem original
					/* Supondo que w = 1000, então:
					   se i < 250
					*/
					if(i < x) {
						pw.setColor(i, j, originalColor);
					
					// Segunda parte - escala de cinza
					/* Supondo que w = 1000, então:
					   se i > 250 e i < 500
					*/
					} else if(i > x && i < x*2) {
						double average = (originalColor.getRed()+originalColor.getGreen()+originalColor.getBlue())/3;
						Color newColor = new Color(average, average, average, originalColor.getOpacity());
						pw.setColor(i, j, newColor);
					
					// Terceira parte - limiarização
					/* Supondo que w = 1000, então:
					   se i > 500 e i < 750
					*/
					} else if(i > x*2 && i < x*3) {
						if(grayScaleColor.getRed() < (127.00/255)) {
							Color newColor = new Color(0, 0, 0, grayScaleColor.getOpacity());
							pw.setColor(i, j, newColor);
						} else {
							Color newColor = new Color(1, 1, 1, grayScaleColor.getOpacity());
							pw.setColor(i, j, newColor);
						}
					
					// Quarta parte - negativa
					/* Supondo que w = 1000, então:
					   se i > 750
					*/
					} else {
						Color newColor = new Color(1-originalColor.getRed(), 1-originalColor.getGreen(), 1-originalColor.getBlue(), originalColor.getOpacity());
						pw.setColor(i, j, newColor);
					}
					
				}
			}
			return wi;
			
		} catch(Exception e) {
			if(e instanceof NullPointerException) {
				AlertMessage.showMsg("Erro", "Nenhuma imagem foi selecionada", "Por favor, selecione uma imagem", AlertType.ERROR);
				return null;
			}
			e.printStackTrace();
			return null;
		}
	}


}
