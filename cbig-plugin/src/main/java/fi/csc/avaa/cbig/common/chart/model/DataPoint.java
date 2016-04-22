package fi.csc.avaa.cbig.common.chart.model;

/**
 * Class representing a single data point in a graph contained in a Vaadin chart.
 * Sorting is based on the x value of DataPoint.
 * 
 * @author jmlehtin
 *
 */
public class DataPoint implements Comparable<DataPoint> {

	private double x;
	private double y;
	
	public DataPoint() {
		this.x = 0.0d;
		this.y = 0.0d;
	}
	
	public DataPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public int compareTo(DataPoint o) {
		if(this.getX() < o.getX()) {
			return -1;
		} else if(this.getX() == o.getX()) {
			return 0;
		} else {
			return 1;
		}
	}
	
	
}
