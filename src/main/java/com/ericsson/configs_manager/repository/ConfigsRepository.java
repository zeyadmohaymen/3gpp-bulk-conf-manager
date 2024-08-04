package com.ericsson.configs_manager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ericsson.configs_manager.model.BulkCmConfigDataFile;

public interface ConfigsRepository extends MongoRepository<BulkCmConfigDataFile, String> {
    
}
