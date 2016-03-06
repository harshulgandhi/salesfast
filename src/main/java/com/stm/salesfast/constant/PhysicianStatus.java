package com.stm.salesfast.constant;

public enum PhysicianStatus {
	PRESCRIBING("PRESCRIBING"),
	PROSPECTING("PROSPECTING"),
	LOST("LOST");
	
	private final String stringValue;
	
	private PhysicianStatus(final String s){ this.stringValue = s;}
	
	@Override
	public String toString(){
		return stringValue;
	}
}

