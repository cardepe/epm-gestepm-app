package com.epm.gestepm.model.shares.construction.service;

import com.epm.gestepm.lib.locale.LocaleProvider;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.model.common.pdf.ImageUtils;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.inspection.exception.InspectionExportException;
import com.epm.gestepm.modelapi.inspection.exception.InspectionNotEndedException;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareDto;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareFileDto;
import com.epm.gestepm.modelapi.shares.construction.dto.finder.ConstructionShareFileByIdFinderDto;
import com.epm.gestepm.modelapi.shares.construction.exception.ConstructionShareExportException;
import com.epm.gestepm.modelapi.shares.construction.exception.ConstructionShareNotEndedException;
import com.epm.gestepm.modelapi.shares.construction.service.ConstructionShareExportService;
import com.epm.gestepm.modelapi.shares.construction.service.ConstructionShareFileService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;

@Service
@Validated
@AllArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class ConstructionShareExportServiceImpl implements ConstructionShareExportService {

    private static final String TEMPLATE_PATH = "/templates/pdf/construction_share_%s.pdf";
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    private static final String EMPTY_DATA_URI = "data:,";
    private static final float IMAGE_COMPRESSION_QUALITY = 0.5f;

    private final ConstructionShareFileService constructionShareFileService;
    private final LocaleProvider localeProvider;
    private final UserService userService;

    @Override
    public byte[] generate(final ConstructionShareDto constructionShare) {
        this.validateConstructionShare(constructionShare);

        PdfReader pdfTemplate = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            pdfTemplate = loadPdfTemplate();

            final PdfStamper stamper = new PdfStamper(pdfTemplate, baos);
            final User user = userService.getUserById(constructionShare.getUserId().longValue());

            this.populateFields(stamper.getAcroFields(), constructionShare, user);
            this.addOperatorSignatureIfPresent(stamper, constructionShare);
            this.addImagesIfPresent(stamper, pdfTemplate, constructionShare);

            stamper.getAcroFields().setGenerateAppearances(true);
            stamper.setFormFlattening(true);
            stamper.close();

            return baos.toByteArray();
        } catch (IOException | DocumentException ex) {
            throw new ConstructionShareExportException(constructionShare.getId());
        } finally {
            if (pdfTemplate != null) {
                pdfTemplate.close();
            }
        }
    }

    private void validateConstructionShare(ConstructionShareDto dto) {
        if (dto.getEndDate() == null) {
            throw new ConstructionShareNotEndedException(dto.getId());
        }
    }

    private PdfReader loadPdfTemplate() throws IOException {
        final String language = localeProvider.getLocale().orElse("es");
        return new PdfReader(String.format(TEMPLATE_PATH, language));
    }

    private void populateFields(AcroFields fields, ConstructionShareDto dto, User user) throws IOException, DocumentException {
        fields.setField("idShare", dto.getId().toString());
        fields.setField("startDate", Utiles.transform(dto.getStartDate(), DATE_FORMAT));
        fields.setField("endDate", Utiles.transform(dto.getEndDate(), DATE_FORMAT));
        fields.setField("observations", dto.getObservations());
        fields.setField("opName", user.getFullName());
    }

    private void addOperatorSignatureIfPresent(PdfStamper stamper, ConstructionShareDto dto) throws IOException, DocumentException {
        final String signature = dto.getOperatorSignature();
        if (isValidSignature(signature)) {
            this.printSignature(stamper, signature, "opSign");
        }
    }

    private boolean isValidSignature(String signature) {
        return StringUtils.isNotBlank(signature) && !EMPTY_DATA_URI.equals(signature);
    }

    private void printSignature(PdfStamper stamper, String signature, String fieldName) throws IOException, DocumentException {
        final Rectangle signFieldRect = stamper.getAcroFields().getFieldPositions(fieldName).get(0).position;
        final Image signImage = Image.getInstance(Utiles.base64PngToByteArray(signature));

        signImage.scaleAbsolute(signFieldRect.getWidth(), signFieldRect.getHeight());
        signImage.setAbsolutePosition(signFieldRect.getLeft(), signFieldRect.getBottom());

        stamper.getAcroFields().removeField(fieldName);
        stamper.getOverContent(1).addImage(signImage);
    }

    private void addImagesIfPresent(PdfStamper stamper, PdfReader reader, ConstructionShareDto dto) throws IOException, DocumentException {
        if (dto.getFileIds().isEmpty()) return;

        int pageNumber = reader.getNumberOfPages();
        final Rectangle pageSize = reader.getPageSize(pageNumber);
        final float pageWidth = pageSize.getWidth();
        final float pageHeight = pageSize.getHeight();

        for (Integer fileId : dto.getFileIds()) {
            final ConstructionShareFileDto file = constructionShareFileService.findOrNotFound(new ConstructionShareFileByIdFinderDto(fileId));
            if (StringUtils.isNotBlank(file.getContent())) {
                stamper.insertPage(++pageNumber, pageSize);

                final byte[] imageBytes = Base64.getDecoder().decode(file.getContent());
                final byte[] compressedBytes = ImageUtils.compressImage(imageBytes, IMAGE_COMPRESSION_QUALITY);
                final Image image = Image.getInstance(compressedBytes);

                image.scaleToFit(pageWidth, pageHeight);
                final float x = (pageWidth - image.getScaledWidth()) / 2;
                final float y = (pageHeight - image.getScaledHeight()) / 2;
                image.setAbsolutePosition(x, y);

                stamper.getOverContent(pageNumber).addImage(image);
            }
        }
    }
}
