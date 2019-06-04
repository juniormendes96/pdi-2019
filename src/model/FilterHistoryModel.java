package model;

public class FilterHistoryModel {
	
	private String filterName;
	private String filterSettings;
	
	public FilterHistoryModel(String filterName) {
		this.filterName = filterName;
		this.filterSettings = "-";
	}
	
	public FilterHistoryModel(String filterName, String filterSettings) {
		this.filterName = filterName;
		this.filterSettings = filterSettings;
	}
	
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public String getFilterSettings() {
		return filterSettings;
	}
	public void setFilterSettings(String filterSettings) {
		this.filterSettings = filterSettings;
	}

}
