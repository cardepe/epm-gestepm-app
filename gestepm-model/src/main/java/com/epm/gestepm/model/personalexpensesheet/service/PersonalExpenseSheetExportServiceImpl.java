package com.epm.gestepm.model.personalexpensesheet.service;

import com.epm.gestepm.lib.locale.LocaleProvider;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.personalexpense.dto.PersonalExpenseDto;
import com.epm.gestepm.modelapi.personalexpense.dto.PersonalExpenseFileDto;
import com.epm.gestepm.modelapi.personalexpense.dto.filter.PersonalExpenseFileFilterDto;
import com.epm.gestepm.modelapi.personalexpense.dto.filter.PersonalExpenseFilterDto;
import com.epm.gestepm.modelapi.personalexpense.service.PersonalExpenseFileService;
import com.epm.gestepm.modelapi.personalexpense.service.PersonalExpenseService;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetDto;
import com.epm.gestepm.modelapi.personalexpensesheet.exception.PersonalExpenseSheetExportException;
import com.epm.gestepm.modelapi.personalexpensesheet.service.PersonalExpenseSheetExportService;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;

@Service
@Validated
@EnableExecutionLog(layerMarker = SERVICE)
public class PersonalExpenseSheetExportServiceImpl implements PersonalExpenseSheetExportService {

    private static final Integer EXPENSES_LIMITER_COUNT = 15;

    private static final String TEMPLATE_PATH = "/templates/pdf/expenses_%s%s.pdf";

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    private final LocaleProvider localeProvider;

    private final MessageSource messageSource;

    private final PersonalExpenseService personalExpenseService;

    private final PersonalExpenseFileService personalExpenseFileService;

    private final ProjectService projectService;

    private final UserService userService;

    public PersonalExpenseSheetExportServiceImpl(LocaleProvider localeProvider, MessageSource messageSource, PersonalExpenseService personalExpenseService, PersonalExpenseFileService personalExpenseFileService, ProjectService projectService, UserService userService) {
        this.localeProvider = localeProvider;
        this.messageSource = messageSource;
        this.personalExpenseService = personalExpenseService;
        this.personalExpenseFileService = personalExpenseFileService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public byte[] generate(PersonalExpenseSheetDto personalExpenseSheet) {
        try {
            final User user = this.userService.getUserById(personalExpenseSheet.getUserId().longValue());
            final Project project = this.projectService.getProjectById(personalExpenseSheet.getProjectId().longValue());

            final String language = localeProvider.getLocale().orElse("es");
            final Locale locale = new Locale(language);
            final PdfReader pdfTemplate = new PdfReader(String.format(
                    TEMPLATE_PATH, language, personalExpenseSheet.getPersonalExpenseIds().size() > EXPENSES_LIMITER_COUNT ? "_full" : ""));
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final PdfStamper stamper = new PdfStamper(pdfTemplate, baos);
            final AcroFields acroFields = stamper.getAcroFields();

            acroFields.setField("pageNumber", personalExpenseSheet.getId().toString());
            acroFields.setField("creationDate", Utiles.transform(personalExpenseSheet.getStartDate(), DATE_FORMAT));
            acroFields.setField("userName", user.getFullName().toUpperCase());
            acroFields.setField("projectName", project.getName().toUpperCase());

            final PersonalExpenseFilterDto filterDto = new PersonalExpenseFilterDto();
            filterDto.setPersonalExpenseSheetId(personalExpenseSheet.getId());

            final List<PersonalExpenseDto> personalExpenses = this.personalExpenseService.list(filterDto);

            int i = 1;
            double total = 0.0;
            for (final PersonalExpenseDto personalExpense : personalExpenses) {

                acroFields.setField("startDate" + i, Utiles.transform(personalExpense.getStartDate(), DATE_FORMAT));
                acroFields.setField("priceType" + i, this.messageSource.getMessage("price.type." + personalExpense.getPriceType().name().toLowerCase(), null, locale));
                acroFields.setField("description" + i, personalExpense.getDescription());
                acroFields.setField("paymentType" + i, this.messageSource.getMessage("payment.type." + personalExpense.getPaymentType().name().toLowerCase(), null, locale));
                acroFields.setField("quantity" + i, formatDouble(personalExpense.getQuantity()));
                acroFields.setField("amount" + i, new DecimalFormat("00.00 €").format(personalExpense.getAmount()));

                total += personalExpense.getAmount();

                if (!personalExpense.getFileIds().isEmpty()) {
                    this.printImages(stamper, pdfTemplate, personalExpense);
                }
                i++;
            }

            acroFields.setField("total", new DecimalFormat("#.## €").format(total));

            acroFields.setGenerateAppearances(true);
            stamper.setFormFlattening(true);

            stamper.close();
            pdfTemplate.close();

            return baos.toByteArray();
        } catch (IOException | DocumentException ex) {
            throw new PersonalExpenseSheetExportException(personalExpenseSheet.getId(), ex.getMessage());
        }
    }

    private void printImages(final PdfStamper stamper, final PdfReader pdfTemplate, final PersonalExpenseDto personalExpense) throws DocumentException, IOException {
        int pageNumber = pdfTemplate.getNumberOfPages();

        final float pageWidth = pdfTemplate.getPageSize(pageNumber).getWidth();
        final float pageHeight = pdfTemplate.getPageSize(pageNumber).getHeight();

        final List<Integer> fileIds = personalExpense.getFileIds();

        final PersonalExpenseFileFilterDto filterDto = new PersonalExpenseFileFilterDto();
        filterDto.setIds(fileIds);

        final List<PersonalExpenseFileDto> files = this.personalExpenseFileService.list(filterDto).stream()
                .filter(file -> file.getContent() != null)
                .collect(Collectors.toList());

        for (final PersonalExpenseFileDto file : files) {
            if (isPDF(file)) {
                final byte[] pdfBytes = Base64.getDecoder().decode(file.getContent());
                final InputStream inputStream = new ByteArrayInputStream(pdfBytes);
                final PdfReader pdfReader = new PdfReader(inputStream);

                for (int j = 1; j <= pdfReader.getNumberOfPages(); j++) {

                    stamper.insertPage(++pageNumber, pdfTemplate.getPageSizeWithRotation(1));

                    final PdfImportedPage page = stamper.getImportedPage(pdfReader, j);
                    final PdfContentByte canvas = stamper.getOverContent(pageNumber);

                    if (page.getRotation() == 0) {
                        canvas.addTemplate(page, 0, -1f, 1f, 0, 0, pageHeight);
                    } else if (page.getRotation() == 90) {
                        canvas.addTemplate(page, -1f, 0, 0, -1f, pageWidth, pageHeight);
                    } else if (page.getRotation() == 180) {
                        canvas.addTemplate(page, 0, 1.0F, -1.0F, 0, pageWidth, 0);
                    } else if (page.getRotation() == 270) {
                        canvas.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
                    }
                }
            } else {
                stamper.insertPage(++pageNumber, pdfTemplate.getPageSizeWithRotation(1));

                final Image image = Image.getInstance(Base64.getDecoder().decode(file.getContent()));

                if (image.getWidth() > pageWidth || image.getHeight() > pageHeight) {
                    image.scaleToFit(pageWidth, pageHeight);
                }

                float x = (pageWidth - image.getScaledWidth()) / 2;
                float y = (pageHeight - image.getScaledHeight()) / 2;

                image.setAbsolutePosition(x, y);

                final PdfContentByte canvas = stamper.getOverContent(pageNumber);
                canvas.addImage(image);
            }
        }
    }

    private String formatDouble(double number) {
        if (number == (long) number) {
            return String.format("%d", (long) number);
        } else {
            return String.format("%s", number);
        }
    }

    private boolean isPDF(final PersonalExpenseFileDto file) {
        return file.getName().endsWith(".pdf");
    }
}
