package com.fbraz.mchelper.mch;

import java.util.ArrayList;

import com.fbraz.mchelper.prism.PrismResult;

public abstract class Property {
	
	private int propertyNumber;
	private String propertyCommand;
	private String propertyName;
	private ArrayList<String> propertyHeaders;
	private ArrayList<PrismResult> propertyResults;
	
	public Property() {
		setPropertyHeaders(new ArrayList<String>());
		setPropertyResults(new ArrayList<PrismResult>());
	}

	public int getPropertyNumber() {
		return propertyNumber;
	}

	public void setPropertyNumber(int propertyNumber) {
		this.propertyNumber = propertyNumber;
	}

	public String getPropertyCommand() {
		return propertyCommand;
	}

	public void setPropertyCommand(String propertyCommand) {
		this.propertyCommand = propertyCommand;
		this.propertyName = propertyCommand.substring(0, propertyCommand.lastIndexOf("\""));
		this.propertyName = propertyName.substring(this.propertyName.lastIndexOf("\"")+1);
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public ArrayList<String> getPropertyHeaders() {
		return propertyHeaders;
	}

	public void setPropertyHeaders(ArrayList<String> propertyHeaders) {
		this.propertyHeaders = propertyHeaders;
	}
	
	public void setPropertyHeaders(String propertyHeaders) {
		if (propertyHeaders != null && !propertyHeaders.equals("")) {
			String[] toks = propertyHeaders.split(" ");
			for (int i = 0; i < toks.length; i++) {
				this.propertyHeaders.add(toks[i]);
			}
		}
	}	

	public ArrayList<PrismResult> getPropertyResults() {
		return propertyResults;
	}

	public void setPropertyResults(ArrayList<PrismResult> propertyResults) {
		this.propertyResults = propertyResults;
	}
	
	public void addPropertyResult(PrismResult result) {
		this.propertyResults.add(result);		
	}	

}
