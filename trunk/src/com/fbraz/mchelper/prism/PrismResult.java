package com.fbraz.mchelper.prism;

import java.util.ArrayList;

public class PrismResult {
	private ArrayList<String> atribs;
	
	public PrismResult() {
		setAtribs(new ArrayList<String>());
	}
	
	public PrismResult(String atrib) {
		this();
		if (atrib != null && !atrib.equals("")) {
			String[] toks = atrib.split(" ");
			for (int i = 0; i < toks.length; i++) {
				atribs.add(toks[i]);
			}
		}
	}

	public ArrayList<String> getAtribs() {
		return atribs;
	}

	public void setAtribs(ArrayList<String> atribs) {
		this.atribs = atribs;
	}
	
	public void addAtrib(String atrib) {
		this.atribs.add(atrib);
	}
	
}
