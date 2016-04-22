package fi.csc.avaa.cbig.carnivores;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.vaadin.addon.charts.Chart;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import fi.csc.avaa.cbig.common.AppType;
import fi.csc.avaa.cbig.common.chart.ChartTypes;
import fi.csc.avaa.cbig.common.chart.ChartVisualization;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresAllSpeciesHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresPresentFamilyHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresPresentIUCNHeaders;
import fi.csc.avaa.cbig.common.csv.CSVHeaders.CarnivoresPresentSizeHeaders;
import fi.csc.avaa.cbig.common.csv.CSVTools;
import fi.csc.avaa.cbig.common.csv.CSVType;

/**
 * @author jmlehtin
 *
 */
@SuppressWarnings("serial")
@Title("Carnivores charts")
public class CarnivoresUI extends UI {
	
	@WebServlet(asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = CarnivoresUI.class)
	public static class Servlet extends VaadinServlet {
	}
	
	public static Log LOG = LogFactoryUtil.getLog(CarnivoresUI.class);
	// App base path
	String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	// Class for creating the actual charts
	private ChartVisualization chartVis = new ChartVisualization();
	// Cache for holding already successfully fetched CSV data
	private static Map<CSVType, List<Map<String, String>>> cbigCsvData = new HashMap<CSVType, List<Map<String, String>>>();
	// Progress bar to show when charts are being loaded
	private final ProgressBar progressBar = new ProgressBar();
	// Outermost layout component within this portlet
	private VerticalLayout portletBaseLayout;
	
    /**
     * Default constructor. 
     */
    public CarnivoresUI() {
    	// Check for server changes every 1000ms
		// Polling used only when waiting for the charts to be drawn
		setPollInterval(1000);

		// Outermost layout for the portlet
		this.portletBaseLayout = new VerticalLayout();
    }
    
	@Override
	protected void init(VaadinRequest request) {
		LOG.debug("Starting to handle VaadinRequest in init method..");
		// Add progress bar to layout
		initProgressBar();
		
		// Run chart loading in another thread to allow progress bar be visible
		// during this time
		class WorkThread extends Thread {

			@Override
			public void run() {
				// Lock the UI session for protecting data and to avoid
				// concurrent access
				UI.getCurrent().getSession().getLockInstance().lock();
				try {
					boolean isOK = true;
					
					// Create layout for page description above the charts
					HorizontalLayout descrLayout = new HorizontalLayout();
					descrLayout.setSizeFull();
					
					Label pageDescr = new Label(
							"<h2>Curves</h2><p id='page-description'>The curves show how much a certain part of the landscape covers the ranges of carnivores on average. You can explore the curves for all carnivore species, different carnivore families, conservation statuses according to the IUCN, and body size. For example, you can compare the curves for Felidae and Dasyuridae and see the difference caused by different range sizes. Place the mouse on top of the curves to see what proportion of the ranges could be protected via a certain proportion of the land.</p>");
					pageDescr.setStyleName("page-description-container");
					pageDescr.setContentMode(ContentMode.HTML);
					descrLayout.addComponent(pageDescr);
					descrLayout.setComponentAlignment(pageDescr, Alignment.MIDDLE_CENTER);
					
					// Create layout for charts and legends
					
					VerticalLayout chartsLayout = new VerticalLayout();
					chartsLayout.setWidth("100%");
					
					portletBaseLayout.addComponent(descrLayout);
					portletBaseLayout.addComponent(chartsLayout);
					
					Chart allSpeciesChart = createCarnivoresChart(CSVType.ALL_SPECIES, ChartTypes.CV_ALL_SPECIES);
					Chart presentFamilyChart = createCarnivoresChart(CSVType.PRESENT_FAMILY, ChartTypes.CV_PRESENT_FAMILY);
					Chart futureFamilyChart = createCarnivoresChart(CSVType.FUTURE_FAMILY, ChartTypes.CV_FUTURE_FAMILY);
					Chart presentIUCNChart = createCarnivoresChart(CSVType.PRESENT_IUCN, ChartTypes.CV_PRESENT_IUCN);
					Chart futureIUCNChart = createCarnivoresChart(CSVType.FUTURE_IUCN, ChartTypes.CV_FUTURE_IUCN);
					Chart presentSizeChart = createCarnivoresChart(CSVType.PRESENT_SIZE, ChartTypes.CV_PRESENT_SIZE);
					Chart futureSizeChart = createCarnivoresChart(CSVType.FUTURE_SIZE, ChartTypes.CV_FUTURE_SIZE);
					
					ChartControlLegend allSpeciesCtrl = new ChartControlLegend("All Species", CarnivoresAllSpeciesHeaders.getGraphIds(), ChartTypes.CV_ALL_SPECIES, Arrays.asList(allSpeciesChart));
					descrLayout.addComponent(allSpeciesCtrl);
					descrLayout.setExpandRatio(pageDescr, 0.35f);
					descrLayout.setExpandRatio(allSpeciesCtrl, 0.65f);
					ChartControlLegend familyCtrl = new ChartControlLegend("Vertebrae Families", CarnivoresPresentFamilyHeaders.getGraphIds(), ChartTypes.CV_PRESENT_FAMILY, Arrays.asList(presentFamilyChart, futureFamilyChart));
					chartsLayout.addComponents(familyCtrl);
					ChartControlLegend iucnCtrl = new ChartControlLegend("IUCN", CarnivoresPresentIUCNHeaders.getGraphIds(), ChartTypes.CV_PRESENT_IUCN, Arrays.asList(presentIUCNChart, futureIUCNChart));
					chartsLayout.addComponents(iucnCtrl);
					ChartControlLegend sizeCtrl = new ChartControlLegend("Body Size", CarnivoresPresentSizeHeaders.getGraphIds(), ChartTypes.CV_PRESENT_SIZE, Arrays.asList(presentSizeChart, futureSizeChart));
					chartsLayout.addComponents(sizeCtrl);
					if (isOK) {
						
					} else {
						LOG.error("Error!");
						// Detach UI from this session
						close();
						showErrorNotification();
					}
					
				} finally {
					// When operations are done, progress bar is no longer
					// needed
					removeProgressBar();
					// Release lock on this UI session
					UI.getCurrent().getSession().getLockInstance().unlock();
					// Do not poll server for changes any more
					setPollInterval(-1);
				}

			}
			/**
			 * Show notification for the user to refresh the page in case of error
			 */
			private void showErrorNotification() {
				Notification notif = new Notification(
						"Please refresh the page",
						"Error occurred when drawing the charts..",
						Notification.Type.WARNING_MESSAGE);
				notif.show(UI.getCurrent().getPage());
			}

		}
		
		UI.getCurrent().setContent(portletBaseLayout);
		final WorkThread th = new WorkThread();
		th.start();
	}
	
	/**
	 * Create progress bar which does not inform user when the waiting time
	 * should end
	 */
	private void initProgressBar() {
		progressBar.setIndeterminate(true);
		progressBar
				.setCaption("Please wait while the charts are being loaded..");
		portletBaseLayout.addComponent(progressBar);
		portletBaseLayout.setComponentAlignment(progressBar,
				Alignment.TOP_CENTER);
	}

	private void removeProgressBar() {
		portletBaseLayout.removeComponent(progressBar);
	}

	private Chart createCarnivoresChart(CSVType csvType, ChartTypes chartType) {
		List<Map<String, String>> csvData = CSVTools.getDataRowsForCsvType(csvType, cbigCsvData);
		return chartVis.createCBIGChart(450, csvType, chartType, csvData, AppType.CARNIVORES);
	}

}
