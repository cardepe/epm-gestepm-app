package com.epm.gestepm.model.teleworkingsigning.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.teleworkingsigning.dao.entity.TeleworkingSigning;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.TeleworkingSigningDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapTSToTeleworkingSigningDto {

  TeleworkingSigningDto from(TeleworkingSigning teleworkingSigning);

  List<TeleworkingSigningDto> from(List<TeleworkingSigning> teleworkingSigning);

  default Page<TeleworkingSigningDto> from(Page<TeleworkingSigning> page) {
    return new Page<>(page.cursor(), from(page.getContent()));
  }

}
