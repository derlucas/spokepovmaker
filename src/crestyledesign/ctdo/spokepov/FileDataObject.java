package crestyledesign.ctdo.spokepov;

public class FileDataObject {

	private int version;
	private int angularSteps;
	private int ledCount;
	private DotDataEntry[][] data;
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getAngularSteps() {
		return angularSteps;
	}
	public void setAngularSteps(int angularSteps) {
		this.angularSteps = angularSteps;
	}
	public int getLedCount() {
		return ledCount;
	}
	public void setLedCount(int ledCount) {
		this.ledCount = ledCount;
	}
	public DotDataEntry[][] getData() {
		return data;
	}
	public void setData(DotDataEntry[][] data) {
		this.data = data;
	}
	
	
	
}
