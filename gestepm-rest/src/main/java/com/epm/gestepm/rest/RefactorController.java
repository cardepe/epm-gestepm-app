package com.epm.gestepm.rest;

import com.epm.gestepm.modelapi.common.utils.datatables.DataTableRequest;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableResults;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.deprecated.project.dto.ProjectTableDTO;
import com.epm.gestepm.modelapi.deprecated.project.service.ProjectOldService;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import com.epm.gestepm.modelapi.userholiday.dto.UserHolidayDTO;
import com.epm.gestepm.modelapi.userholiday.service.UserHolidaysService;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@Data
@RestController
public class RefactorController {

    private static final Log log = LogFactory.getLog(RefactorController.class);

    private final MessageSource messageSource;

    private final ProjectOldService projectOldService;

    private final UserHolidaysService userHolidaysService;

    @ResponseBody
    @GetMapping("/users/{id}/holidays/dt")
    public DataTableResults<UserHolidayDTO> userHolidaysDatatable(@PathVariable Long id, HttpServletRequest request, Locale locale) {

        try {

            DataTableRequest<UserHoliday> dataTableInRQ = new DataTableRequest<>(request);
            PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

            List<UserHolidayDTO> userHolidays = userHolidaysService.getUserHolidaysDTOsByUserId(id, pagination);

            Long totalRecords = userHolidaysService.getUserHolidaysCountByUser(id);

            DataTableResults<UserHolidayDTO> dataTableResult = new DataTableResults<>();
            dataTableResult.setDraw(dataTableInRQ.getDraw());
            dataTableResult.setData(userHolidays);
            dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
            dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

            if (userHolidays != null && !userHolidays.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
                dataTableResult.setRecordsFiltered(Integer.toString(userHolidays.size()));
            }

            return dataTableResult;

        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @ResponseBody
    @GetMapping("/users/{id}/projects/dt")
    public DataTableResults<ProjectTableDTO> userProjectsDatatable(@PathVariable Long id, HttpServletRequest request, Locale locale) {

        try {

            DataTableRequest<UserHoliday> dataTableInRQ = new DataTableRequest<>(request);
            PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

            List<ProjectTableDTO> userProjects = projectOldService.getProjectsByUserMemberDataTables(id, pagination, null);

            Long totalRecords = projectOldService.getProjectsCountByUserMember(id, null);

            DataTableResults<ProjectTableDTO> dataTableResult = new DataTableResults<>();
            dataTableResult.setDraw(dataTableInRQ.getDraw());
            dataTableResult.setData(userProjects);
            dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
            dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

            if (userProjects != null && !userProjects.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
                dataTableResult.setRecordsFiltered(Integer.toString(userProjects.size()));
            }

            return dataTableResult;

        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @ResponseBody
    @PostMapping("/users/{id}/projects/create")
    public ResponseEntity<String> createProjectMember(@RequestParam Long[] projectId, @PathVariable Long id, Locale locale) {

        try {

            for (Long project : projectId) {

                projectOldService.createMember(project, id);

                log.info("Usuario " + id + " añadido al proyecto " + project);
            }

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("user.detail.projects.success", new Object[] { }, locale), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(messageSource.getMessage("user.detail.projects.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @DeleteMapping("/users/{id}/projects/delete/{projectId}")
    public ResponseEntity<String> deleteProjectMember(@PathVariable Long projectId, @PathVariable Long id, Locale locale) {

        try {

            projectOldService.deleteMember(projectId, id);

            log.info("Usuario " + id + " eliminado del proyecto " + projectId);

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("project.detail.projects.delete", new Object[] { }, locale), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(messageSource.getMessage("project.detail.projects.derror", new Object[] { }, locale), HttpStatus.NOT_FOUND);
        }
    }
}
