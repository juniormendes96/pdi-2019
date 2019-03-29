package core;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class NoiseReduction {
	
	public static ArrayList<Double> neighborsR = new ArrayList<Double>();
	public static ArrayList<Double> neighborsG = new ArrayList<Double>();
	public static ArrayList<Double> neighborsB = new ArrayList<Double>();
	
	public static ArrayList<Double> medianChannels = new ArrayList<Double>();

	
	public static ArrayList<Double> reductionX(Image image, double positionX, double positionY) {
		
		medianChannels.clear();
		clearLists();
		
		try {
			int width = (int)image.getWidth();
			int height = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			
			// largura X
			for(int i=0; i < width; i++) {
				
				// altura Y
				for(int j=0; j < height; j++) {

					// checa se está no pixel informado
					if(i == positionX && j == positionY) {
																
						// percorre todos os vizinhos
						for(int z = 0; z < 9; z++) {
							Color neighborColor;
							switch(z) {
								case 0:
									neighborColor = pr.getColor(i-1, j+1);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
								case 2:
									neighborColor = pr.getColor(i+1, j+1);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
								case 4:
									neighborColor = pr.getColor(i, j);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
								case 6:
									neighborColor = pr.getColor(i-1, j-1);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
								case 8:
									neighborColor = pr.getColor(i+1, j-1);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
							}
						}		
						
						// obrigatório antes de calcular mediana
						sortLists();
						
						medianChannels.add(median(neighborsR));
						medianChannels.add(median(neighborsG));
						medianChannels.add(median(neighborsB));
					}
				}
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// retorna a mediana dos pixels na posição
		return medianChannels;
	}
	
	// redução 3x3	
	public static ArrayList<Double> reduction3x3(Image image, double positionX, double positionY) {
		
		medianChannels.clear();
		clearLists();
		
		try {
			int width = (int)image.getWidth();
			int height = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			
			// largura X
			for(int i=0; i < width; i++) {
				
				// altura Y
				for(int j=0; j < height; j++) {

					// checa se está no pixel informado
					if(i == positionX && j == positionY) {
																
						// percorre todos os vizinhos
						for(int z = 0; z < 9; z++) {
							Color neighborColor;
							switch(z) {
								case 0:
									neighborColor = pr.getColor(i-1, j+1);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
								case 1:
									neighborColor = pr.getColor(i, j-1);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
								case 2:
									neighborColor = pr.getColor(i+1, j+1);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
								case 3:
									neighborColor = pr.getColor(i-1, j);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
								case 4:
									neighborColor = pr.getColor(i, j);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
								case 5:
									neighborColor = pr.getColor(i+1, j);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
								case 6:
									neighborColor = pr.getColor(i-1, j-1);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
								case 7:
									neighborColor = pr.getColor(i, j-1);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
								case 8:
									neighborColor = pr.getColor(i+1, j-1);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
							}
						}		
						
						// obrigatório antes de calcular mediana
						sortLists();
						
						medianChannels.add(median(neighborsR));
						medianChannels.add(median(neighborsG));
						medianChannels.add(median(neighborsB));
					}
				}
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// retorna a mediana dos pixels na posição
		return medianChannels;
	}
	
	// redução em cruz	
	public static ArrayList<Double> crossReduction(Image image, double positionX, double positionY) {
		
		medianChannels.clear();
		clearLists();
		
		try {
			int width = (int)image.getWidth();
			int height = (int)image.getHeight();
			
			PixelReader pr = image.getPixelReader();
			
			// largura X
			for(int i = 0; i < width; i++) {
				
				// altura Y
				for(int j = 0; j < height; j++) {

					// checa se está no pixel informado
					if(i == positionX && j == positionY) {
																
						// percorre todos os vizinhos
						for(int z = 0; z < 9; z++) {
							Color neighborColor;
							switch(z) {
								case 1:
									neighborColor = pr.getColor(i, j-1);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
								case 3:
									neighborColor = pr.getColor(i-1, j);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
								case 4:
									neighborColor = pr.getColor(i, j);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
								case 5:
									neighborColor = pr.getColor(i+1, j);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
								case 7:
									neighborColor = pr.getColor(i, j-1);
									neighborsR.add(neighborColor.getRed());
									neighborsG.add(neighborColor.getGreen());
									neighborsB.add(neighborColor.getBlue());
									break;
							}
						}		
						
						// obrigatório antes de calcular mediana
						sortLists();
						
						medianChannels.add(median(neighborsR));
						medianChannels.add(median(neighborsG));
						medianChannels.add(median(neighborsB));
					}
				}
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// retorna a mediana dos pixels na posição
		return medianChannels;
	}
	
	// calcula mediana de uma lista a ser informada
	public static Double median(ArrayList<Double> list) {		

		// resto da divisão
		int leftOver = list.size() % 2;
		
		// tem número ao centro
        if(leftOver > 0) {
            return list.get(Math.round(list.size() / 2));
        } else {
        	// caso não exista número ao centro
            int bigger = (list.size() / 2) -1;
            int smaller = (list.size() / 2);

            return (list.get(smaller) + list.get(bigger)) / 2;
        }
		
	}
	
	public static void sortLists() {	
		Collections.sort(neighborsR);
		Collections.sort(neighborsG);
		Collections.sort(neighborsB);
	}
	
	public static void clearLists() {
		neighborsR.clear();
		neighborsG.clear();
		neighborsB.clear();
	}

}
