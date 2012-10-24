package com.fbraz.mchelper.prism;

import com.fbraz.mchelper.mch.Log;
import com.fbraz.mchelper.prism.PrismLogData.ModelMatrix;
import com.fbraz.mchelper.prism.PrismLogData.Reachab;

public class PrismLog extends Log {
	
	public PrismLog() {
		
	}

	//LOG
	private String typeModel;
	private float time;
	private long memory;
	private int states;
	private int initState;
	private int transitions;
	private int choices;
	private Reachab modelReach;
	private ModelMatrix matrix;
	private String commandLine;
	private boolean memProblem;
	
	public PrismLog(String typeModel, float time, long memory, int states,
			int initState, int transitions, int choices, Reachab modelReach,
			ModelMatrix matrix) {
		super();
		this.typeModel = typeModel;
		this.time = time;
		this.memory = memory;
		this.states = states;
		this.initState = initState;
		this.transitions = transitions;
		this.choices = choices;
		this.modelReach = modelReach;
		this.matrix = matrix;
	}
	

	public String getTypeModel() {
		return typeModel;
	}
	public void setTypeModel(String typeModel) {
		this.typeModel = typeModel;
	}
	public float getTime() {
		return time;
	}
	public void setTime(float time) {
		this.time = time;
	}
	public int getStates() {
		return states;
	}
	public void setStates(int states) {
		this.states = states;
	}
	public int getInitState() {
		return initState;
	}
	public void setInitState(int initState) {
		this.initState = initState;
	}
	public int getTransitions() {
		return transitions;
	}
	public void setTransitions(int transitions) {
		this.transitions = transitions;
	}
	public int getChoices() {
		return choices;
	}

	public void setChoices(int choices) {
		this.choices = choices;
	}

	public Reachab getModelReach() {
		return modelReach;
	}
	public void setModelReach(Reachab modelReach) {
		this.modelReach = modelReach;
	}
	public ModelMatrix getMatrix() {
		return matrix;
	}
	public void setMatrix(ModelMatrix matrix) {
		this.matrix = matrix;
	}
	public long getMemory() {
		return memory;
	}
	public void setMemory(long memory) {
		this.memory = memory;
	}

	public String getCommandLine() {
		return commandLine;
	}

	public void setCommandLine(String commandLine) {
		this.commandLine = commandLine;
	}
	
	public boolean isMemProblem() {
		return memProblem;
	}

	public void setMemProblem(boolean memProblem) {
		this.memProblem = memProblem;
	}

	public void printModelProperty()
	{
		System.out.println(this.getTypeModel());
		System.out.println();
		System.out.println(this.modelReach.getIterations() +" "+ this.modelReach.getTime() +
				" " + this.modelReach.getAverage() +" "+ this.modelReach.getSetup());
		System.out.println(this.getTime());
		System.out.println(this.getStates());
		System.out.println(this.getTransitions());
		System.out.println(this.matrix.getType() +" "+ this.matrix.getNodes() +" "+ 
		this.matrix.getTerminals() +" "+ this.matrix.getMinterms()  +" "+ this.matrix.getVars());
		System.out.println();

	}

}
