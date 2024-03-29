package controller;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import core.ImageProcess;
import enums.NeighborEnum;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.FilterHistoryModel;
import utils.AlertMessage;

public class SampleController {
	
	@FXML private ImageView imageViewFirstTab;
	@FXML private ImageView imageViewSecondTab;
	@FXML private ImageView imageViewResult;
	
	@FXML private Label lblR;
	@FXML private Label lblG;
	@FXML private Label lblB;
	
	@FXML private Pane pnlColor;
	
	@FXML private TextField txtR;
	@FXML private TextField txtG;
	@FXML private TextField txtB;
	
	@FXML private Slider thresholdSlider;
	
	@FXML private RadioButton radioCross;
	
	@FXML private RadioButton radioX;
	@FXML private RadioButton radio3x3;
	
	@FXML private Slider percTransp;
	
	@FXML private ColorPicker colorPicker1;
	@FXML private ColorPicker colorPicker2;
	@FXML private ColorPicker colorPicker3;
	
	@FXML private Button btnChallenge2;
	
	@FXML private ColorPicker colorPickerQuestion1;
	
	@FXML private TextField pixelDistanceQuestion1;
	
	@FXML private TextField qtColumns;
	
	@FXML private Slider cannyThreshold;
	
	@FXML private TabPane tabPane;
	
	@FXML private Tab tabResult;
		
	private Image firstImage;
	private Image secondImage;
	private Image imageResult;
	private Image originalImage;
	
	private File f; 
	
	private int initialX, finalX, initialY, finalY;
	
	private List<FilterHistoryModel> filterHistoryItems = new ArrayList<>();
		
	@FXML public void laplace() {
		imageResult = ImageProcess.processLaplaceBorderDetection(firstImage);
		updateImageResult();
		tabPane.getSelectionModel().select(tabResult);
		filterHistoryItems.add(new FilterHistoryModel("Laplace"));
	}
	
	@FXML public void sobel() {
		imageResult = ImageProcess.processSobelBorderDetection(firstImage);
		updateImageResult();
		tabPane.getSelectionModel().select(tabResult);
		filterHistoryItems.add(new FilterHistoryModel("Sobel"));
	}
	
	@FXML public void canny() {
		imageResult = ImageProcess.processCannyBorderDetection(firstImage, cannyThreshold.getValue());
		updateImageResult();
		tabPane.getSelectionModel().select(tabResult);
		filterHistoryItems.add(new FilterHistoryModel("Canny", "Valor threshold: " + cannyThreshold.getValue()));
	}
	
	@FXML public void erode() {
		imageResult = ImageProcess.erode(firstImage);
		updateImageResult();
		tabPane.getSelectionModel().select(tabResult);
		filterHistoryItems.add(new FilterHistoryModel("Eros�o"));
	}
	
	@FXML public void dilate() {
		imageResult = ImageProcess.dilate(firstImage);
		updateImageResult();
		tabPane.getSelectionModel().select(tabResult);
		filterHistoryItems.add(new FilterHistoryModel("Dilata��o"));
	}
	
	// Desafio Segmenta��o
	@FXML public void challenge2() {
		imageResult = ImageProcess.segmentate(firstImage, colorPicker1.getValue(), colorPicker2.getValue(), colorPicker3.getValue());
		updateImageResult();
		tabPane.getSelectionModel().select(tabResult);
	}
	
	// Prova 1 quest�o 1
	@FXML public void test1Question1() {
		imageResult = ImageProcess.test1Question1(firstImage, Integer.parseInt(qtColumns.getText()));
		updateImageResult();
		tabPane.getSelectionModel().select(tabResult);
	}
	
	// Prova 1 quest�o 2
	@FXML public void test1Question2() {
		imageResult = ImageProcess.test1Question2(firstImage, initialX, finalX, initialY, finalY);
		updateImageResult();
		tabPane.getSelectionModel().select(tabResult);
	}
	
	// Prova 1 quest�o 3
	@FXML public void test1Question3() {
		ImageProcess.test1Question3(firstImage);
	}
	
	@FXML public void simulatedTestQuestion1() {
		imageResult = ImageProcess.simulatedTestQuestion1(firstImage, Integer.parseInt(pixelDistanceQuestion1.getText()), colorPickerQuestion1.getValue());
		updateImageResult();
		tabPane.getSelectionModel().select(tabResult);
	}
	
	@FXML public void simulatedTestQuestion2() {
		imageResult = ImageProcess.simulatedTestQuestion2(firstImage);
		updateImageResult();
		tabPane.getSelectionModel().select(tabResult);
	}
	
	@FXML public void rotateRight() {
		imageResult = ImageProcess.rotateRight(firstImage);
		updateImageResult();
		tabPane.getSelectionModel().select(tabResult);
	}
	
	@FXML public void rotateLeft() {
		imageResult = ImageProcess.rotateLeft(firstImage);
		updateImageResult();
		tabPane.getSelectionModel().select(tabResult);
	}
	
	@FXML public void equalizeHistogram() {
		imageResult = ImageProcess.equalizeHistogram(firstImage);
		updateImageResult();
		tabPane.getSelectionModel().select(tabResult);
		filterHistoryItems.add(new FilterHistoryModel("Equaliza��o histograma"));
	}
	
	@FXML public void openHistogramModal(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HistogramModal.fxml"));
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.setTitle("Histogramas");
			stage.initOwner(((Node)event.getSource()).getScene().getWindow());
			stage.show();
			
			HistogramModalController controller = (HistogramModalController)loader.getController();
			if(firstImage != null) {
				ImageProcess.getGraph(firstImage, controller.barChartImg1);
			}
			if(secondImage != null) {
				ImageProcess.getGraph(secondImage, controller.barChartImg2);
			}
			if(imageResult != null) {
				ImageProcess.getGraph(imageResult, controller.barChartImg3);
			}	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void onMousePressed(MouseEvent evt) {
		if(imageViewFirstTab.getImage() != null) {
			initialX = (int)evt.getX();
			initialY = (int)evt.getY();
		}
	}
	
	@FXML public void onMouseReleased(MouseEvent evt) {
		finalX = (int)evt.getX();
		finalY = (int)evt.getY();
		firstImage = ImageProcess.demarcate(originalImage, initialX, finalX, initialY, finalY);
		//ImageProcess.simulatedTestQuestion3(firstImage, initialX, finalX, initialY, finalY);
		updateFirstImage();
	}
	
	@FXML public void removeDemarcation() {
		firstImage = originalImage;
		initialX = 0;
		initialY = 0;
		finalX = 0;
		finalY = 0;
		updateFirstImage();
	}

	@FXML public void addition() {
		imageResult = ImageProcess.calcAddition(firstImage, secondImage, percTransp.getValue()/100, (100-percTransp.getValue())/100);
		updateImageResult();
		tabPane.getSelectionModel().select(tabResult);
		filterHistoryItems.add(new FilterHistoryModel("Adi��o", 
				String.format("Perc transp. img 1: %f Perc transp. img 2: %f", 
						percTransp.getValue()/100, (100-percTransp.getValue()/100))));
	}
	
	@FXML public void subtraction() {
		imageResult = ImageProcess.calcSubtraction(firstImage, secondImage);
		updateImageResult();
		tabPane.getSelectionModel().select(tabResult);
		filterHistoryItems.add(new FilterHistoryModel("Subtra��o"));
	}
	
	@FXML public void reduceNoise() {
		if(radio3x3.isSelected()) {
			imageResult = ImageProcess.reduceNoise(firstImage, NeighborEnum.NEIGHBOR_3X3);
			filterHistoryItems.add(new FilterHistoryModel("Redu��o de ru�do", "3x3"));
		} else if(radioCross.isSelected()) {
			imageResult = ImageProcess.reduceNoise(firstImage, NeighborEnum.NEIGHBOR_CROSS);
			filterHistoryItems.add(new FilterHistoryModel("Redu��o de ru�do", "Cruz"));
		} else if(radioX.isSelected()) {
			imageResult = ImageProcess.reduceNoise(firstImage, NeighborEnum.NEIGHBOR_X);
			filterHistoryItems.add(new FilterHistoryModel("Redu��o de ru�do", "X"));
		}
		updateImageResult();
	}
	
	@FXML public void challenge1() {
		imageResult = ImageProcess.challenge1(firstImage);
		updateImageResult();
		tabPane.getSelectionModel().select(tabResult);
	}
	
	@FXML public void negative() {
		if(isDemarcated()) {
			imageResult = ImageProcess.calcNegative(originalImage, initialX, finalX, initialY, finalY);
		} else {
			imageResult = ImageProcess.calcNegative(originalImage, 0, 0, 0, 0);
		}
		updateImageResult();
		filterHistoryItems.add(new FilterHistoryModel("Negativa"));
		tabPane.getSelectionModel().select(tabResult);
	}
	
	@FXML public void thresholding() {
		if(isDemarcated()) {
			imageResult = ImageProcess.calcThresholding(originalImage, thresholdSlider.getValue(), initialX, finalX, initialY, finalY);
		} else {
			imageResult = ImageProcess.calcThresholding(originalImage, thresholdSlider.getValue(), 0, 0, 0, 0);
		}
		updateImageResult();
		filterHistoryItems.add(new FilterHistoryModel("Limiariza��o", String.format("Valor: %f", thresholdSlider.getValue())));
		tabPane.getSelectionModel().select(tabResult);
	}
	
	@FXML public void grayScaleAverage() {
		if(isDemarcated()) {
			imageResult = ImageProcess.calcGrayScale(originalImage, initialX, finalX, initialY, finalY);
		} else {
			imageResult = ImageProcess.calcGrayScale(originalImage, 0, 0, 0, 0);
		}
		updateImageResult();
		filterHistoryItems.add(new FilterHistoryModel("Escala de cinza"));
		tabPane.getSelectionModel().select(tabResult);
	}
	
	@FXML private void weightedAverage() {
		int r = Integer.parseInt(txtR.getText());
		int g = Integer.parseInt(txtG.getText());
		int b = Integer.parseInt(txtB.getText());
		
		if(r + g + b > 100) {
            AlertMessage.showMsg("Escala de Cinza", "Erro", "A soma deve ser menor ou igual a 10.", AlertType.ERROR);
		} else {
			if(isDemarcated()) {
				imageResult = ImageProcess.calcWeightedAverage(originalImage, r, g, b, initialX, finalX, initialY, finalY);
			} else {
				imageResult = ImageProcess.calcWeightedAverage(originalImage, r, g, b, 0, 0, 0, 0);
			}
			updateImageResult();
			filterHistoryItems.add(new FilterHistoryModel("M�dia ponderada", String.format("R: %d G: %d B: %d", r, g, b)));
			tabPane.getSelectionModel().select(tabResult);
		}
	}
	
	@FXML public void updateImageResult() {
		imageViewResult.setImage(imageResult);
		imageViewResult.setFitWidth(imageResult.getWidth());
		imageViewResult.setFitHeight(imageResult.getHeight());
	}
	
	@FXML public void updateFirstImage() {
		if(firstImage != null) {
			imageViewFirstTab.setImage(firstImage);
			imageViewFirstTab.setFitWidth(firstImage.getWidth());
			imageViewFirstTab.setFitHeight(firstImage.getHeight());
		}
	}
	
	@FXML public void openFirstImage() {
		f = selectImage();
		if(f != null) {
			firstImage = new Image(f.toURI().toString());
			imageViewFirstTab.setImage(firstImage);
			imageViewFirstTab.setFitWidth(firstImage.getWidth());
			imageViewFirstTab.setFitHeight(firstImage.getHeight());
			originalImage = firstImage;
		}
		
	}
	
	@FXML public void openSecondImage() {
		f = selectImage();
		if(f != null) {
			secondImage = new Image(f.toURI().toString());
			imageViewSecondTab.setImage(secondImage);
			imageViewSecondTab.setFitWidth(secondImage.getWidth());
			imageViewSecondTab.setFitHeight(secondImage.getHeight());
		}
	}
	
	private File selectImage() {
	   FileChooser fileChooser = new FileChooser();
	   fileChooser.getExtensionFilters().add(new 
			   FileChooser.ExtensionFilter(
					   "Imagens", "*.jpg", "*.JPG", 
					   "*.png", "*.PNG", "*.gif", "*.GIF", 
					   "*.bmp", "*.BMP")); 	
	   //fileChooser.setInitialDirectory(new File("C:\\Users\\User\\workspace\\Pdi-2019\\images"));
	   File selectedImage = fileChooser.showOpenDialog(null);
	   try {
		   if (selectedImage != null) {
			   return selectedImage;
		   }
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
	   return null;
	}
	
	@FXML public void rasterImg(MouseEvent evt) {
		ImageView iv = (ImageView)evt.getTarget();
		if(iv.getImage() != null) {
			verifyColor(iv.getImage(), (int)evt.getX(), (int)evt.getY());
		}
	}
	
	private void verifyColor(Image img, int x, int y){
	    try {
			Color color = img.getPixelReader().getColor(x-1, y-1);
			lblR.setText(""+(int) (color.getRed()*255));
			lblG.setText(""+(int) (color.getGreen()*255));
			lblB.setText(""+(int) (color.getBlue()*255));
			pnlColor.setStyle("-fx-background-color: RGB(" + (int)(color.getRed()*255)+","+(int)(color.getGreen()*255)+","+(int)(color.getBlue()*255)+")");
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	@FXML public void save(){
		if (firstImage != null){
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(
					new FileChooser.ExtensionFilter("Imagem", "*.png")); 	
			fileChooser.setInitialDirectory(new 
					File("C:\\Users\\Junior\\eclipse-workspace\\Pdi-2019\\images"));
			File file = fileChooser.showSaveDialog(null);
			if (file !=null){
				BufferedImage bImg = SwingFXUtils.fromFXImage(imageResult, null);
				try {
					ImageIO.write(bImg, "PNG", file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			AlertMessage.showMsg("Salvar imagem", 
					"N�o � poss�vel salvar a imagem.", 
					"N�o h� nenhuma imagem modificada.", 
					AlertType.ERROR);
		}
	}
	
	@FXML
	public void transferResultToFirstImage() {
		firstImage = imageResult;
		originalImage = imageResult;
		updateFirstImage();
	}
	
	public void openFilterHistoryModal() {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FilterHistoryModal.fxml"));
			Parent root = loader.load();
			
			FilterHistoryController controller = (FilterHistoryController)loader.getController();
			if(!filterHistoryItems.isEmpty()) controller.addItems(filterHistoryItems);
			
			stage.setScene(new Scene(root));
			stage.setTitle("Hist�rico");
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isDemarcated() {
		if(initialX != 0 && finalX != 0 && initialY != 0 && finalY != 0) {
			return true;
		}
		return false;
	}
	
	
}
