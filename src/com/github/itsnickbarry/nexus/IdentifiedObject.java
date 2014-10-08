package com.github.itsnickbarry.nexus;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class IdentifiedObject {
	
	public static AtomicInteger currentId = new AtomicInteger();
	
	private final int id;
	
	public IdentifiedObject() {
		this.id = currentId.incrementAndGet();
	}
	
	public int getId() {
		return this.id;
	}

}
