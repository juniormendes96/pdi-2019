package application;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.ImageProcess;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

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
	
	private Image firstImage;
	private Image secondImage;
	private Image imageResult;
	private File f;
	
	@FXML
	public void greyScaleAverage() {
		imageResult = ImageProcess.greyScale(firstImage);
		updateImageResult();
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
			showMsg("Salvar imagem", 
					"Não é possível salvar a imagem.", 
					"Não há nenhuma imagem modificada.", 
					AlertType.ERROR);
		}
  }
	
	 private void showMsg(String title, String header, String msg, AlertType type) {
		  Alert alert = new Alert(type);
		  alert.setTitle(title);
		  alert.setHeaderText(header);
		  alert.setContentText(msg);
		  alert.showAndWait();
	  }
	
}
