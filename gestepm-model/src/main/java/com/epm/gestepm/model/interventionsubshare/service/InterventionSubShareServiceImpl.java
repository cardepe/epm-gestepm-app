package com.epm.gestepm.model.interventionsubshare.service;

import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.interventionsubshare.dao.InterventionSubShareRepository;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionShareMaterial;
import com.epm.gestepm.modelapi.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;
import com.epm.gestepm.modelapi.interventionsubshare.service.InterventionSubShareService;
import com.epm.gestepm.modelapi.interventionsubsharefile.dto.InterventionSubShareFile;
import com.epm.gestepm.modelapi.materialrequired.dto.MaterialRequired;
import com.epm.gestepm.modelapi.user.dto.User;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Transactional
public class InterventionSubShareServiceImpl implements InterventionSubShareService {
	
	private static final Log log = LogFactory.getLog(InterventionSubShareServiceImpl.class);
	
	@Autowired
	private InterventionSubShareRepository interventionSubShareDao;

	@Autowired
	private MessageSource messageSource;

	@Override
	public InterventionSubShare getById(Long id) {
		return interventionSubShareDao.findById(id).orElse(null);
	}
	
	@Override
	public InterventionSubShare getByShareAndOrder(Long shareId, Long interventionId) {
		return interventionSubShareDao.findByShareAndOrder(shareId, interventionId);
	}

	@Override
	public InterventionSubShare save(InterventionSubShare interventionSubShare) {	
		return interventionSubShareDao.save(interventionSubShare);
	}
	
	@Override
	public InterventionSubShare getOpenIntervention(Long shareId) {
		return interventionSubShareDao.findOpenIntervention(shareId);
	}
	
	@Override
	public byte[] generateInterventionSharePdf(InterventionSubShare subShare, Locale locale) {
	
		try {

			PdfReader pdfTemplate = new PdfReader("/templates/pdf/intervention_share_no_programmed_" + locale.getLanguage() + ".pdf");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        PdfStamper stamper = new PdfStamper(pdfTemplate, (OutputStream) baos);
	        
	        stamper.getAcroFields().setField("faultNum", subShare.getInterventionShare().getId().toString());
	        stamper.getAcroFields().setField("station", subShare.getInterventionShare().getProject().getName());  
	        stamper.getAcroFields().setField("faultDate", Utiles.getDateTimeFormatted(subShare.getInterventionShare().getNoticeDate()));
	        stamper.getAcroFields().setField("faultDescription", subShare.getInterventionShare().getDescription().replaceAll("( *\n *){2,}", "\n"));
			stamper.getAcroFields().setField("interventionText", parseActionToText(subShare.getAction(), locale));
			stamper.getAcroFields().setField("interventionNum", parseOrderIdToOrder(subShare.getOrderId().intValue()).toString());
	        stamper.getAcroFields().setField("interventionStartDate", Utiles.transformToString(subShare.getStartDate()));
			stamper.getAcroFields().setField("interventionEndDate", Utiles.transformToString(subShare.getEndDate()));
	        stamper.getAcroFields().setField("technic1", generateName(subShare.getFirstTechnical()));
	        
	        if (subShare.getSecondTechnical() != null) {
	        	stamper.getAcroFields().setField("technic2", generateName(subShare.getSecondTechnical()));
	        }
	        
	        if (subShare.getInterventionShare().getFamily() != null) {
		        stamper.getAcroFields().setField("family", ("es".equals(locale.getLanguage()) ? subShare.getInterventionShare().getFamily().getNameES() : subShare.getInterventionShare().getFamily().getNameFR()));
		        stamper.getAcroFields().setField("brand", subShare.getInterventionShare().getFamily().getBrand());
		        stamper.getAcroFields().setField("model", subShare.getInterventionShare().getFamily().getModel());
		        stamper.getAcroFields().setField("enrollment", subShare.getInterventionShare().getFamily().getEnrollment());
	        }
	        
	        if (subShare.getInterventionShare().getSubFamily() != null) {
	        	stamper.getAcroFields().setField("subFamily", ("es".equals(locale.getLanguage()) ? subShare.getInterventionShare().getSubFamily().getNameES() : subShare.getInterventionShare().getSubFamily().getNameFR()));
	        }
	        
	        stamper.getAcroFields().setField("interventionDesc", subShare.getDescription().replaceAll("( *\n *){2,}", "\n"));
	        stamper.getAcroFields().setField("clientName", subShare.getClientName());
			stamper.getAcroFields().setField("equipmentHours", subShare.getEquipmentHours() != null ? subShare.getEquipmentHours().toString() : "");

			if (subShare.getMaterialsFile() != null) {
				stamper.getAcroFields().setField("excelNum", "EXCEL: " + subShare.getInterventionShare().getId() + "/" + subShare.getOrderId());
			} else {
				stamper.getAcroFields().setField("excelNum", "EXCEL: S/N");
			}

			List<MaterialRequired> materialsRequired = subShare.getInterventionShare().getProject().getMaterialsRequired();

			int i = 1;

			for (InterventionShareMaterial material : subShare.getInterventionShareMaterials()) {

				stamper.getAcroFields().setField("materialDesc" + i, material.getDescription());
				stamper.getAcroFields().setField("materialRef" + i, material.getReference());
				stamper.getAcroFields().setField("materialAmount" + i, material.getUnits().toString());
				i++;

				// only 5 materials in pdf
				if (i > 5) {
					break;
				}
			}

	        if (!StringUtils.isBlank(subShare.getSignature()) && !"data:,".equals(subShare.getSignature())) {
		        Rectangle signFieldRec = stamper.getAcroFields().getFieldPositions("clientSign").get(0).position;
		        Image sign = Image.getInstance(Utiles.base64PngToByteArray(subShare.getSignature()));
		        sign.scaleAbsoluteHeight(signFieldRec.getHeight());
		        sign.scaleAbsoluteWidth(signFieldRec.getWidth());
		        sign.scaleToFit(signFieldRec);
		        stamper.getAcroFields().removeField("clientSign");
		        sign.setAbsolutePosition(signFieldRec.getLeft(), signFieldRec.getBottom());
		        PdfContentByte canvas = stamper.getOverContent(1);
		        canvas.addImage(sign);
	        }
	        
	        if (!StringUtils.isBlank(subShare.getSignatureOp()) && !"data:,".equals(subShare.getSignatureOp())) {
		        Rectangle signFieldRec = stamper.getAcroFields().getFieldPositions("opSign").get(0).position;
		        Image sign = Image.getInstance(Utiles.base64PngToByteArray(subShare.getSignatureOp()));
		        sign.scaleAbsoluteHeight(signFieldRec.getHeight());
		        sign.scaleAbsoluteWidth(signFieldRec.getWidth());
		        sign.scaleToFit(signFieldRec);
		        stamper.getAcroFields().removeField("opSign");
		        sign.setAbsolutePosition(signFieldRec.getLeft(), signFieldRec.getBottom());
		        PdfContentByte canvas = stamper.getOverContent(1);
		        canvas.addImage(sign);
	        }
	        
	        if (!subShare.getInterventionSubShareFiles().isEmpty()) {
	        	
	        	int pageNumber = pdfTemplate.getNumberOfPages();
	        	
	        	float pageWidth = pdfTemplate.getPageSize(pageNumber).getWidth();
	        	float pageHeight = pdfTemplate.getPageSize(pageNumber).getHeight();
	        	
	        	float topMargin = 40;
	        	float leftMargin = 50;

		        for (InterventionSubShareFile interventionFile : subShare.getInterventionSubShareFiles()) {
		        	
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
	public byte[] generateInterventionShareMaterialsPdf(InterventionSubShare subShare, Locale locale) {

		try {

			PdfReader pdfTemplate = new PdfReader("/templates/pdf/intervention_share_no_programmed_" + locale.getLanguage() + "_materials.pdf");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfStamper stamper = new PdfStamper(pdfTemplate, (OutputStream) baos);

			stamper.getAcroFields().setField("faultNum", subShare.getInterventionShare().getId().toString());
			stamper.getAcroFields().setField("interventionText", parseActionToText(subShare.getAction(), locale));
			stamper.getAcroFields().setField("interventionNum", parseOrderIdToOrder(subShare.getOrderId().intValue()).toString());

			if (subShare.getMaterialsFile() != null) {
				stamper.getAcroFields().setField("excelNum", "EXCEL: " + subShare.getInterventionShare().getId() + "/" + subShare.getOrderId());
			} else {
				stamper.getAcroFields().setField("excelNum", "EXCEL: S/N");
			}

			List<MaterialRequired> materialsRequired = subShare.getInterventionShare().getProject().getMaterialsRequired();

			int i = 1;

			if (!materialsRequired.isEmpty()) {

				materialsRequired = materialsRequired.stream().filter(mr -> mr.getRequired() == 1).collect(Collectors.toList());

				for (final MaterialRequired mr : materialsRequired) {

					if (locale.getLanguage().equals("fr")) {
						stamper.getAcroFields().setField("reqMaterialDesc" + i, mr.getNameFR());
					} else {
						stamper.getAcroFields().setField("reqMaterialDesc" + i, mr.getNameES());
					}

					stamper.getAcroFields().setField("reqMaterialRef" + i, "x");
					i++;

					if (i > 30) {
						break;
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
	public List<InterventionSubShare> getWeekSigningsByUserId(Date startDate, Date endDate, Long userId) {
		return interventionSubShareDao.findWeekSigningsByUserId(startDate, endDate, userId);
	}

	@Override
	public List<InterventionSubShare> getWeekSigningsByProjectId(Date startDate, Date endDate, Long projectId) {
		return interventionSubShareDao.findWeekSigningsByProjectId(startDate, endDate, projectId);
	}
	
	@Override
	public List<ShareTableDTO> getShareTableByProjectId(Long projectId) {
		return interventionSubShareDao.findShareTableByProjectId(projectId);
	}
	
	@Override
	public List<ShareTableDTO> getShareTableByUserSigningId(Long userSigningId) {
		return interventionSubShareDao.findShareTableByUserSigningId(userSigningId);
	}

	@Override
	public List<PdfFileDTO> generateSharesByProjectAndInterval(Long projectId, Date startDate, Date endDate) {

		final List<PdfFileDTO> pdfs = new ArrayList<>();

		final List<InterventionSubShare> shares = this.getWeekSigningsByProjectId(startDate, endDate, projectId);

		for (InterventionSubShare share : shares) {

			final byte[] pdf = generateInterventionSharePdf(share, Locale.getDefault());

			if (pdf == null) {
				log.error("Error al generar el fichero pdf del parte de mantenimiento no programado " + share.getId());
				continue;
			}

			final String fileName = messageSource.getMessage("shares.no.programmed.pdf.name", new Object[]{share.getId().toString(), Utiles.getDateFormatted(share.getStartDate())}, Locale.getDefault()) + ".pdf";

			final PdfFileDTO pdfFileDTO = new PdfFileDTO();
			pdfFileDTO.setDocumentBytes(pdf);
			pdfFileDTO.setFileName(fileName);

			pdfs.add(pdfFileDTO);
		}

		return pdfs;
	}

	private String parseActionToText(final Integer action, final Locale locale) {

		String type = "";

		switch (action)
		{
			case 1:
				type = messageSource.getMessage("shares.intervention.detail.inc", null, locale).toLowerCase();
				break;
			case 2:
				type = messageSource.getMessage("shares.intervention.detail.dia", null, locale).toLowerCase();
				break;
			case 3:
				type = messageSource.getMessage("shares.intervention.detail.tra", null, locale).toLowerCase();
				break;
		}

		return messageSource.getMessage("shares.intervention.detail.num.type", new Object[] { type }, locale);
	}

	private Integer parseOrderIdToOrder(final Integer orderId) {
		return ((orderId - 1) / 3) + 1;
	}

	private String generateName(final User technical) {

		String fullName = "";

		if (technical != null) {

			if (technical.getName() != null) {
				fullName += technical.getName();
			}

			if (technical.getSurnames() != null) {
				fullName += (fullName.isEmpty() ? "" : " ") + technical.getSurnames();
			}
		}

		return fullName;
	}
}
