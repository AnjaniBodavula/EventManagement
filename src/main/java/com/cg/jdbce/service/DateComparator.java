package com.cg.jdbce.service;

import java.time.LocalDate;
import java.util.Comparator;
import com.cg.jdbce.model.Event;

public class DateComparator implements Comparator <Event>{
	
	@Override
	public int compare(Event e1, Event e2) {
			
			LocalDate date = e1.getStartDate();
			LocalDate otherDate = e2.getStartDate();
			return date.compareTo(otherDate);
		
	}

}
