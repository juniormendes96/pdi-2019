package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import enums.NeighborEnum;
import enums.PixelEnum;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import model.Pixel;
import utils.AlertMessage;
import utils.ColorUtils;

public class ImageProcess {	
	
	// Desafio Segmenta��o
	public static Image segmentate(Image image, Color color1, Color color2, Color color3) {
		try {
			int w = (int)image.getWidth();
			int h = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(w, h);
			PixelWriter pw = wi.getPixelWriter();

			Color originalColor;
			
			double differenceColor1;
			double differenceColor2;
			double differenceColor3;
			
			double minValue;
				
			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					originalColor = pr.getColor(i, j);
					
					differenceColor1 = ColorUtils.getColorDifference(originalColor, color1);
					differenceColor2 = ColorUtils.getColorDifference(originalColor, color2);
					differenceColor3 = ColorUtils.getColorDifference(originalColor, color3);
					
					minValue = Math.min(differenceColor1, Math.min(differenceColor2, differenceColor3));
					
					if(minValue == differenceColor1) {
						pw.setColor(i, j, color1);
					} else if(minValue == differenceColor2) {
						pw.setColor(i, j, color2);
					} else if(minValue == differenceColor3) {
						pw.setColor(i, j, color3);
					}
				}
			}	
			return wi;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	// Simulado 1 quest�o 1
	public static Image simulatedTestQuestion1(Image image, int pixelDistance, Color pixelColor) {
		try {
			int w = (int) image.getWidth();
			int h = (int) image.getHeight();
			
			WritableImage wi = new WritableImage(w, h);
			PixelReader pr = image.getPixelReader();
			PixelWriter pw = wi.getPixelWriter();
			
			Color originalColor;
			
			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					originalColor = pr.getColor(i, j);
					if(i % pixelDistance == 0 && i != 0) {
						pw.setColor(i, j, pixelColor);
					} else {
						pw.setColor(i, j, originalColor);
					}
				}
			}
			return wi;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Simulado 1 quest�o 2
	public static Image simulatedTestQuestion2(Image image) {
		try {
			int w = (int) image.getWidth();
			int h = (int) image.getHeight();
			
			WritableImage wi = new WritableImage(w, h);
			PixelReader pr = image.getPixelReader();
			PixelWriter pw = wi.getPixelWriter();
			
			Color color;
			
			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					if(j <= h/2) {
						color= pr.getColor(i, j);
						pw.setColor(i, j, color);
					} else {
						color = pr.getColor(w-i-1, h-(j-h/2)-1);
						pw.setColor(i, j, color);
					}
				}
			}
						
			return wi;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Simulado 1 quest�o 3
	public static void simulatedTestQuestion3(Image image, int initialX, int finalX, int initialY, int finalY) {
		try {

			PixelReader pr = image.getPixelReader();
			ArrayList<Color> selectedColors = new ArrayList<Color>();
			Color color;
			
			for(int i=initialX; i<finalX; i++) {
				for(int j=initialY; j<finalY; j++) {
					color = pr.getColor(i, j);
					if(i == initialX && j == initialY) {
						selectedColors.add(color);
					} else {
						if(!selectedColors.contains(color)) {
							selectedColors.add(color);
						}
					}
				}
			}
			String txt = "";
			for(Color c : selectedColors) {
				txt += String.format("\nR: %d G: %d B: %d", (int)(c.getRed()*255), (int)(c.getGreen()*255), (int)(c.getBlue()*255));
			}
			
			AlertMessage.showMsg("Info", "Cores selecionadas", txt, AlertType.INFORMATION);
			
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static Image rotateRight(Image image) {
		try {
			int w = (int) image.getWidth();
			int h = (int) image.getHeight();
			
			WritableImage wi = new WritableImage(h, w);
			PixelReader pr = image.getPixelReader();
			PixelWriter pw = wi.getPixelWriter();
			Color color;
			
			for(int i=0; i<w; i++) {
				for(int j=h-1; j>=0; j--) {
					color = pr.getColor(i, j);
					pw.setColor((h-1)-j, i, color);
				}
			}
			return wi;	
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Image rotateLeft(Image image) {
		try {
			int w = (int) image.getWidth();
			int h = (int) image.getHeight();
			
			WritableImage wi = new WritableImage(h, w);
			PixelReader pr = image.getPixelReader();
			PixelWriter pw = wi.getPixelWriter();
			Color color;
			
			for(int i=w-1; i>=0; i--) {
				for(int j=0; j<h; j++) {
					color = pr.getColor(i, j);
					pw.setColor(j, (w-1)-i, color);
				}
			}
			return wi;	
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Image equalizeHistogram(Image image) {
		int w = (int) image.getWidth();
		int h = (int) image.getHeight();
		WritableImage wi = new WritableImage(w, h);
		PixelReader pr = image.getPixelReader();
		PixelWriter pw = wi.getPixelWriter();

		double n = w * h;

		int[] histR = histogram(image, 'r');
		int[] histG = histogram(image, 'g');
		int[] histB = histogram(image, 'b');
		int[] histAcR = accumulatedHistogram(histR);
		int[] histAcG = accumulatedHistogram(histG);
		int[] histAcB = accumulatedHistogram(histB);

		double r, g, b;

		for (int i=0; i<w; i++) {
		    for (int j=0; j<h; j++) {
			Color color = pr.getColor(i, j);
			r = ((254.0 / n) * histAcR[(int) (color.getRed() * 255)])/255;
			g = ((254.0 / n) * histAcG[(int) (color.getGreen() * 255)])/255;
			b = ((254.0 / n) * histAcB[(int) (color.getBlue() * 255)])/255;
			Color newColor = new Color(r, g, b, color.getOpacity());
			pw.setColor(i, j, newColor);
		    }
		}
		return wi;
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public static void getGraph(Image image, BarChart<String, Number> graph) {
		int[] hist = histogram(image, ' ');
		XYChart.Series vlr = new XYChart.Series();
		for(int i=0; i<hist.length; i++) {
			vlr.getData().add(new XYChart.Data(i+"", hist[i]));
		}
		graph.getData().addAll(vlr);
	}
	
	// Histograma acumulado
	public static int[] accumulatedHistogram(int[] histogram) {
		int[] accumulatedHistogram = new int[histogram.length];
		int sum = histogram[0];
		for(int i=0; i<histogram.length-1; i++) {
			accumulatedHistogram[i] = sum;
			sum += histogram[i+1];
		}
		return accumulatedHistogram;
	}
	
	// Histograma
	public static int[] histogram(Image image, char channel) {
		
		int[] qt = new int[256];
		
		int w = (int)image.getWidth();
		int h = (int)image.getHeight();
		
		PixelReader pr = image.getPixelReader();
		
		for(int i=0; i<w; i++) {
			for(int j=0; j<h; j++) {
				Color color = pr.getColor(i, j);
				switch(channel) {
					case 'r':
					case 'R':
						qt[(int)(color.getRed()*255)]++;	
						break;
					case 'g':
					case 'G':
						qt[(int)(color.getGreen()*255)]++;
						break;
					case 'b':
					case 'B':
						qt[(int)(color.getBlue()*255)]++;
						break;
					default:
						qt[(int)(color.getRed()*255)]++;
						qt[(int)(color.getGreen()*255)]++;
						qt[(int)(color.getBlue()*255)]++;
				}	
			}
		}
		return qt;
	}
	
	// Demarcação da imagem
	public static Image demarcate(Image image, int initialX, int finalX, int initialY, int finalY) {
		try {
			
			// <Tratamento caso o clique seja de baixo pra cima>
			
			int aux;
			
			if(initialX > finalX) {
				aux = initialX;
				initialX = finalX;
				finalX = aux;
			}
			
			if(initialY > finalY) {
				aux = initialY;
				initialY = finalY;
				finalY = aux;
			}
			
			// </Tratamento caso o clique seja de baixo pra cima>
			
			int w = (int)image.getWidth();
			int h = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			// coluna
			for(int i=0; i<w; i++) {
				// linha
				for(int j=0; j<h; j++) {
					Color originalColor = pr.getColor(i, j);
					Color redColor = new Color(1, 0, 0, originalColor.getOpacity());
					if(i == initialX || i == finalX) {
						if(j >= initialY && j <= finalY) {
							pw.setColor(i, j, redColor);
						} else {
							pw.setColor(i, j, originalColor);
						}
					} else if(j == initialY || j == finalY) {
						if(i >= initialX && i <= finalX) {
							pw.setColor(i, j, redColor);
						} else {
							pw.setColor(i, j, originalColor);
						}
					} else {
						pw.setColor(i, j, originalColor);
					}	
				}
			}
			return wi;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	
	// Adição
	public static Image calcAddition(Image img1, Image img2, double percentImg1, double percentImg2) {
		try {
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
			
			double r, g, b;
			
			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					Color originalColorImg1 = prImg1.getColor(i, j);
					Color originalColorImg2 = prImg2.getColor(i, j);
					r = (originalColorImg1.getRed() * percentImg1) + (originalColorImg2.getRed() * percentImg2);
					g = (originalColorImg1.getGreen() * percentImg1) + (originalColorImg2.getGreen() * percentImg2);
					b = (originalColorImg1.getBlue() * percentImg1) + (originalColorImg2.getBlue() * percentImg2);
					r = r > 1 ? 1 : r;
					g = g > 1 ? 1 : g;
					b = b > 1 ? 1 : b;
					Color newColor = new Color(r, g, b, 1);
					pw.setColor(i, j, newColor);
				}
			}
			return wi;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Subtração
	public static Image calcSubtraction(Image img1, Image img2) {
		try {
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
			
			double r, g, b;
			
			for(int i=0; i<w; i++) {
				for(int j=0; j<h; j++) {
					Color originalColorImg1 = prImg1.getColor(i, j);
					Color originalColorImg2 = prImg2.getColor(i, j);
					r = originalColorImg1.getRed() - originalColorImg2.getRed();
					g = originalColorImg1.getGreen() - originalColorImg2.getGreen();
					b = originalColorImg1.getBlue() - originalColorImg2.getBlue();
					r = r < 0 ? 0 : r;
					g = g < 0 ? 0 : g;
					b = b < 0 ? 0 : b;
					Color newColor = new Color(r, g, b, 1);
					pw.setColor(i, j, newColor);
				}
			}
			return wi;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Image reduceNoise(Image image, NeighborEnum neighbor) {
		int w = (int) image.getWidth();
		int h = (int) image.getHeight();
		
		PixelReader pr = image.getPixelReader();
		WritableImage wi = new WritableImage(w, h);
		PixelWriter pw = wi.getPixelWriter();
		
		double medianR, medianG, medianB;
		
		for (int i=1; i<(w-1); i++) {
			for (int j=1; j<(h-1); j++) {

				Color prevColor = pr.getColor(i, j);
				Pixel p = new Pixel(prevColor.getRed(), prevColor.getGreen(), prevColor.getBlue(), i, j);

				if (neighbor.equals(NeighborEnum.NEIGHBOR_CROSS)) {
					List<Pixel> list = createNeighborC(image, p, i, j);

					medianR = median(list, PixelEnum.RED);
					medianG = median(list, PixelEnum.GREEN);
					medianB = median(list, PixelEnum.BLUE);

					pw.setColor(i, j, new Color(medianR, medianG, medianB, prevColor.getOpacity()));
				}

				if (neighbor.equals(NeighborEnum.NEIGHBOR_X)) {
					List<Pixel> list = createNeighborX(image, p, i, j);

					medianR = median(list, PixelEnum.RED);
					medianG = median(list, PixelEnum.GREEN);
					medianB = median(list, PixelEnum.BLUE);

					pw.setColor(i, j, new Color(medianR, medianG, medianB, prevColor.getOpacity()));
				}

				if (neighbor.equals(NeighborEnum.NEIGHBOR_3X3)) {
					List<Pixel> list = new ArrayList<>();
					list.addAll(createNeighborC(image, p, i, j));
					list.addAll(createNeighborX(image, p, i, j));

					medianR = median(list, PixelEnum.RED);
					medianG = median(list, PixelEnum.GREEN);
					medianB = median(list, PixelEnum.BLUE);

					pw.setColor(i, j, new Color(medianR, medianG, medianB, prevColor.getOpacity()));
				}

			}
		}

		return wi;
	}
	
	public static List<Pixel> createNeighborX(Image image, Pixel p, int x, int y) {
		List<Pixel> neighbors = new ArrayList<>();
		PixelReader pr = image.getPixelReader();

		Color color1 = pr.getColor(x - 1, y + 1);
		Color color2 = pr.getColor(x + 1, y - 1);
		Color color3 = pr.getColor(x - 1, y - 1);
		Color color4 = pr.getColor(x + 1, y + 1);

		neighbors.add(new Pixel(color1.getRed(), color1.getGreen(), color1.getBlue(), x - 1, y + 1));
		neighbors.add(new Pixel(color2.getRed(), color2.getGreen(), color2.getBlue(), x + 1, y - 1));
		neighbors.add(new Pixel(color3.getRed(), color3.getGreen(), color3.getBlue(), x - 1, y - 1));
		neighbors.add(new Pixel(color4.getRed(), color4.getGreen(), color4.getBlue(), x + 1, y + 1));
		neighbors.add(p);

		return neighbors;
	}

	public static List<Pixel> createNeighborC(Image image, Pixel p, int x, int y) {
		List<Pixel> neighbors = new ArrayList<>();
		PixelReader pr = image.getPixelReader();

		Color color1 = pr.getColor(x, y - 1);
		Color color2 = pr.getColor(x, y + 1);
		Color color3 = pr.getColor(x - 1, y);
		Color color4 = pr.getColor(x + 1, y);

		neighbors.add(new Pixel(color1.getRed(), color1.getGreen(), color1.getBlue(), x, y - 1));
		neighbors.add(new Pixel(color2.getRed(), color2.getGreen(), color2.getBlue(), x, y + 1));
		neighbors.add(new Pixel(color3.getRed(), color3.getGreen(), color3.getBlue(), x - 1, y));
		neighbors.add(new Pixel(color4.getRed(), color4.getGreen(), color4.getBlue(), x + 1, y));
		neighbors.add(p);

		return neighbors;
	}

	private static Double median(List<Pixel> pixelsList, PixelEnum pixelType) {
		List<Double> list = new ArrayList<Double>();

		if (PixelEnum.RED.equals(pixelType)) {
			for(Pixel pixel : pixelsList) {
				list.add(pixel.getRed());
			}
		} else if (PixelEnum.GREEN.equals(pixelType)) {
			for(Pixel pixel : pixelsList) {
				list.add(pixel.getGreen());
			}
		} else if (PixelEnum.BLUE.equals(pixelType)) {
			for(Pixel pixel : pixelsList) {
				list.add(pixel.getBlue());
			}
		}

		Collections.sort(list);
		Integer size = list.size();
		Double index = size.doubleValue() / 2;

		return list.get(index.intValue());
	}
	
	//Escala de cinza
	public static Image calcGrayScale(Image image, int initialX, int finalX, int initialY, int finalY) {
		try {
			
			int w = (int)image.getWidth();
			int h = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			if(isDemarcated(initialX, finalX, initialY, finalY)) {
				for(int i= initialX; i<finalX; i++) {
					for(int j= initialY; j<finalY; j++) {
						Color originalColor = pr.getColor(i,j);
						double average = (originalColor.getRed()+originalColor.getGreen()+originalColor.getBlue())/3;
						Color newColor = new Color(average, average, average, originalColor.getOpacity());
						pw.setColor(i, j, newColor);
					}
				}
			} else {
				for (int i = 0; i < w; i++) {
					for (int j = 0; j < h; j++) {
						Color originalColor = pr.getColor(i,j);
						double average = (originalColor.getRed()+originalColor.getGreen()+originalColor.getBlue())/3;
						Color newColor = new Color(average, average, average, originalColor.getOpacity());
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
	
	// Média ponderada
	public static Image calcWeightedAverage(Image image, int r, int g, int b, int initialX, int finalX, int initialY, int finalY) {
		try {
			int w = (int)image.getWidth();
			int h = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			if(isDemarcated(initialX, finalX, initialY, finalY)) {
				for(int i=initialX; i<finalX; i++) {
					for(int j=initialY; j<finalY; j++) {
						Color originalColor = pr.getColor(i, j);
						double average = (originalColor.getRed() * r
										+ originalColor.getGreen() * g
										+ originalColor.getBlue() * b)/100;
						Color newColor = new Color(average, average, average, originalColor.getOpacity());
						pw.setColor(i, j, newColor);
					}
				}
			} else {
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
	public static Image calcThresholding(Image image, double intensity, int initialX, int finalX, int initialY, int finalY) {
		try {
			int w = (int)image.getWidth();
			int h = (int)image.getHeight();
			
			Image grayScaleImage = calcGrayScale(image, 0, 0, 0, 0);
			
			PixelReader pr = grayScaleImage.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			if(isDemarcated(initialX, finalX, initialY, finalY)) {
				for(int i=initialX; i<finalX; i++) {
					for(int j=initialY; j<finalY; j++) {
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
			} else {
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
	public static Image calcNegative(Image image, int initialX, int finalX, int initialY, int finalY) {
		try {
			int w = (int)image.getWidth();
			int h = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			WritableImage wi = new WritableImage(w,h);
			PixelWriter pw = wi.getPixelWriter();
			
			if(isDemarcated(initialX, finalX, initialY, finalY)) {
				for(int i=initialX; i<finalX; i++) {
					for(int j=initialY; j<finalY; j++) {
						Color originalColor = pr.getColor(i, j);
						Color newColor = new Color(1-originalColor.getRed(), 1-originalColor.getGreen(), 1-originalColor.getBlue(), originalColor.getOpacity());
						pw.setColor(i, j, newColor);
					}
				}
			} else {
				for(int i=0; i<w; i++) {
					for(int j=0; j<h; j++) {
						Color originalColor = pr.getColor(i, j);
						Color newColor = new Color(1-originalColor.getRed(), 1-originalColor.getGreen(), 1-originalColor.getBlue(), originalColor.getOpacity());
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
	
	public static Image challenge1(Image image) {
		try {
			int w = (int)image.getWidth();
			int h = (int)image.getHeight();
			
			Image grayScaleImage = calcGrayScale(image, 0, 0, 0, 0);
			
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
	
	public static boolean isDemarcated(int initialX, int finalX, int initialY, int finalY) {
		if(initialX != 0 && finalX != 0 && initialY != 0 && finalY != 0) {
			return true;
		}
		return false;
	}


}
