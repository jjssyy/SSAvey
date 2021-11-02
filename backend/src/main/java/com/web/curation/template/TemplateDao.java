package com.web.curation.template;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemplateDao extends MongoRepository<Template, String> {
}
