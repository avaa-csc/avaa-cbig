package fi.csc.avaa.cbig.common.chart.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Class for extracting and grouping CSV data for the use of Vaadin charts. An
 * instance of this class represents one graph (of many) data points in a Vaadin
 * chart.
 * 
 * @author jmlehtin
 *
 */
public class GraphData {

	// List of single data points for this graph
	private List<DataPoint> dataPoints;

	public GraphData() {
		this.dataPoints = new ArrayList<DataPoint>();
	}

	public List<DataPoint> getDataPoints() {
		return dataPoints;
	}

	public void setDataPoints(List<DataPoint> dataPoints) {
		this.dataPoints = dataPoints;
	}
	
	public void removeDuplicateXPoints() {
		double prevX = -1;
		Iterator<DataPoint> it = dataPoints.iterator();
		while(it.hasNext()) {
			DataPoint dp = it.next();
			double x = dp.getX();
			if(dp == null || prevX == x) {
				it.remove();
			} else {
				prevX = x;
			}
		}
	}
	
	public void sortDataPoints() {
		Collections.sort(dataPoints);
	}
	
}
