package model;

public class DataModel {
	DscDataList dataList;

	public DataModel(){
		dataList = new DscDataList();
	}
	public DscDataList getDataList() {
		return dataList;
	}

	public void setDataList(DscDataList dataList) {
		this.dataList = dataList;
	}
}
