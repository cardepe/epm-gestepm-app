package com.epm.gestepm.modelapi.personalsigning.service;

import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;

public interface PersonalSigningService {
	
	PersonalSigning save(PersonalSigning personalSigning);
	PersonalSigning getById(Long id);
}
