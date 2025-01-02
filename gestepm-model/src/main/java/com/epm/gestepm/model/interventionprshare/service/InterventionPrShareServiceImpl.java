package com.epm.gestepm.model.interventionprshare.service;

import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.interventionprshare.dao.InterventionPrShareRepository;
import com.epm.gestepm.model.interventionprsharefile.dao.InterventionPrShareFileRepository;
import com.epm.gestepm.model.interventionshare.service.mapper.ShareMapper;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShareFile;
import com.epm.gestepm.modelapi.interventionprshare.service.InterventionPrShareService;
import com.epm.gestepm.modelapi.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class InterventionPrShareServiceImpl implements InterventionPrShareService {

	private static final Log log = LogFactory.getLog(InterventionPrShareServiceImpl.class);

	private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";
	
	@Autowired
	private InterventionPrShareRepository interventionPrShareDao;
	
	@Autowired
	private InterventionPrShareFileRepository interventionPrShareFileDao;

	@Autowired
	private MessageSource messageSource;

	@Override
	public InterventionPrShare getInterventionPrShareById(Long id) {
		return interventionPrShareDao.findById(id).orElse(null);
	}
	
	@Override
	public InterventionPrShare save(InterventionPrShare interventionPrShare) {	
		return interventionPrShareDao.save(interventionPrShare);
	}
	
	@Override
	public InterventionPrShare create(InterventionPrShare interventionPrShare, List<MultipartFile> files) {	

		InterventionPrShare saved = interventionPrShareDao.save(interventionPrShare);
		
		if (files != null && CollectionUtils.isNotEmpty(files)) {
			files.forEach((file) -> {
				InterventionPrShareFile interventionPrShareFile = ShareMapper.mapMultipartFileToInterventionPrShareFile(file, saved);
				interventionPrShareFileDao.save(interventionPrShareFile);
			});
		}
		
		return saved;
	}
	
	@Override
	public void deleteById(Long shareId) {
		interventionPrShareDao.deleteById(shareId);
	}

	@Override
	public List<ShareTableDTO> getShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress) {
		return interventionPrShareDao.findShareTableByActivityCenterId(id, activityCenterId, projectId, progress);
	}
	
	@Override
	public List<ShareTableDTO> getShareTableByProjectId(Long projectId) {
		return interventionPrShareDao.findShareTableByProjectId(projectId);
	}
	
	@Override
	public List<ShareTableDTO> getShareTableByUserSigningId(Long userSigningId) {
		return interventionPrShareDao.findShareTableByUserSigningId(userSigningId);
	}
	
	@Override
	public List<InterventionPrShare> getWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId) {
		return interventionPrShareDao.findWeekSigningsByUserId(startDate, endDate, userId);
	}

	@Override
	public List<InterventionPrShare> getWeekSigningsByProjectId(LocalDateTime startDate, LocalDateTime endDate, Long projectId) {
		return interventionPrShareDao.findWeekSigningsByProjectId(startDate, endDate, projectId);
	}
	
	@Override
	public byte[] generateInterventionSharePdf(InterventionPrShare share, Locale locale) {
	
		try {
			
			PdfReader pdfTemplate = new PdfReader("/templates/pdf/intervention_share_programmed_" + locale.getLanguage() + ".pdf");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfStamper stamper = new PdfStamper(pdfTemplate, (OutputStream) baos);
	        
	        stamper.getAcroFields().setField("idShare", share.getId().toString());
			stamper.getAcroFields().setField("station", share.getProject().getName());
	        stamper.getAcroFields().setField("startDate", Utiles.transform(share.getStartDate(), DATE_FORMAT));
	        stamper.getAcroFields().setField("endDate", Utiles.transform(share.getEndDate(), DATE_FORMAT));

			if (share.getSecondTechnical() != null) {
				stamper.getAcroFields().setField("secondTechnical", share.getSecondTechnical().getName() + " " + share.getSecondTechnical().getSurnames());
			}

			stamper.getAcroFields().setField("observations", share.getObservations());

	        if (!StringUtils.isBlank(share.getSignature())) {
		        Rectangle signFieldRec = stamper.getAcroFields().getFieldPositions("clientSign").get(0).position;
		        Image sign = Image.getInstance(Utiles.base64PngToByteArray(share.getSignature()));
		        sign.scaleAbsoluteHeight(signFieldRec.getHeight());
		        sign.scaleAbsoluteWidth(signFieldRec.getWidth());
		        sign.scaleToFit(signFieldRec);
		        stamper.getAcroFields().removeField("clientSign");
		        sign.setAbsolutePosition(signFieldRec.getLeft(), signFieldRec.getBottom());
		        PdfContentByte canvas = stamper.getOverContent(1);
		        canvas.addImage(sign);
	        }
	        
	        if (!StringUtils.isBlank(share.getSignatureOp())) {
		        Rectangle signFieldRec = stamper.getAcroFields().getFieldPositions("opSign").get(0).position;
		        Image sign = Image.getInstance(Utiles.base64PngToByteArray(share.getSignatureOp()));
		        sign.scaleAbsoluteHeight(signFieldRec.getHeight());
		        sign.scaleAbsoluteWidth(signFieldRec.getWidth());
		        sign.scaleToFit(signFieldRec);
		        stamper.getAcroFields().removeField("opSign");
		        sign.setAbsolutePosition(signFieldRec.getLeft(), signFieldRec.getBottom());
		        PdfContentByte canvas = stamper.getOverContent(1);
		        canvas.addImage(sign);
	        }
	        
	        if (!share.getInterventionPrShareFiles().isEmpty()) {
	        	
	        	int pageNumber = pdfTemplate.getNumberOfPages();
	        	
	        	float pageWidth = pdfTemplate.getPageSize(pageNumber).getWidth();
	        	float pageHeight = pdfTemplate.getPageSize(pageNumber).getHeight();
	        	
	        	float topMargin = 40;
	        	float leftMargin = 50;

		        for (InterventionPrShareFile interventionFile : share.getInterventionPrShareFiles()) {
		        	
		        	if (interventionFile.getContent() != null) {
		        	
			        	stamper.insertPage(++pageNumber, pdfTemplate.getPageSizeWithRotation(1));
			        	
			        	Image image = Image.getInstance(FileUtils.decompressBytes(interventionFile.getContent()));
			        	
			        	Rectangle maxImageSize = new Rectangle(PageSize.A4.getWidth() - (leftMargin * 2), PageSize.A4.getHeight() - (topMargin * 2));
			        	
			        	if (image.getWidth() > pageWidth || image.getHeight() > pageHeight) {
			        		image.scaleToFit(maxImageSize);
			        	}

			        	image.setAbsolutePosition(leftMargin,  (PageSize.A4.getHeight() - image.getScaledHeight()) - topMargin);
			        	PdfContentByte canvas = stamper.getOverContent(pageNumber);
				        canvas.addImage(image);
		        	}
		        }
	        }
	        
	        stamper.getAcroFields().setGenerateAppearances(true);
	        stamper.setFormFlattening(true);
	        
	        stamper.close();
	        pdfTemplate.close();
	        
	        return baos.toByteArray();
	        
		} catch (IOException | DocumentException e) {
			log.error(e);
		}
        
		return null;
	}

	@Override
	public List<PdfFileDTO> generateSharesByProjectAndInterval(Long projectId, LocalDateTime startDate, LocalDateTime endDate) {

		final List<PdfFileDTO> pdfs = new ArrayList<>();

		final List<InterventionPrShare> shares = this.getWeekSigningsByProjectId(startDate, endDate, projectId);

		for (InterventionPrShare share : shares) {

			final byte[] pdf = generateInterventionSharePdf(share, Locale.getDefault());

			if (pdf == null) {
				log.error("Error al generar el fichero pdf del parte de mantenimiento programado " + share.getId());
				continue;
			}

			final String fileName = messageSource.getMessage("shares.programmed.pdf.name", new Object[]{share.getId().toString(), Utiles.getDateFormatted(share.getStartDate())}, Locale.getDefault()) + ".pdf";

			final PdfFileDTO pdfFileDTO = new PdfFileDTO();
			pdfFileDTO.setDocumentBytes(pdf);
			pdfFileDTO.setFileName(fileName);

			pdfs.add(pdfFileDTO);
		}

		return pdfs;
	}
}
