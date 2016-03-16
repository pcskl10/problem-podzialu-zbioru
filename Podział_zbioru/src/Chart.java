import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Chart extends Settings
{
	static Generation best_inds;
	static int iteration;

	Chart()
	{
		display();
	}
	
    //public static final String Title = "Liczba generacji= " + Settings.MAX_GEN + "   Liczba osobników w gen= " + Settings.IND + "   Liczba zbiorów= " + Settings.SUBSETS;

    public static void main(String[] args) {
    	
    	try 
        {
          UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel");
          //UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel");
        } 
        catch (Exception e) 
        {
          e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                display();
            }
        });
    }

    static void display() {
    	//create the series - add some dummy data
        XYSeries series1 = new XYSeries("Najlepszy");
        XYSeries series2 = new XYSeries("Idealny");
        XYSeries series3 = new XYSeries("Najgorszy");
        
        ArrayList<BigInteger> sumSetList = new ArrayList<BigInteger>(); 
        //int lowerRange = Integer.MAX_VALUE;
        for (int i = 1, j = 0; j < BEST_INDS.listGen.get(0).listInd.size(); i++, j++) 
        {
            series1.add(i, BEST_INDS.listGen.get(0).sumSet(j));
            sumSetList.add(BEST_INDS.listGen.get(0).sumSet(j));
        }

        
        for (int i = 1, j = 0; j < BEST_INDS.listGen.get(0).listInd.size(); i++, j++) 
        {
            series2.add(i, IDEAL_SET);
        }
        
        for (int i = 1, j = 0; j < BEST_INDS.listGen.get(0).listInd.size(); i++, j++) 
        {
            series3.add(i, BEST_INDS.listGen.get(BEST_INDS.listGen.size()-1).sumSet(j));
            sumSetList.add(BEST_INDS.listGen.get(BEST_INDS.listGen.size()-1).sumSet(j));
        }
        
        BigInteger minValue = Collections.min(sumSetList);
        System.out.println(minValue);
        
        //create the datasets
        XYSeriesCollection dataset1 = new XYSeriesCollection();
        dataset1.addSeries(series1);
        dataset1.addSeries(series2);
        dataset1.addSeries(series3);

        //construct the plot
        XYPlot plot = new XYPlot();
        plot.setDataset(0, dataset1);
        
        
       
        
        //customize the plot with renderers and axis
        plot.setRenderer(0, new XYSplineRenderer());//use default fill paint for first series
        XYSplineRenderer splinerenderer = new XYSplineRenderer();
        splinerenderer.setSeriesFillPaint(0, Color.BLUE);
        plot.setRenderer(1, splinerenderer);
       
        plot.setRangeAxis(0, new NumberAxis("Suma zbioru"));
        
        NumberAxis n2 = new NumberAxis("Numer zbioru");
        n2.setTickUnit(new NumberTickUnit(1));
        
        
        
        plot.setDomainAxis(n2);

        //Map the data to the appropriate axis
        plot.mapDatasetToRangeAxis(0, 0);
        plot.mapDatasetToRangeAxis(1, 0);   
        plot.getDomainAxis().setLowerBound(0.5);
        //plot.getRangeAxis().setLowerMargin(minValue.intValue());
        
        //generate the chart
        JFreeChart chart = new JFreeChart("Iteracja " + iteration, plot);
        //chart.getCategoryPlot().getDomainAxis().setLowerMargin(1);
        chart.setBackgroundPaint(Color.WHITE);
        JPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800,700));
        JFrame frame = new JFrame("Wykres");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(chartPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}