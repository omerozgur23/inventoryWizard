package com.tobeto.core.utilities.logging;

import java.time.LocalDateTime;
import java.util.UUID;

public interface LoggingService {

	void logCreate(UUID entityId, String entityName, String createdBy, LocalDateTime createdDate);

	void logUpdate(UUID entityId, String entityName, String updatedBy, LocalDateTime updatedDate, String updateDetails,
			boolean success);

	void logSoftDelete(UUID entityId, String entityName, String deletedBy);
}
