package application;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.AlertMessage;

public class SampleController {
	
	@FXML
	private ImageView imageViewFirstTab;
	
	@FXML
	private ImageView imageViewSecondTab;
	
	@FXML
	private ImageView imageViewResult;
	
	@FXML
	private Label lblR;
	
	@FXML
	private Label lblG;
	
	@FXML
	private Label lblB;
	
	@FXML
	private Pane pnlColor;
	
	@FXML
	private TextField txtR;
	
	@FXML
	private TextField txtG;
	
	@FXML
	private TextField txtB;
	
	@FXML
	private Slider thresholdSlider;
	
	@FXML
	private RadioButton radioCross;
	
	@FXML
	private RadioButton radioX;
	
	@FXML
	private RadioButton radio3x3;
	
	@FXML
	private Slider percTransp;
	
	private Image firstImage;
	private Image secondImage;
	private Image imageResult;
	private File f;
	
	private int initialX, finalX, initialY, finalY;
	
	@FXML
	public void openHistogramModal(ActionEvent event) {
		try {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("HistogramModal.fxml"));
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
	
	@FXML
	public void onMousePressed(MouseEvent evt) {
		if(imageViewFirstTab.getImage() != null) {
			initialX = (int)evt.getX();
			initialY = (int)evt.getY();
		}
	}
	
	@FXML
	public void onMouseReleased(MouseEvent evt) {
		finalX = (int)evt.getX();
		finalY = (int)evt.getY();
		imageResult = ImageProcess.demarcate(firstImage, initialX, finalX, initialY, finalY);
		updateImageResult();
	}

	@FXML
	public void addition() {
		imageResult = ImageProcess.calcAddition(firstImage, secondImage, percTransp.getValue()/100, (100-percTransp.getValue())/100);
		updateImageResult();
	}
	
	@FXML
	public void subtraction() {
		imageResult = ImageProcess.calcSubtraction(firstImage, secondImage);
		updateImageResult();
	}
	
	@FXML
	public void reduceNoise() {
		if(radio3x3.isSelected()) {
			imageResult = ImageProcess.reduceNoise(firstImage, NeighborEnum.NEIGHBOR_3X3);
		} else if(radioCross.isSelected()) {
			imageResult = ImageProcess.reduceNoise(firstImage, NeighborEnum.NEIGHBOR_CROSS);
		} else if(radioX.isSelected()) {
			imageResult = ImageProcess.reduceNoise(firstImage, NeighborEnum.NEIGHBOR_X);
		}
		updateImageResult();
	}
	
	@FXML
	public void challenge1() {
		imageResult = ImageProcess.challenge1(firstImage);
		updateImageResult();
	}
	
	@FXML
	public void negative() {
		if(isDemarcated()) {
			imageResult = ImageProcess.calcNegative(firstImage, initialX, finalX, initialY, finalY);
		} else {
			imageResult = ImageProcess.calcNegative(firstImage, 0, 0, 0, 0);
		}
		updateImageResult();
	}
	
	@FXML
	public void thresholding() {
		if(isDemarcated()) {
			imageResult = ImageProcess.calcThresholding(firstImage, thresholdSlider.getValue(), initialX, finalX, initialY, finalY);
		} else {
			imageResult = ImageProcess.calcThresholding(firstImage, thresholdSlider.getValue(), 0, 0, 0, 0);
		}
		updateImageResult();
	}
	
	@FXML
	public void grayScaleAverage() {
		if(isDemarcated()) {
			imageResult = ImageProcess.calcGrayScale(firstImage, initialX, finalX, initialY, finalY);
		} else {
			imageResult = ImageProcess.calcGrayScale(firstImage, 0, 0, 0, 0);
		}
		updateImageResult();
	}
	
	@FXML
	private void weightedAverage() {
		int r = Integer.parseInt(txtR.getText());
		int g = Integer.parseInt(txtG.getText());
		int b = Integer.parseInt(txtB.getText());
		
		if(r + g + b > 100) {
            AlertMessage.showMsg("Escala de Cinza", "Erro", "A soma deve ser menor ou igual a 10.", AlertType.ERROR);
		} else {
			if(isDemarcated()) {
				imageResult = ImageProcess.calcWeightedAverage(firstImage, r, g, b, initialX, finalX, initialY, finalY);
			} else {
				imageResult = ImageProcess.calcWeightedAverage(firstImage, r, g, b, 0, 0, 0, 0);
			}
			updateImageResult();
		}
	}
	
	@FXML
	public void updateImageResult() {
		imageViewResult.setImage(imageResult);
		imageViewResult.setFitWidth(imageResult.getWidth());
		imageViewResult.setFitHeight(imageResult.getHeight());
	}
	
	@FXML
	public void openFirstImage() {
		f = selectImage();
		if(f != null) {
			firstImage = new Image(f.toURI().toString());
			imageViewFirstTab.setImage(firstImage);
			imageViewFirstTab.setFitWidth(firstImage.getWidth());
			imageViewFirstTab.setFitHeight(firstImage.getHeight());
		}
	}
	
	@FXML
	public void openSecondImage() {
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
	   fileChooser.setInitialDirectory(new File(
			   "C:\\Users\\vilma\\eclipse-workspace\\Pdi-2019\\images"));
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
	
	@FXML
	public void rasterImg(MouseEvent evt) {
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
	
	@FXML
	public void save(){
		if (firstImage != null){
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(
					new FileChooser.ExtensionFilter("Imagem", "*.png")); 	
			fileChooser.setInitialDirectory(new 
					File("C:\\Users\\vilma\\eclipse-workspace\\Pdi-2019\\images"));
			File file = fileChooser.showSaveDialog(null);
			if (file !=null){
				BufferedImage bImg = SwingFXUtils.fromFXImage(firstImage, null);
				try {
					ImageIO.write(bImg, "PNG", file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			AlertMessage.showMsg("Salvar imagem", 
					"Não é possível salvar a imagem.", 
					"Não há nenhuma imagem modificada.", 
					AlertType.ERROR);
		}
	}
	
	public boolean isDemarcated() {
		if(initialX != 0 && finalX != 0 && initialY != 0 && finalY != 0) {
			return true;
		}
		return false;
	}
	
	
}
