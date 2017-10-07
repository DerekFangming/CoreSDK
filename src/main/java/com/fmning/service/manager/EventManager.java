package com.fmning.service.manager;

import java.time.Instant;

public interface EventManager {
	
	/**
	 * Create an Event object and save into database
	 * @param mappingId
	 * @param title
	 * @param startTime
	 * @param endTime
	 * @param location
	 * @param ownerId
	 * @return the database id of the row
	 */
	public int createEvent(int mappingId, String title, Instant startTime, Instant endTime, String location,
			int ownerId);

}
