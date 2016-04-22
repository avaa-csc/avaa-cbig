package fi.csc.avaa.cbig.common.chart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.Axis;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Credits;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.LayoutDirection;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.Marker;
import com.vaadin.addon.charts.model.MarkerSymbolEnum;
import com.vaadin.addon.charts.model.PlotOptionsLine;
import com.vaadin.addon.charts.model.SubTitle;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.ZoomType;
import com.vaadin.addon.charts.model.style.FontWeight;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.model.style.Style;

import fi.csc.avaa.cbig.common.AppType;
import fi.csc.avaa.cbig.common.CSVToChartTools;
import fi.csc.avaa.cbig.common.chart.model.GraphData;
import fi.csc.avaa.cbig.common.chart.model.DataPoint;
import fi.csc.avaa.cbig.common.csv.CSVType;
import fi.csc.avaa.cbig.common.csv.CSVDataValueTypes.GPANGroupType;
import fi.csc.avaa.cbig.common.csv.CSVDataValueTypes.GPANVariantType;
import fi.csc.avaa.tools.NumberTools;
import fi.csc.avaa.tools.StringTools;

/**
 * Class for creating Vaadin charts based on received dataSet containing the data points (from CSV data).
 * 
 * @author jmlehtin
 *
 */
public class ChartVisualization {

	private static final Log LOG = LogFactoryUtil.getLog(ChartVisualization.class);
	// Cache file for holding the already created charts
    private Map<ChartTypes, Chart> cbigCharts = new HashMap<ChartTypes,Chart>();
	
	public Map<ChartTypes, Chart> getCbigCharts() {
		return cbigCharts;
	}
	
	/**
	 * Create chart using data from a specific CSV file
	 * 
	 * @param csvType
	 *            CSV file type to be used as the source data for the chart
	 * @param chartType
	 *            Type of the chart that needs to be created using the CSV data
	 * @param liferayipc
	 * @return Chart that was created. If there was problems creating the chart,
	 *         return null.
	 */
	public Chart createCBIGChart(int chartHeight, CSVType csvType, ChartTypes chartType, List<Map<String, String>> csvData, AppType appType) {
		LOG.debug("Starting to create " + chartType.name() + " chart using "
				+ csvType.name() + " CSV file..");
		return getChart(null, chartHeight + "px", chartType,
				CSVToChartTools.getChartDataPoints(appType, csvType, csvData, chartType), appType);
	}
	
	private Chart getChart(String width, String height, ChartTypes chartType, Map<String, GraphData> dataSet, AppType appType) {
		if(cbigCharts.containsKey(chartType)) {
    		LOG.debug("Using cached chart..");
    		return cbigCharts.get(chartType);
    	} else {
    		LOG.debug("Trying to create " + chartType.name() + " chart using read CSV data..");
    		if(dataSet == null) {
	    		LOG.warn("Nothing to draw in CSV data set");
	    		return null;
	    	}
    		
    		Chart chart = new Chart();
    		
    		if(width != null) {
	        	chart.setWidth(width);
	        } else {
	        	chart.setWidth("100%");
	        }
	        
	        if(height != null) {
	        	chart.setHeight(height);
	        } else {
	        	chart.setHeight("100%");
	        }
    		
    		// Set chart basic data
	    	String title = ChartTools.getChartTitle(chartType);
	    	String subtitle = ChartTools.getChartSubtitle(chartType);
	    	String xAxisName = ChartTools.getChartXAxisName(chartType);
	    	String yAxisName = ChartTools.getChartYAxisName(chartType);
	    	
	    	Configuration conf = new Configuration();
	        conf.getChart().setType(ChartType.LINE);
	    	conf.getChart().setZoomType(ZoomType.XY);
	    	
	    	if(!StringTools.isEmptyOrNull(title)) {
	        	Title chartTitle = conf.getTitle(); 
	        	chartTitle.setText(title);
	        	Style titleStyle = new Style();
	        	titleStyle.setFontSize("20px");
	        	chartTitle.setStyle(titleStyle);
	        }
	        if(!StringTools.isEmptyOrNull(subtitle)) {
	        	SubTitle chartSubtitle = conf.getSubTitle(); 
	        	chartSubtitle.setText(subtitle);
	        	Style subtitleStyle = new Style();
	        	subtitleStyle.setFontSize("16px");
	        	chartSubtitle.setStyle(subtitleStyle);
	        }
	        
	        Axis xAxis = conf.getxAxis();
	        Title xTitle = new Title(xAxisName);
	        Style xStyle = new Style();
	        xStyle.setFontSize("15px");
	        xTitle.setStyle(xStyle);
	        xAxis.setTitle(xTitle);
	        xAxis.getTitle().setVerticalAlign(VerticalAlign.HIGH);
			
	        Axis yAxis = conf.getyAxis();
	        Title yTitle = new Title(yAxisName);
	        Style yStyle = new Style();
	        yStyle.setFontSize("15px");
	        yTitle.setStyle(yStyle);
	        yAxis.setTitle(yTitle);
	        yAxis.getTitle().setVerticalAlign(VerticalAlign.HIGH);
	        
	        // This is used to control what is shown when mouse is moved over a graph
	        Tooltip tooltip = conf.getTooltip();
	        switch (chartType) {
			case GPAN_IUCN:
				tooltip.setFormatter("'Protecting ' + Highcharts.numberFormat(this.x, 0) + '% of terrestrial world could cover<br/>on average ' + Highcharts.numberFormat(this.y, 0) + '% of ' + this.series.name + ' IUCN ranges'");
				break;
			case GPAN_MEAN:
				tooltip.setFormatter("'Protecting ' + Highcharts.numberFormat(this.x, 0) + '% of terrestrial world could cover<br/>on average ' + Highcharts.numberFormat(this.y, 0) + '% in variant ' + this.series.name");
				break;
			case GPAN_SPECIES:
				tooltip.setFormatter("'Protecting ' + Highcharts.numberFormat(this.x, 0) + '% of terrestrial world could cover<br/>on average ' + Highcharts.numberFormat(this.y, 0) + '% of ' + this.series.name + ' species ranges'");
				break;
			case CV_ALL_SPECIES:
				tooltip.setFormatter("'Protecting ' + Highcharts.numberFormat(this.x, 0) + '% of terrestrial world could cover<br/>on average ' + Highcharts.numberFormat(this.y, 0) + '% of ' + this.series.name + ' IUCN ranges'");
				break;
			case CV_FUTURE_FAMILY:
				tooltip.setFormatter("'Protecting ' + Highcharts.numberFormat(this.x, 0) + '% of terrestrial world could cover<br/>on average ' + Highcharts.numberFormat(this.y, 0) + '% of ' + this.series.name + ' IUCN ranges'");
				break;
			case CV_FUTURE_IUCN:
				tooltip.setFormatter("'Protecting ' + Highcharts.numberFormat(this.x, 0) + '% of terrestrial world could cover<br/>on average ' + Highcharts.numberFormat(this.y, 0) + '% of ' + this.series.name + ' IUCN ranges'");
				break;
			case CV_FUTURE_SIZE:
				tooltip.setFormatter("'Protecting ' + Highcharts.numberFormat(this.x, 0) + '% of terrestrial world could cover<br/>on average ' + Highcharts.numberFormat(this.y, 0) + '% of ' + this.series.name + ' IUCN ranges'");
				break;
			case CV_PRESENT_FAMILY:
				tooltip.setFormatter("'Protecting ' + Highcharts.numberFormat(this.x, 0) + '% of terrestrial world could cover<br/>on average ' + Highcharts.numberFormat(this.y, 0) + '% of ' + this.series.name + ' IUCN ranges'");
				break;
			case CV_PRESENT_IUCN:
				tooltip.setFormatter("'Protecting ' + Highcharts.numberFormat(this.x, 0) + '% of terrestrial world could cover<br/>on average ' + Highcharts.numberFormat(this.y, 0) + '% of ' + this.series.name + ' IUCN ranges'");
				break;
			case CV_PRESENT_SIZE:
				tooltip.setFormatter("'Protecting ' + Highcharts.numberFormat(this.x, 0) + '% of terrestrial world could cover<br/>on average ' + Highcharts.numberFormat(this.y, 0) + '% of ' + this.series.name + ' IUCN ranges'");
				break;
			default:
				break;
			}
	        
	        // Configure the legend
	        Legend legend = conf.getLegend();
	        // Do not display legends for any charts
	        legend.setEnabled(false);
	        
	        switch (appType) {
			case CARNIVORES:
				setCarnivoresChartConfig(chartType, dataSet, conf);
				break;
			case GPAN:
				setGPANChartConfig(chartType, dataSet, conf);
				break;
			default:
				break;
			}
	        
	        chart.drawChart(conf);
	        // Store chart into cache
	        cbigCharts.put(chartType, chart);
	        LOG.debug("Done creating chart!");
	        return chart;
    	}
	}

	private void setCarnivoresChartConfig(ChartTypes chartType, Map<String, GraphData> dataSet, Configuration conf) {
		Axis xAxis = conf.getxAxis();
		xAxis.setReversed(Boolean.FALSE);
        xAxis.setMin(0);
        xAxis.setMax(100);

        Axis yAxis = conf.getyAxis();
        yAxis.setMin(0);
        yAxis.setMax(100);

        // Set graph-specific variables
        for(Map.Entry<String, GraphData> graphItem : dataSet.entrySet()) {
        	String graphId = graphItem.getKey();
        	DataSeries series = new DataSeries();
        	PlotOptionsLine plotOptionsLine = new PlotOptionsLine();
        	plotOptionsLine.setLineWidth(2.5);
	        plotOptionsLine.setShadow(true);
	        plotOptionsLine.setAnimation(false);
	        plotOptionsLine.setColor(ChartTools.getCarnivoresGraphColor(chartType, graphId));
	        Marker marker = new Marker();
	        marker.setSymbol(MarkerSymbolEnum.CIRCLE);
	        plotOptionsLine.setMarker(marker);
	        series.setPlotOptions(plotOptionsLine);
        	
        	String seriesName = ChartTools.getCarnivoresGraphDisplayName(chartType, graphId);
			series.setName(seriesName);
			
			// Set stack variable to hold graph id
			series.setStack(graphId);
	        // Here the actual data points are set to the graph
			List<DataPoint> dataPoints = graphItem.getValue().getDataPoints();
        	for(DataPoint point : dataPoints) {
        		DataSeriesItem dsi = new DataSeriesItem();
        		dsi.setX(point.getX());
        		dsi.setY(point.getY()*100);
        		dsi.setMarker(new Marker(false));
        		series.add(dsi);
        	}
        	conf.addSeries(series);
        }
	}

	/**
     * Creates a Vaadin chart with the given dataSet.
     * 
     * @param width
     * 			Width of the chart 
     * @param height
     * 			Height of the chart
     * @param chartType
     * 			Type of the chart to be drawn
     * @param dataSet
     * 			Map containing all the graph data that is to be drawn into this chart
     * @param liferayipc
     * 			Object for enabling inter-portlet communication.
     * @return
     * 			Chart object
     */
    public void setGPANChartConfig(ChartTypes chartType, Map<String, GraphData> dataSet, Configuration conf) {
    	Credits creds = new Credits("University of Helsinki");
        creds.setHref("http://www.helsinki.fi/university/");
        Style credStyle = new Style();
        credStyle.setFontSize("12px");
        creds.setStyle(credStyle);
        conf.setCredits(creds);
    	
    	Axis xAxis = conf.getxAxis();
        xAxis.setMin(0);
        xAxis.setMax(100);

        Axis yAxis = conf.getyAxis();
        yAxis.setMin(0);
        yAxis.setMax(100);

        // Set graph-specific variables
        for(Map.Entry<String, GraphData> graphItem : dataSet.entrySet()) {
        	String graphId = graphItem.getKey();
        	GPANVariantType variant = ChartTools.getGPANVariantCSVTypeFromGraphId(graphId);
        	GPANGroupType group = ChartTools.getGPANGroupCSVTypeFromGraphId(graphId);
        	
        	DataSeries series = new DataSeries();
        	PlotOptionsLine plotOptionsLine = new PlotOptionsLine();
        	plotOptionsLine.setLineWidth(2.5);
        	Marker marker = new Marker();
	        marker.setSymbol(ChartTools.getGPANLineSymbol(variant));
	        plotOptionsLine.setMarker(marker);
	        plotOptionsLine.setShadow(true);
	        plotOptionsLine.setAnimation(false);
	        /*
	         * If developers need to use large data sets and point specific
	         * settings, they can override the default turbo threshold. Here we set
	         * it to 200000 (default 1000). Turbo threshold is Highcharts related
	         * configuration that works as a "sanity threshold" so that old browsers
	         * wont drop to their knees under load. Without this Highcharts might
	         * not render chart if data items has e.g. name set.
	         */
//		        plotOptionsLine.setTurboThreshold(200000);
	        
	        // Set graph color
	        plotOptionsLine.setColor(ChartTools.getGPANGraphColor(variant));
	        series.setPlotOptions(plotOptionsLine);
        	
        	String seriesName = ChartTools.getGPANGraphDisplayName(variant, group);
			series.setName(seriesName);
			
			// Set stack variable to hold graph id of variant-group combo
			series.setStack(graphId);
	        // Here the actual data points are set to the graph
			List<DataPoint> dataPoints = graphItem.getValue().getDataPoints();
        	for(DataPoint point : dataPoints) {
        		DataSeriesItem dsi = new DataSeriesItem();
        		dsi.setX(NumberTools.roundDoubleWithDecimals(point.getX(), 2));
        		dsi.setY(NumberTools.roundDoubleWithDecimals(point.getY(), 2));
        		dsi.setMarker(new Marker(false));
        		series.add(dsi);
        	}

        	// Uncomment to enable markers for graphs
//	        	enableMarkerForRandomSeriesItem(series);
        	
        	conf.addSeries(series);
        }
    }
    
	/**
     * Generate 6 randomly distributed markers for a single graph
     * 
     * @param series
     */
    private void enableMarkerForRandomSeriesItem(DataSeries series) {
    	Random randomizer = new Random();
    	List<DataSeriesItem> items = series.getData();
    	for(int i=0; i <= 5; i++) {
    		DataSeriesItem item = items.get(randomizer.nextInt(items.size()));
    		item.getMarker().setEnabled(true);
    	}
	}

	/**
	 * Set legend parameters
	 * 
	 * @param legend
	 * @param chartType
	 */
	private void setChartLegendParams(Legend legend, ChartTypes chartType) {
    	switch (chartType) {
		case GPAN_IUCN:
			legend.setLayout(LayoutDirection.VERTICAL);
	        legend.setHorizontalAlign(HorizontalAlign.RIGHT);
	        legend.setVerticalAlign(VerticalAlign.TOP);
	        legend.setX(-30d);
	        legend.setY(30d);
	        legend.setBackgroundColor(new SolidColor("#F9F9F9"));
	        Style iucnItemStyle = legend.getItemStyle();
	        iucnItemStyle.setFontWeight(FontWeight.BOLD);
//	        Style iucnHiddenStyle = new Style();
//	        iucnHiddenStyle.setFontWeight(FontWeight.NORMAL);
//	        legend.setItemHiddenStyle(iucnHiddenStyle);
	        legend.setEnabled(true);
	        break;
		case GPAN_MEAN:
			legend.setLayout(LayoutDirection.VERTICAL);
	        legend.setHorizontalAlign(HorizontalAlign.LEFT);
	        legend.setVerticalAlign(VerticalAlign.MIDDLE);
	        legend.setX(20d);
	        legend.setShadow(false);
	        legend.setBackgroundColor(new SolidColor("#F9F9F9"));
	        legend.setItemMarginBottom(10);
	        Style meanItemStyle = legend.getItemStyle();
	        meanItemStyle.setFontWeight(FontWeight.BOLD);
	        meanItemStyle.setFontSize("20px");
	        meanItemStyle.setFontFamily("Arial, Helvetica, sans-serif");
	        legend.setItemStyle(meanItemStyle);
			break;
		case GPAN_SPECIES:
			legend.setLayout(LayoutDirection.VERTICAL);
	        legend.setHorizontalAlign(HorizontalAlign.RIGHT);
	        legend.setVerticalAlign(VerticalAlign.TOP);
	        legend.setX(-10d);
	        legend.setY(100d);
	        legend.setBackgroundColor(new SolidColor("#F9F9F9"));
	        Style speciesItemStyle = legend.getItemStyle();
	        speciesItemStyle.setFontWeight(FontWeight.BOLD);
//	        Style speciesHiddenStyle = new Style();
//	        speciesHiddenStyle.setFontWeight(FontWeight.NORMAL);
//	        legend.setItemHiddenStyle(speciesHiddenStyle);
	        legend.setEnabled(true);
			break;
		default:
			break;
		}
	}
}