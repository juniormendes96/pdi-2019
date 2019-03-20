package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertMessage {
	
	public static void showMsg(String title, String header, String msg, AlertType type) {
		  Alert alert = new Alert(type);
		  alert.setTitle(title);
		  alert.setHeaderText(header);
		  alert.setContentText(msg);
		  alert.showAndWait();
	 }

}
