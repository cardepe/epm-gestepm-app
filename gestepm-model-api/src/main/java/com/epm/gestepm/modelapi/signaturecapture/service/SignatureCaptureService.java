package com.epm.gestepm.modelapi.signaturecapture.service;

import com.epm.gestepm.modelapi.signaturecapture.dto.SignatureRecord;

public interface SignatureCaptureService {
	SignatureRecord save(SignatureRecord signatureRecord);
}