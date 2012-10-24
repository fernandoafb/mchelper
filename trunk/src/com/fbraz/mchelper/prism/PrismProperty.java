package com.fbraz.mchelper.prism;

import java.io.BufferedWriter;
import java.io.IOException;


import com.fbraz.mchelper.mch.Property;

public class PrismProperty extends Property {
	
	public PrismProperty() {
		super();
	}
	
	//MCProperty
	private String prop;
	private String mConst;
	private String pConst;
	private float memProp;
	private IMethod iMethod;
	private double valueInit;
	private float timeMC;
	private double result;
	
	public String getProp() {
		return prop;
	}
	public void setProp(String prop) {
		this.prop = prop;
	}
	public String getmConst() {
		return mConst;
	}
	public void setmConst(String mConst) {
		this.mConst = mConst;
	}
	public String getpConst() {
		return pConst;
	}
	public void setpConst(String pConst) {
		this.pConst = pConst;
	}
	public float getMemProp() {
		return memProp;
	}
	public void setMemProp(float memProp) {
		this.memProp = memProp;
	}
	public IMethod getiMethod() {
		return iMethod;
	}
	public void setiMethod(IMethod iMethod) {
		this.iMethod = iMethod;
	}
	public double getValueInit() {
		return valueInit;
	}
	public void setValueInit(double valueInit) {
		this.valueInit = valueInit;
	}
	public float getTimeMC() {
		return timeMC;
	}
	public void setTimeMC(float timeMC) {
		this.timeMC = timeMC;
	}
	public double getResult() {
		return result;
	}
	public void setResult(double result) {
		this.result = result;
	}
	
	public void printProperty(BufferedWriter bw){
		try{			
			bw.write(this.getMemProp()+";");
			bw.write(this.getiMethod().getIterations()+";");
			bw.write(this.getiMethod().getTime()+";");
			bw.write(this.getValueInit()+";");
			bw.write(this.getTimeMC()+";");
			bw.write(this.getResult()+";\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//IMETHOD
	public class IMethod {
		
		private int iterations;
		private float time;
		private double average;
		private double setup;
		private String type;
		
		public IMethod(){
			
		}
		
		public float getTime() {
			return time;
		}
		public void setTime(float time) {
			this.time = time;
		}
		public double getAverage() {
			return average;
		}
		public void setAverage(double average) {
			this.average = average;
		}
		public double getSetup() {
			return setup;
		}
		public void setSetup(double setup) {
			this.setup = setup;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getIterations() {
			return iterations;
		}

		public void setIterations(int iterations) {
			this.iterations = iterations;
		}
		
		public IMethod setIMethodIte(String[] tokens){
			this.setType(tokens[0]);
			this.setIterations(Integer.parseInt(tokens[2]));
			this.setTime(Float.parseFloat(tokens[5]));
			this.setAverage(Double.parseDouble(tokens[8].substring(0, tokens[8].length()-1)));
			this.setSetup(Double.parseDouble(tokens[10].substring(0, tokens[10].length()-1)));
			return this;
		}
		
		public IMethod setIMethodIja(String[] tokens){
			this.setType(tokens[0].substring(0, tokens[0].length()-1));
			this.setIterations(Integer.parseInt(tokens[1]));
			this.setTime(Float.parseFloat(tokens[4]));
			this.setAverage(Double.parseDouble(tokens[7].substring(0, tokens[7].length()-1)));
			this.setSetup(Double.parseDouble(tokens[9].substring(0, tokens[9].length()-1)));
			return this;
		}

	}
}
