package com.epm.gestepm.model.workshare.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.epm.gestepm.model.interventionshare.service.mapper.ShareMapper;
import com.epm.gestepm.model.worksharefile.dao.WorkShareFileRepository;
import com.epm.gestepm.model.workshare.dao.WorkShareRepository;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.workshare.dto.WorkShareTableDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import com.epm.gestepm.modelapi.workshare.dto.WorkShare;
import com.epm.gestepm.modelapi.worksharefile.dto.WorkShareFile;
import com.epm.gestepm.modelapi.workshare.service.WorkShareService;
import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;

@Service
@Transactional
public class WorkShareServiceImpl implements WorkShareService {

	private static final Log log = LogFactory.getLog(WorkShareServiceImpl.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private WorkShareRepository workShareDao;
	
	@Autowired
	private WorkShareFileRepository workShareFileDao;
	
	@Override
	public WorkShare getWorkShareById(Long id) {
		return workShareDao.findById(id).orElse(null);
	}
	
	@Override
	public WorkShare save(WorkShare workShare) {	
		return workShareDao.save(workShare);
	}
	
	@Override
	public WorkShare create(WorkShare workShare, List<MultipartFile> files) {	
		
		WorkShare saved = workShareDao.save(workShare);
		
		if (files != null && CollectionUtils.isNotEmpty(files)) {
			files.forEach((file) -> {
				WorkShareFile workShareFile = ShareMapper.mapMultipartFileToWorkShareFile(file, saved);
				workShareFileDao.save(workShareFile);
			});
		}
		
		return saved;
	}

	@Override
	public List<ShareTableDTO> getShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress) {
		return workShareDao.findShareTableByActivityCenterId(id, activityCenterId, projectId, progress);
	}

	@Override
	public List<ShareTableDTO> getShareTableByUserId(Long userId, Long projectId, Integer progress) {
		return workShareDao.findShareTableByUserId(userId, projectId, progress);
	}
	
	@Override
	public void deleteById(Long shareId) {
		workShareDao.deleteById(shareId);
	}

	@Override
	public List<ShareTableDTO> getShareTableByProjectId(Long projectId) {
		return workShareDao.findShareTableByProjectId(projectId);
	}
	
	@Override
	public List<ShareTableDTO> getShareTableByUserSigningId(Long userSigningId) {
		return workShareDao.findShareTableByUserSigningId(userSigningId);
	}
	
	@Override
	public Long getWorkSharesCountByUser(Long userId) {
		return workShareDao.findWorkSharesCountByUserId(userId);
	}
	
	@Override
	public List<WorkShareTableDTO> getWorkSharesByUserDataTables(Long userId, PaginationCriteria pagination) {
		return workShareDao.findWorkSharesByUserDataTables(userId, pagination);
	}
	
	@Override
	public List<WorkShare> getWeekSigningsByUserId(Date startDate, Date endDate, Long userId) {
		return workShareDao.findWeekSigningsByUserId(startDate, endDate, userId);
	}

	@Override
	public List<WorkShare> getWeekSigningsByProjectId(Date startDate, Date endDate, Long projectId) {
		return workShareDao.findWeekSigningsByProjectId(startDate, endDate, projectId);
	}

	@Override
	public List<ExpensesMonthDTO> getExpensesMonthDTOByProjectId(Long projectId, Integer year) {
		return workShareDao.findExpensesMonthDTOByProjectId(projectId, year);
	}
	
	@Override
	public byte[] generateWorkSharePdf(WorkShare share, Locale locale) {
	
		try {
			
			PdfReader pdfTemplate = new PdfReader("/templates/pdf/work_share_" + locale.getLanguage() + ".pdf");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfStamper stamper = new PdfStamper(pdfTemplate, (OutputStream) baos);
	        
	        stamper.getAcroFields().setField("idShare", share.getId().toString());
	        stamper.getAcroFields().setField("startDate", Utiles.transformFormattedDateToString(share.getStartDate()));
	        stamper.getAcroFields().setField("endDate", Utiles.transformFormattedDateToString(share.getEndDate()));
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
	        
	        if (!share.getWorkShareFiles().isEmpty()) {
	        	
	        	int pageNumber = pdfTemplate.getNumberOfPages();
	        	
	        	float pageWidth = pdfTemplate.getPageSize(pageNumber).getWidth();
	        	float pageHeight = pdfTemplate.getPageSize(pageNumber).getHeight();
	        	
	        	float topMargin = 40;
	        	float leftMargin = 50;

		        for (WorkShareFile interventionFile : share.getWorkShareFiles()) {
		        	
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
	public List<PdfFileDTO> generateSharesByProjectAndInterval(Long projectId, Date startDate, Date endDate) {

		final List<PdfFileDTO> pdfs = new ArrayList<>();

		final List<WorkShare> shares = this.getWeekSigningsByProjectId(startDate, endDate, projectId);

		for (WorkShare share : shares) {

			final byte[] pdf = generateWorkSharePdf(share, Locale.getDefault());

			if (pdf == null) {
				log.error("Error al generar el fichero pdf del parte de trabajo " + share.getId());
				continue;
			}

			final String fileName = messageSource.getMessage("shares.work.pdf.name", new Object[]{share.getId().toString(), Utiles.getDateFormatted(share.getStartDate())}, Locale.getDefault()) + ".pdf";

			final PdfFileDTO pdfFileDTO = new PdfFileDTO();
			pdfFileDTO.setDocumentBytes(pdf);
			pdfFileDTO.setFileName(fileName);

			pdfs.add(pdfFileDTO);
		}

		return pdfs;
	}
}
