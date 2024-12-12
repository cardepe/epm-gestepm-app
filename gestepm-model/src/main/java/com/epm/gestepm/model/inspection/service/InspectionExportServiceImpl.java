package com.epm.gestepm.model.inspection.service;

import com.epm.gestepm.lib.locale.LocaleProvider;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.family.dto.Family;
import com.epm.gestepm.modelapi.family.service.FamilyService;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.inspection.dto.InspectionFileDto;
import com.epm.gestepm.modelapi.inspection.dto.MaterialDto;
import com.epm.gestepm.modelapi.inspection.dto.finder.InspectionFileByIdFinderDto;
import com.epm.gestepm.modelapi.inspection.exception.InspectionExportException;
import com.epm.gestepm.modelapi.inspection.service.InspectionExportService;
import com.epm.gestepm.modelapi.inspection.service.InspectionFileService;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.service.NoProgrammedShareService;
import com.epm.gestepm.modelapi.subfamily.dto.SubFamily;
import com.epm.gestepm.modelapi.subfamily.service.SubFamilyService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;

@Service
@Validated
@EnableExecutionLog(layerMarker = SERVICE)
public class InspectionExportServiceImpl implements InspectionExportService {

    private static final String TEMPLATE_PATH = "/templates/pdf/intervention_share_no_programmed_%s.pdf";

    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";

    private final FamilyService familyService;

    private final InspectionFileService inspectionFileService;
    
    private final LocaleProvider localeProvider;

    private final MessageSource messageSource;

    private final NoProgrammedShareService noProgrammedShareService;

    private final ProjectService projectService;
    
    private final SubFamilyService subFamilyService;
    
    private final UserService userService;

    public InspectionExportServiceImpl(FamilyService familyService, InspectionFileService inspectionFileService, LocaleProvider localeProvider, MessageSource messageSource, NoProgrammedShareService noProgrammedShareService, ProjectService projectService, SubFamilyService subFamilyService, UserService userService) {
        this.familyService = familyService;
        this.inspectionFileService = inspectionFileService;
        this.localeProvider = localeProvider;
        this.messageSource = messageSource;
        this.noProgrammedShareService = noProgrammedShareService;
        this.projectService = projectService;
        this.subFamilyService = subFamilyService;
        this.userService = userService;
    }

    @Override
    public byte[] generate(InspectionDto inspection) {
        try {
            final NoProgrammedShareDto noProgrammedShare = this.noProgrammedShareService.findOrNotFound(
                    new NoProgrammedShareByIdFinderDto(inspection.getShareId()));
            final Project project = this.projectService.getProjectById(noProgrammedShare.getProjectId().longValue());
            final User firstTechnical = this.userService.getUserById(inspection.getFirstTechnicalId().longValue());

            final String language = localeProvider.getLocale().orElse("es");
            final Locale locale = new Locale(language);
            final PdfReader pdfTemplate = new PdfReader(String.format(TEMPLATE_PATH, language));
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final PdfStamper stamper = new PdfStamper(pdfTemplate, baos);
            final AcroFields acroFields = stamper.getAcroFields();

            acroFields.setField("faultNum", noProgrammedShare.getId().toString());
            acroFields.setField("station", project.getName());
            acroFields.setField("faultDate", Utiles.transform(noProgrammedShare.getStartDate(), DATE_FORMAT));
            acroFields.setField("faultDescription", noProgrammedShare.getDescription().replaceAll("( *\n *){2,}", "\n"));
            acroFields.setField("interventionText", this.messageSource.getMessage("inspection.type." + inspection.getAction().name().toLowerCase(), null, locale));
            acroFields.setField("interventionNum", inspection.getInspectionTypeNumber(noProgrammedShare.getInspectionIds()).toString());
            acroFields.setField("interventionStartDate", Utiles.transform(inspection.getStartDate(), DATE_FORMAT));
            acroFields.setField("interventionEndDate", Utiles.transform(inspection.getEndDate(), DATE_FORMAT));
            acroFields.setField("technic1", firstTechnical.getFullName());
            
            if (inspection.getSecondTechnicalId() != null) {
                final User secondTechnical = this.userService.getUserById(inspection.getSecondTechnicalId().longValue());
                acroFields.setField("technic2", secondTechnical.getFullName());
            }

            if (noProgrammedShare.getFamilyId() != null) {
                final Family family = this.familyService.getById(noProgrammedShare.getFamilyId().longValue());
                acroFields.setField("family", ("es".equals(language) ? family.getNameES() : family.getNameFR()));
                acroFields.setField("brand", family.getBrand());
                acroFields.setField("model", family.getModel());
                acroFields.setField("enrollment", family.getEnrollment());
            }

            if (noProgrammedShare.getSubFamilyId() != null) {
                final SubFamily subFamily = this.subFamilyService.getById(noProgrammedShare.getSubFamilyId().longValue());
                acroFields.setField("subFamily", ("es".equals(language) ? subFamily.getNameES() : subFamily.getNameFR()));
            }

            acroFields.setField("interventionDesc", inspection.getDescription().replaceAll("( *\n *){2,}", "\n"));
            acroFields.setField("clientName", inspection.getClientName());
            acroFields.setField("equipmentHours", inspection.getEquipmentHours() != null ? inspection.getEquipmentHours().toString() : "");
            acroFields.setField("excelNum", "EXCEL: " + (inspection.getMaterialsFile() != null ? noProgrammedShare.getId() : "S/N"));

            final List<MaterialDto> materials = inspection.getMaterials().stream().limit(5).collect(Collectors.toList());

            for (int i = 0; i < materials.size(); i++) {
                acroFields.setField("materialDesc" + i, materials.get(i).getDescription());
                acroFields.setField("materialRef" + i, materials.get(i).getReference());
                acroFields.setField("materialAmount" + i, materials.get(i).getUnits().toString());
            }

            if (!StringUtils.isBlank(inspection.getSignature()) && !"data:,".equals(inspection.getSignature())) {
                printSignature(stamper, inspection.getSignature(), "clientSign");
            }

            if (!StringUtils.isBlank(inspection.getOperatorSignature()) && !"data:,".equals(inspection.getOperatorSignature())) {
                printSignature(stamper, inspection.getOperatorSignature(), "opSign");
            }

            if (!inspection.getFileIds().isEmpty()) {
                this.printImages(stamper, pdfTemplate, inspection);
            }

            acroFields.setGenerateAppearances(true);
            stamper.setFormFlattening(true);

            stamper.close();
            pdfTemplate.close();

            return baos.toByteArray();
        } catch (Exception ex) {
            throw new InspectionExportException(inspection.getId());
        }
    }

    private void printSignature(final PdfStamper stamper, final String signature, final String fieldName) throws DocumentException, IOException {
        final Rectangle signFieldRec = stamper.getAcroFields().getFieldPositions(fieldName).get(0).position;
        final Image sign = Image.getInstance(Utiles.base64PngToByteArray(signature));

        sign.scaleAbsoluteHeight(signFieldRec.getHeight());
        sign.scaleAbsoluteWidth(signFieldRec.getWidth());
        sign.scaleToFit(signFieldRec);
        stamper.getAcroFields().removeField(fieldName);
        sign.setAbsolutePosition(signFieldRec.getLeft(), signFieldRec.getBottom());

        final PdfContentByte canvas = stamper.getOverContent(1);
        canvas.addImage(sign);
    }

    private void printImages(final PdfStamper stamper, final PdfReader pdfTemplate, final InspectionDto inspection) throws DocumentException, IOException {
        int pageNumber = pdfTemplate.getNumberOfPages();

        final float pageWidth = pdfTemplate.getPageSize(pageNumber).getWidth();
        final float pageHeight = pdfTemplate.getPageSize(pageNumber).getHeight();

        final float topMargin = 40;
        final float leftMargin = 50;

        for (Integer id : inspection.getFileIds()) {

            final InspectionFileDto file = this.inspectionFileService.findOrNotFound(new InspectionFileByIdFinderDto(id));

            if (StringUtils.isNoneBlank(file.getContent())) {

                stamper.insertPage(++pageNumber, pdfTemplate.getPageSizeWithRotation(1));

                final Image image = Image.getInstance(file.getContent());
                final Rectangle maxImageSize = new Rectangle(PageSize.A4.getWidth() - (leftMargin * 2), PageSize.A4.getHeight() - (topMargin * 2));

                if (image.getWidth() > pageWidth || image.getHeight() > pageHeight) {
                    image.scaleToFit(maxImageSize);
                }

                image.setAbsolutePosition(leftMargin,  (PageSize.A4.getHeight() - image.getScaledHeight()) - topMargin);

                final PdfContentByte canvas = stamper.getOverContent(pageNumber);
                canvas.addImage(image);
            }
        }
    }
}
