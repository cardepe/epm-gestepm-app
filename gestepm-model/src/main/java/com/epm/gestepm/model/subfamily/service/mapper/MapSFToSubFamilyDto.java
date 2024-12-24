package com.epm.gestepm.model.subfamily.service.mapper;

import com.epm.gestepm.modelapi.subfamily.dto.SubFamily;
import com.epm.gestepm.modelapi.subfamily.dto.SubFamilyDto;
import com.epm.gestepm.modelapi.subrole.dto.SubRole;
import org.mapstruct.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Mapper
public interface MapSFToSubFamilyDto {

    @Mapping(target = "name", ignore = true)
    @Mapping(source = "subFamily.family.id", target = "familyId")
    @Mapping(source = "subFamily.subRoles", target = "subRoleIds", qualifiedByName = "mapSubRoles")
    SubFamilyDto from(SubFamily subFamily, @Context Locale locale);

    List<SubFamilyDto> from(List<SubFamily> subFamilies, @Context Locale locale);

    @Named("mapSubRoles")
    static List<Integer> mapSubRoles(List<SubRole> subRoles) {
        return subRoles.stream().map(SubRole::getId).mapToInt(Long::intValue).boxed().collect(Collectors.toList());
    }

    @AfterMapping
    default void transform(@MappingTarget SubFamilyDto dto, SubFamily subFamily, @Context Locale locale) {
        dto.setName("es".equals(locale.getLanguage()) ? subFamily.getNameES() : subFamily.getNameFR());
    }
}
