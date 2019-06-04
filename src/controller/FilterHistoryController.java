package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.FilterHistoryModel;

public class FilterHistoryController implements Initializable{

	@FXML private TableView<FilterHistoryModel> tblFilterHistory;
	@FXML private TableColumn<FilterHistoryModel, String> columnFilterName;
	@FXML private TableColumn<FilterHistoryModel, String> columnFilterSettings;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		columnFilterName.setCellValueFactory(new PropertyValueFactory<>("filterName"));
		columnFilterSettings.setCellValueFactory(new PropertyValueFactory<>("filterSettings"));
	}
	
	public void addItems(List<FilterHistoryModel> list) {
		tblFilterHistory.setItems(FXCollections.observableArrayList(list));
	}

}
