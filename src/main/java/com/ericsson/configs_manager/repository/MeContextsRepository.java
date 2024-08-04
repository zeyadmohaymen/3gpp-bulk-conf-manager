package com.ericsson.configs_manager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ericsson.configs_manager.model.MeContext;

public interface MeContextsRepository extends MongoRepository<MeContext, String> {
    
}
