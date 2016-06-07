/**
 * 
 */
package fi.csc.avaa.cbig.carnivores;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.Series;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.UI;

import fi.csc.avaa.cbig.common.chart.ChartTools;
import fi.csc.avaa.cbig.common.chart.ChartTypes;

/**
 * 
 * @author jmlehtin
 * 
 *         Class for controlling Vaadin charts with Vaadin OptionGroup
 *         component. OptionGroup items may have icons and HTML descriptions
 *         each.
 */
public class ChartControlLegend extends CustomComponent {

	private static final long serialVersionUID = 1L;
	public static Log LOG = LogFactoryUtil.getLog(ChartControlLegend.class);
	private static final String BASEPATH = VaadinService.getCurrent()
			.getBaseDirectory().getAbsolutePath();
	// Group of checkboxes
	private OptionGroup optGrp;
	// Charts that are controlled with this Component
	private List<Chart> charts;
	// Graph / option group IDs
	private List<String> ids;
	// Map for holding the visibility state of the graphs in the charts
	private Map<String, Boolean> graphVisibilities = new HashMap<String, Boolean>();
	private ChartTypes chartType;
	private boolean isInit = true;

	@SuppressWarnings("unchecked")
	public ChartControlLegend(String legendCaption, List<String> graphIds, ChartTypes chartType, List<Chart> chartsToControl) {
		HorizontalLayout baseLayout = new HorizontalLayout();	
		baseLayout.setSpacing(true);
		baseLayout.setMargin(new MarginInfo(true, false, true, false));
		baseLayout.setWidth(100, Unit.PERCENTAGE);
		if (graphIds != null && chartsToControl != null && chartsToControl.size() > 0) {
			this.chartType = chartType;
			this.charts = chartsToControl;
			this.ids = graphIds;
			this.optGrp = new OptionGroup();
			this.optGrp.addStyleName("carnivores-chart-control");
			this.optGrp.setImmediate(true);
			this.optGrp.setMultiSelect(true);
			this.optGrp.setHtmlContentAllowed(true);
			this.optGrp.setCaption(legendCaption);
			for(String id : this.ids) {
				this.optGrp.addItem(id);
				this.optGrp.setItemIcon(id, getCheckBoxIcon(this.chartType, id));
				this.optGrp.setItemCaption(id, ChartTools.getCarnivoresGraphDisplayName(this.chartType, id));
				// Init graph visibility state
				this.graphVisibilities.put(id, false);
			}

			if(chartsToControl.size() == 1) {
				baseLayout.addComponent(optGrp);
				baseLayout.addComponent(chartsToControl.get(0));
				baseLayout.setExpandRatio(optGrp, 0.2f);
				baseLayout.setExpandRatio(chartsToControl.get(0), 0.8f);
			} else if(chartsToControl.size() == 2) {
				baseLayout.addComponent(chartsToControl.get(0));
				baseLayout.addComponent(optGrp);
				baseLayout.addComponent(chartsToControl.get(1));
				baseLayout.setExpandRatio(chartsToControl.get(0), 0.4f);
				baseLayout.setExpandRatio(optGrp, 0.2f);
				baseLayout.setExpandRatio(chartsToControl.get(1), 0.4f);
			}
			baseLayout.setComponentAlignment(optGrp, Alignment.MIDDLE_CENTER);
			
			this.optGrp.addValueChangeListener(e -> {
				Set<String> checkedItems = (Set<String>) e.getProperty()
						.getValue();
				for (String id : this.ids) {
					if (checkedItems.contains(id)) {
						setGraphVisibility(id, true);
					} else {
						setGraphVisibility(id, false);
					}
				}
				toggleChartSeries();
			});
			
			// Set initial value: first item checked
			Set<String> set = new HashSet<>();
			set.add(this.ids.get(0));
			optGrp.setValue(set);
		} else {
			LOG.error("Unable to create optiongroup for charts");
		}
		//ChartTools.getAllTooltipUrls().forEach(url -> JavaScript.getCurrent().execute("(new Image()).src='" + url + "';"));
		setCompositionRoot(baseLayout);
	}

	private void toggleChartSeries() {
		for (Chart chart : charts) {
			List<Series> graphs = chart.getConfiguration().getSeries();
			for (Series series : graphs) {
				DataSeries dSeries = (DataSeries) series;
				String graphId = dSeries.getStack();
				dSeries.setVisible(graphVisibilities.get(graphId), true);
			}
		}
	}

	private void setGraphVisibility(String id, boolean isVisible) {
		if(!isInit && isVisible && !graphVisibilities.get(id)) {
//			String tooltipHtml = ChartTools.getCarnivoresControlTooltip(chartType, id);
//			if(tooltipHtml != null) {
//				Notification not = new Notification(null, tooltipHtml,
//							Notification.Type.HUMANIZED_MESSAGE);
//				not.setHtmlContentAllowed(true);
//				not.setDelayMsec(500);
//				not.setPosition(Position.MIDDLE_RIGHT);
//				UI.getCurrent().showNotification(not);
//			}
		}
		isInit = false;
		graphVisibilities.put(id, isVisible);
	}
	
	/**
	 * Set icons for checkboxes. Use same color as the corresponding graph color.
	 * 
	 * @param optGroup
	 * @param variants
	 */
	private FileResource getCheckBoxIcon(ChartTypes chartType, String graphId) {
		Color color = ChartTools.getCarnivoresGraphColor(chartType, graphId);
		String relPathToFile = "/WEB-INF/images/carnivores/checkbox_color_" + color.toString().substring(1) + ".png";
		return new FileResource(new File(BASEPATH + relPathToFile));
	}
}
