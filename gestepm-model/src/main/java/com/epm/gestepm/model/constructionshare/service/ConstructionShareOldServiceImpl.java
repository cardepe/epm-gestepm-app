package com.epm.gestepm.model.constructionshare.service;

import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.constructionshare.dao.ConstructionShareRepository;
import com.epm.gestepm.model.constructionsharefile.dao.ConstructionShareFileRepository;
import com.epm.gestepm.model.interventionshare.service.mapper.ShareMapper;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.constructionshare.service.ConstructionShareOldService;
import com.epm.gestepm.modelapi.constructionsharefile.dto.ConstructionShareFile;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
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
public class ConstructionShareOldServiceImpl implements ConstructionShareOldService {
	
	private static final Log log = LogFactory.getLog(ConstructionShareOldServiceImpl.class);

	private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";
	
	@Autowired
	private ConstructionShareRepository constructionShareDao;
	
	@Autowired
	private ConstructionShareFileRepository constructionShareFileDao;

	@Autowired
	private MessageSource messageSource;
	
	@Override
	public ConstructionShare getConstructionShareById(Long id) {
		return constructionShareDao.findById(id).orElse(null);
	}
	
	@Override
	public ConstructionShare save(ConstructionShare constructionShare) {	
		return constructionShareDao.save(constructionShare);
	}
	
	@Override
	public ConstructionShare create(ConstructionShare constructionShare, List<MultipartFile> files) {	
		
		ConstructionShare saved = constructionShareDao.save(constructionShare);
		
		if (CollectionUtils.isNotEmpty(files)) {
			files.forEach((file) -> {
				ConstructionShareFile constructionShareFile = ShareMapper.mapMultipartFileToConstructionShareFile(file, saved);
				constructionShareFileDao.save(constructionShareFile);
			});
		}
		
		return saved;
	}
	
	@Override
	public void deleteById(Long shareId) {
		constructionShareDao.deleteById(shareId);
	}

	@Override
	public List<ShareTableDTO> getShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress) {
		return constructionShareDao.findShareTableByActivityCenterId(id, activityCenterId, projectId, progress);
	}
	
	@Override
	public List<ShareTableDTO> getShareTableByProjectId(Long projectId) {
		return constructionShareDao.findShareTableByProjectId(projectId);
	}

	@Override
	public List<ConstructionShare> getWeekSigningsByProjectId(LocalDateTime startDate, LocalDateTime endDate, Long projectId) {
		return constructionShareDao.findWeekSigningsByProjectId(startDate, endDate, projectId);
	}
	
	@Override
	public byte[] generateConstructionSharePdf(ConstructionShare share, Locale locale) {
	
		try {
			
			PdfReader pdfTemplate = new PdfReader("/templates/pdf/construction_share_" + locale.getLanguage() + ".pdf");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfStamper stamper = new PdfStamper(pdfTemplate, (OutputStream) baos);
	        
	        stamper.getAcroFields().setField("idShare", share.getId().toString());
	        stamper.getAcroFields().setField("startDate", Utiles.transform(share.getStartDate(), DATE_FORMAT));
	        stamper.getAcroFields().setField("endDate", Utiles.transform(share.getEndDate(), DATE_FORMAT));
	        stamper.getAcroFields().setField("observations", share.getObservations());
	        stamper.getAcroFields().setField("opName", share.getUser().getName() + " " + share.getUser().getSurnames());

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
	        
	        if (!share.getConstructionShareFiles().isEmpty()) {
	        	
	        	int pageNumber = pdfTemplate.getNumberOfPages();
	        	
	        	float pageWidth = pdfTemplate.getPageSize(pageNumber).getWidth();
	        	float pageHeight = pdfTemplate.getPageSize(pageNumber).getHeight();
	        	
	        	float topMargin = 40;
	        	float leftMargin = 50;

		        for (ConstructionShareFile constructionFile : share.getConstructionShareFiles()) {
		        	
		        	if (constructionFile.getContent() != null) {
		        	
			        	stamper.insertPage(++pageNumber, pdfTemplate.getPageSizeWithRotation(1));
			        	
			        	Image image = Image.getInstance(FileUtils.decompressBytes(constructionFile.getContent()));
			        	
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

		final List<ConstructionShare> shares = this.getWeekSigningsByProjectId(startDate, endDate, projectId);

		for (ConstructionShare share : shares) {

			final byte[] pdf = generateConstructionSharePdf(share, Locale.getDefault());

			if (pdf == null) {
				log.error("Error al generar el fichero pdf del parte de construccion " + share.getId());
				continue;
			}

			final String fileName = messageSource.getMessage("shares.construction.pdf.name", new Object[]{share.getId().toString(), Utiles.getDateFormatted(share.getStartDate())}, Locale.getDefault()) + ".pdf";

			final PdfFileDTO pdfFileDTO = new PdfFileDTO();
			pdfFileDTO.setDocumentBytes(pdf);
			pdfFileDTO.setFileName(fileName);

			pdfs.add(pdfFileDTO);
		}

		return pdfs;
	}
}
