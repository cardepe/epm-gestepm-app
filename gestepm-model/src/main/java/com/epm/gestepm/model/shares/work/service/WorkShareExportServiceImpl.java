package com.epm.gestepm.model.shares.work.service;

import com.epm.gestepm.lib.locale.LocaleProvider;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.model.common.pdf.ImageUtils;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.shares.work.dto.WorkShareDto;
import com.epm.gestepm.modelapi.shares.work.dto.WorkShareFileDto;
import com.epm.gestepm.modelapi.shares.work.dto.finder.WorkShareFileByIdFinderDto;
import com.epm.gestepm.modelapi.shares.work.exception.WorkShareExportException;
import com.epm.gestepm.modelapi.shares.work.exception.WorkShareNotEndedException;
import com.epm.gestepm.modelapi.shares.work.service.WorkShareExportService;
import com.epm.gestepm.modelapi.shares.work.service.WorkShareFileService;
import com.epm.gestepm.modelapi.userold.dto.User;
import com.epm.gestepm.modelapi.userold.service.UserServiceOld;
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
public class WorkShareExportServiceImpl implements WorkShareExportService {

    private static final String TEMPLATE_PATH = "/templates/pdf/work_share_%s.pdf";
    private static final String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    private static final String EMPTY_DATA_URI = "data:,";
    private static final float IMAGE_COMPRESSION_QUALITY = 0.5f;

    private final WorkShareFileService workShareFileService;
    private final LocaleProvider localeProvider;
    private final UserServiceOld userServiceOld;

    @Override
    public byte[] generate(final WorkShareDto workShare) {
        this.validateWorkShare(workShare);

        PdfReader pdfTemplate = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            pdfTemplate = loadPdfTemplate();

            final PdfStamper stamper = new PdfStamper(pdfTemplate, baos);
            final User user = userServiceOld.getUserById(workShare.getUserId().longValue());

            this.populateFields(stamper.getAcroFields(), workShare, user);
            this.addOperatorSignatureIfPresent(stamper, workShare);
            this.addImagesIfPresent(stamper, pdfTemplate, workShare);

            stamper.getAcroFields().setGenerateAppearances(true);
            stamper.setFormFlattening(true);
            stamper.close();

            return baos.toByteArray();
        } catch (IOException | DocumentException ex) {
            throw new WorkShareExportException(workShare.getId());
        } finally {
            if (pdfTemplate != null) {
                pdfTemplate.close();
            }
        }
    }

    private void validateWorkShare(WorkShareDto dto) {
        if (dto.getEndDate() == null) {
            throw new WorkShareNotEndedException(dto.getId());
        }
    }

    private PdfReader loadPdfTemplate() throws IOException {
        final String language = localeProvider.getLocale().orElse("es");
        return new PdfReader(String.format(TEMPLATE_PATH, language));
    }

    private void populateFields(AcroFields fields, WorkShareDto dto, User user) throws IOException, DocumentException {
        fields.setField("idShare", dto.getId().toString());
        fields.setField("startDate", Utiles.transform(dto.getStartDate(), DATE_FORMAT));
        fields.setField("endDate", Utiles.transform(dto.getEndDate(), DATE_FORMAT));
        fields.setField("observations", dto.getObservations());
        fields.setField("opName", user.getFullName());
    }

    private void addOperatorSignatureIfPresent(PdfStamper stamper, WorkShareDto dto) throws IOException, DocumentException {
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

    private void addImagesIfPresent(PdfStamper stamper, PdfReader reader, WorkShareDto dto) throws IOException, DocumentException {
        if (dto.getFileIds().isEmpty()) return;

        int pageNumber = reader.getNumberOfPages();
        final Rectangle pageSize = reader.getPageSize(pageNumber);
        final float pageWidth = pageSize.getWidth();
        final float pageHeight = pageSize.getHeight();

        for (Integer fileId : dto.getFileIds()) {
            final WorkShareFileDto file = workShareFileService.findOrNotFound(new WorkShareFileByIdFinderDto(fileId));
            if (StringUtils.isNotBlank(file.getContent())) {
                stamper.insertPage(++pageNumber, pageSize);

                final byte[] imageBytes = Base64.getDecoder().decode(file.getContent());
                final byte[] compressedBytes = ImageUtils.compressImage(imageBytes, IMAGE_COMPRESSION_QUALITY);
                final Image image = Image.getInstance(compressedBytes);

                float margin = 36f;
                float availableWidth = pageWidth - 2 * margin;
                float availableHeight = pageHeight - 2 * margin;

                if (image.getWidth() > availableWidth || image.getHeight() > availableHeight) {
                    image.scaleToFit(availableWidth, availableHeight);
                }

                final float x = (pageWidth - image.getScaledWidth()) / 2;
                final float y = (pageHeight - image.getScaledHeight()) / 2;
                image.setAbsolutePosition(x, y);

                stamper.getOverContent(pageNumber).addImage(image);
            }
        }
    }
}
