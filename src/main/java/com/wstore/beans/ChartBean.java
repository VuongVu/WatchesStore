package com.wstore.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean
@SessionScoped
public class ChartBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2814504631073464325L;
	BarChartModel barChartModel;

	@PostConstruct
    public void init() {
        createBarModel();
    }
	public BarChartModel getBarChartModel() {
		return barChartModel;
	}

	public void setBarChartModel(BarChartModel barChartModel) {
		this.barChartModel = barChartModel;
	}
	
	private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 120);
        boys.set("2005", 100);
        boys.set("2006", 44);
        boys.set("2007", 150);
        boys.set("2008", 25);
 
        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 110);
        girls.set("2007", 135);
        girls.set("2008", 120);
 
        model.addSeries(boys);
        model.addSeries(girls);
         
        return model;
    }
	
	private void createBarModel() {
		barChartModel = initBarModel();
         
		barChartModel.setTitle("Bar Chart");
		barChartModel.setLegendPosition("ne");
         
        Axis xAxis = barChartModel.getAxis(AxisType.X);
        xAxis.setLabel("Gender");
         
        Axis yAxis = barChartModel.getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }
	
}
