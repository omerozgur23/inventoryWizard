package com.tobeto.core.utilities.logging;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoggingManager implements LoggingService {

	@Override
	public void logCreate(UUID entityId, String entityName, String createdBy, LocalDateTime createdDate) {
		log.info("Create operation for {} ID: {}, Created by: {}, Created date: {}", entityName, entityId, createdBy,
				createdDate);
	}

//	@Override
//	public void logUpdate(UUID entityId, String entityName, String updatedBy, LocalDateTime updatedDate,
//			String updateDetails) {
//		log.info("Update operation for {} ID: {}, Updated by: {}, Updated date: {}, Update details: {}", entityName,
//				entityId, updatedBy, updatedDate, updateDetails);
//	}
	@Override
	public void logUpdate(UUID entityId, String entityName, String updatedBy, LocalDateTime updatedDate,
			String updateDetails, boolean success) {
		log.info("Update operation for {} ID: {}, Updated by: {}, Updated date: {}, Update details: {}, Success: {}",
				entityName, entityId, updatedBy, updatedDate, updateDetails, success);
	}

	@Override
	public void logSoftDelete(UUID entityId, String entityName, String deletedBy) {
		log.info("Soft delete executed for {} ID: {}, Deleted by: {}, Delete date: {}", entityName, entityId, deletedBy,
				LocalDateTime.now());
	}
}
