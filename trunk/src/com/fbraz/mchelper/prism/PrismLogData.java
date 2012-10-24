package com.fbraz.mchelper.prism;

public class PrismLogData {

	//MODELMATRIX
	public class ModelMatrix {
		
		private String type;
		private int nodes;
		private int terminals;
		private int minterms;
		private String vars;
		
		public ModelMatrix(){
			
		}
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public int getNodes() {
			return nodes;
		}
		public void setNodes(int nodes) {
			this.nodes = nodes;
		}
		public int getTerminals() {
			return terminals;
		}
		public void setTerminals(int terminals) {
			this.terminals = terminals;
		}
		public int getMinterms() {
			return minterms;
		}
		public void setMinterms(int minterms) {
			this.minterms = minterms;
		}
		public String getVars() {
			return vars;
		}
		public void setVars(String vars) {
			this.vars = vars;
		}
		
		public ModelMatrix setMMatrix(String[] tokens){
			this.setType(tokens[0]);
			this.setNodes(Integer.parseInt(tokens[2]));
			this.setTerminals(Integer.parseInt(tokens[4].substring(1)));
			this.setMinterms(Integer.parseInt(tokens[6]));
			this.setVars(tokens[9]);
			
			return this;
		}

	}
	
	//REACHAB
	public class Reachab {
		
		private int iterations;
		private float time;
		private float average;
		private float setup;
		
		public Reachab()
		{
			
		}
		public int getIterations() {
			return iterations;
		}
		public void setIterations(int iterations) {
			this.iterations = iterations;
		}
		public float getTime() {
			return time;
		}
		public void setTime(float time) {
			this.time = time;
		}
		public float getAverage() {
			return average;
		}
		public void setAverage(float average) {
			this.average = average;
		}
		public float getSetup() {
			return setup;
		}
		public void setSetup(float setup) {
			this.setup = setup;
		}
		
		public Reachab setReachab(String[] tokens)
		{
			this.setIterations(Integer.parseInt(tokens[1]));
			this.setTime(Float.parseFloat(tokens[4]));
			this.setAverage(Float.parseFloat(tokens[7].substring(0, tokens[7].length()-1))); 
			this.setSetup(Float.parseFloat(tokens[9].substring(0, tokens[9].length()-1)));
			return this;
		}

	}

}
