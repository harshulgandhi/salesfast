package com.stm.salesfast.constant;

public enum PhysicianStatus {
	prescribing("PRESCRIBING"),
	prospecting("PROSPECTING"),
	lost("LOST");
	
	private final String stringValue;
	
	private PhysicianStatus(final String s){ this.stringValue = s;}
	
	@Override
	public String toString(){
		return stringValue;
	}
}
