package com.epm.gestepm.forum.model.dao;

import com.epm.gestepm.forum.model.api.dto.Attachment;
import org.springframework.data.repository.CrudRepository;

public interface AttachmentRepository extends CrudRepository<Attachment, Long>, AttachmentRepositoryCustom {

}
