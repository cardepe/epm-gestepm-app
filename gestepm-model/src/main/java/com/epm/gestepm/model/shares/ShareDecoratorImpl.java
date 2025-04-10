package com.epm.gestepm.model.shares;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.constructionshare.service.ConstructionShareOldService;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFilterDto;
import com.epm.gestepm.modelapi.inspection.service.InspectionExportService;
import com.epm.gestepm.modelapi.inspection.service.InspectionService;
import com.epm.gestepm.modelapi.interventionprshare.service.InterventionPrShareService;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.shares.ShareDecorator;
import com.epm.gestepm.modelapi.workshare.service.WorkShareService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;

@Service
@Validated
@EnableExecutionLog(layerMarker = SERVICE)
public class ShareDecoratorImpl implements ShareDecorator {

    private final ConstructionShareOldService constructionShareOldService;

    private final InterventionPrShareService interventionPrShareService;

    private final InspectionService inspectionService;

    private final InspectionExportService inspectionExportService;

    private final MessageSource messageSource;

    private final WorkShareService workShareService;

    public ShareDecoratorImpl(ConstructionShareOldService constructionShareOldService, InterventionPrShareService interventionPrShareService, InspectionService inspectionService, InspectionExportService inspectionExportService, MessageSource messageSource, WorkShareService workShareService) {
        this.constructionShareOldService = constructionShareOldService;
        this.interventionPrShareService = interventionPrShareService;
        this.inspectionService = inspectionService;
        this.inspectionExportService = inspectionExportService;
        this.messageSource = messageSource;
        this.workShareService = workShareService;
    }

    @Override
    public List<PdfFileDTO> exportShares(Integer projectId, LocalDateTime startDate, LocalDateTime endDate) {
        final List<PdfFileDTO> pdfs = new ArrayList<>();

        final List<PdfFileDTO> csPdfs = this.constructionShareOldService.generateSharesByProjectAndInterval(projectId.longValue(), startDate, endDate);
        final List<PdfFileDTO> iprPdfs = this.interventionPrShareService.generateSharesByProjectAndInterval(projectId.longValue(), startDate, endDate);

        final InspectionFilterDto filter = new InspectionFilterDto();
        filter.setProjectId(projectId);
        filter.setStartDate(startDate);
        filter.setEndDate(endDate);

        final List<PdfFileDTO> isPdfs = this.getInspections(filter);

        final List<PdfFileDTO> wsPdfs = this.workShareService.generateSharesByProjectAndInterval(projectId.longValue(), startDate, endDate);

        pdfs.addAll(csPdfs);
        pdfs.addAll(iprPdfs);
        pdfs.addAll(isPdfs);
        pdfs.addAll(wsPdfs);

        return pdfs;
    }

    private List<PdfFileDTO> getInspections(final InspectionFilterDto filter) {
        final List<PdfFileDTO> pdfs = new ArrayList<>();
        final List<InspectionDto> inspections = this.inspectionService.list(filter);

        inspections.forEach(inspection -> {
            final byte[] pdf = this.inspectionExportService.generate(inspection);

            final String fileName = this.messageSource.getMessage("shares.no.programmed.pdf.name", new Object[]{
                    inspection.getShareId().toString(),
                    inspection.getId().toString(),
                    Utiles.transform(inspection.getStartDate(), "yyyyMMdd")
            }, Locale.getDefault()) + ".pdf";

            final PdfFileDTO pdfFileDTO = new PdfFileDTO();
            pdfFileDTO.setDocumentBytes(pdf);
            pdfFileDTO.setFileName(fileName);

            pdfs.add(pdfFileDTO);
        });

        return pdfs;
    }
}
