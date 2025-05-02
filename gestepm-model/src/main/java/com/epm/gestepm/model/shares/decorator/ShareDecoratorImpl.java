package com.epm.gestepm.model.shares.decorator;

import com.epm.gestepm.lib.locale.LocaleProvider;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFilterDto;
import com.epm.gestepm.modelapi.inspection.service.InspectionExportService;
import com.epm.gestepm.modelapi.inspection.service.InspectionService;
import com.epm.gestepm.modelapi.interventionprshare.service.InterventionPrShareService;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareDto;
import com.epm.gestepm.modelapi.shares.construction.dto.filter.ConstructionShareFilterDto;
import com.epm.gestepm.modelapi.shares.construction.service.ConstructionShareExportService;
import com.epm.gestepm.modelapi.shares.construction.service.ConstructionShareService;
import com.epm.gestepm.modelapi.shares.decorator.ShareDecorator;
import com.epm.gestepm.modelapi.workshare.service.WorkShareService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class ShareDecoratorImpl implements ShareDecorator {

    private final ConstructionShareService constructionShareService;

    private final ConstructionShareExportService constructionShareExportService;

    private final InterventionPrShareService interventionPrShareService;

    private final InspectionService inspectionService;

    private final InspectionExportService inspectionExportService;

    private final LocaleProvider localeProvider;

    private final MessageSource messageSource;

    private final WorkShareService workShareService;

    @Override
    public List<PdfFileDTO> exportShares(Integer projectId, LocalDateTime startDate, LocalDateTime endDate) {
        final List<PdfFileDTO> pdfs = new ArrayList<>();

        final ConstructionShareFilterDto constructionShareFilter = new ConstructionShareFilterDto();
        constructionShareFilter.setProjectIds(List.of(projectId));
        constructionShareFilter.setStartDate(startDate);
        constructionShareFilter.setEndDate(endDate);

        final List<PdfFileDTO> csPdfs = this.getConstructionShares(constructionShareFilter);

        final List<PdfFileDTO> iprPdfs = this.interventionPrShareService.generateSharesByProjectAndInterval(projectId.longValue(), startDate, endDate);

        final InspectionFilterDto inspectionFilter = new InspectionFilterDto();
        inspectionFilter.setProjectId(projectId);
        inspectionFilter.setStartDate(startDate);
        inspectionFilter.setEndDate(endDate);

        final List<PdfFileDTO> isPdfs = this.getInspections(inspectionFilter);

        final List<PdfFileDTO> wsPdfs = this.workShareService.generateSharesByProjectAndInterval(projectId.longValue(), startDate, endDate);

        pdfs.addAll(csPdfs);
        pdfs.addAll(iprPdfs);
        pdfs.addAll(isPdfs);
        pdfs.addAll(wsPdfs);

        return pdfs;
    }

    private List<PdfFileDTO> getConstructionShares(final ConstructionShareFilterDto filter) {
        final List<PdfFileDTO> pdfs = new ArrayList<>();
        final List<ConstructionShareDto> constructionShares = this.constructionShareService.list(filter);
        final Locale locale = new Locale(this.localeProvider.getLocale().orElse("es"));

        constructionShares.forEach(constructionShare -> {
            final byte[] pdf = this.constructionShareExportService.generate(constructionShare);

            final String fileName = this.messageSource.getMessage("shares.construction.pdf.name", new Object[] {
                    constructionShare.getId().toString(),
                    Utiles.transform(constructionShare.getStartDate(), "dd-MM-yyyy")
            }, locale) + ".pdf";

            final PdfFileDTO pdfFileDTO = new PdfFileDTO();
            pdfFileDTO.setDocumentBytes(pdf);
            pdfFileDTO.setFileName(fileName);

            pdfs.add(pdfFileDTO);
        });

        return pdfs;
    }

    private List<PdfFileDTO> getInspections(final InspectionFilterDto filter) {
        final List<PdfFileDTO> pdfs = new ArrayList<>();
        final List<InspectionDto> inspections = this.inspectionService.list(filter);
        final Locale locale = new Locale(this.localeProvider.getLocale().orElse("es"));

        inspections.forEach(inspection -> {
            final byte[] pdf = this.inspectionExportService.generate(inspection);

            final String fileName = this.messageSource.getMessage("shares.no.programmed.pdf.name", new Object[]{
                    inspection.getShareId().toString(),
                    inspection.getId().toString(),
                    Utiles.transform(inspection.getStartDate(), "yyyyMMdd")
            }, locale) + ".pdf";

            final PdfFileDTO pdfFileDTO = new PdfFileDTO();
            pdfFileDTO.setDocumentBytes(pdf);
            pdfFileDTO.setFileName(fileName);

            pdfs.add(pdfFileDTO);
        });

        return pdfs;
    }
}
