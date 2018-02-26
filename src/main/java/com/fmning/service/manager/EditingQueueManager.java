package com.fmning.service.manager;

import com.fmning.service.domain.EditingQueue;
import com.fmning.util.EditingQueueType;

public interface EditingQueueManager {
	
	/**
	 * Get editing queue by type and mapping id.
	 * If nothing exist, create a row and return null
	 * If there is something for this mapping, update to the new one and return the existing one
	 * @param type the type
	 * @param typeMappingId mapping id for type
	 * @param ownerId owner
	 * @return mentioned above
	 */
	public EditingQueue updateQueue(EditingQueueType type, int typeMappingId, int ownerId);

}
