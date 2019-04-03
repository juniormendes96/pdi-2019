package application;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import core.ImageProcess;
import core.NoiseReduction;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
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
		int w = (int)firstImage.getWidth();
		int h = (int)firstImage.getHeight();
		
		WritableImage wi = new WritableImage(w,h);
		PixelWriter pw = wi.getPixelWriter();
		
		// vizinhança em cruz
        if(radio3x3.isSelected()) {

            for(int i=1; i<(int)firstImage.getWidth()-1; i++) {
                for(int j=1; j<(int)firstImage.getHeight()-1; j++) {

                	//medianas
                    ArrayList<Double> medians = NoiseReduction.reduction3x3(firstImage, i, j);

                    Color newColor = new Color(medians.get(0),
                            medians.get(1),
                            medians.get(2),
                            1);

                    pw.setColor(i, j, newColor);

                }
            }

            imageViewResult.setImage(wi);
            imageViewResult.setFitWidth(wi.getWidth());

        } else if (radioX.isSelected()) {

            for(int i=1; i<(int)firstImage.getWidth()-1; i++) {
                for(int j=1; j<(int)firstImage.getHeight()-1; j++) {

                    ArrayList<Double> medians = NoiseReduction.reductionX(firstImage, i, j);

                    Color newColor = new Color(medians.get(0),
                            medians.get(1),
                            medians.get(2),
                            1);

                    pw.setColor(i, j, newColor);

                }
            }

            imageViewResult.setImage(wi);
            imageViewResult.setFitWidth(wi.getWidth());

        } else if (radioCross.isSelected()) {

            for(int i=1; i<(int)firstImage.getWidth()-1; i++) {
                for(int j=1; j<(int)firstImage.getHeight()-1; j++) {

                    ArrayList<Double> medians = NoiseReduction.crossReduction(firstImage, i, j);

                    Color newColor = new Color(medians.get(0),
                            medians.get(1),
                            medians.get(2),
                            1);

                    pw.setColor(i, j, newColor);

                }
            }

            imageViewResult.setImage(wi);
            imageViewResult.setFitWidth(wi.getWidth());

        } else {
            AlertMessage.showMsg("Selecione um tipo de redução", "Atenção", "É necessário selecionar uma técnica de aplicação da redução de ruído.", AlertType.WARNING);
        }
        
	}
	
	@FXML
	public void challenge1() {
		imageResult = ImageProcess.challenge1(firstImage);
		updateImageResult();
	}
	
	@FXML
	public void negative() {
		imageResult = ImageProcess.calcNegative(firstImage);
		updateImageResult();
	}
	
	@FXML
	public void thresholding() {
		imageResult = ImageProcess.calcThresholding(firstImage, thresholdSlider.getValue());
		updateImageResult();
	}
	
	@FXML
	public void grayScaleAverage() {
		imageResult = ImageProcess.calcGrayScale(firstImage);
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
			imageResult = ImageProcess.calcWeightedAverage(firstImage, r, g, b);
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
	
	
}
