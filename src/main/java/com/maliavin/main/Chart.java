package com.maliavin.main;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class Chart extends ApplicationFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3296209491840703534L;
	
	private String nameChart;
	
	private String stage;

	/**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public Chart(final String title) {

        super(title);
        
        this.stage = title;
        
        nameChart = "Stage - "+title;

        final XYDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }
    
    /**
     * Creates a sample dataset.
     * 
     * @return a sample dataset.
     * @throws IOException 
     */
    private XYDataset createDataset() {
        
    	File f = new File("d:/Dmitriy/"+stage+".txt");
    	
    	Map<String, XYSeries> map = new HashMap<String, XYSeries>();
    	
    	try
    	{
    	FileInputStream fis = new FileInputStream(f);
    	 
    	//Construct BufferedReader from InputStreamReader
    	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
     
    	String line = null;
    	while ((line = br.readLine()) != null) {
    		String [] lineInfo = line.split("_");
    		if (!map.containsKey(lineInfo[0]))
    		{
    			map.put(lineInfo[0], new XYSeries(lineInfo[0]));
    		}
    		
    		map.get(lineInfo[0]).add(Double.parseDouble(lineInfo[2]), Double.parseDouble(lineInfo[1]));
    	}
     
    	br.close();
    	}catch (Exception e) {
			System.out.println(e.toString());
		}
    	
        final XYSeriesCollection dataset = new XYSeriesCollection();
        
        for (Entry<String, XYSeries> entry : map.entrySet())
        {
        	dataset.addSeries(entry.getValue());
        }
                
        return dataset;
        
    }
    
    /**
     * Creates a chart.
     * 
     * @param dataset  the data for the chart.
     * 
     * @return a chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
             nameChart,               // chart title
            "Secret size",            // x axis label
            "Time",                   // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
  //      legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
    //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        //renderer.setSeriesLinesVisible(0, false);
        //renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        return chart;
        
    }
}
