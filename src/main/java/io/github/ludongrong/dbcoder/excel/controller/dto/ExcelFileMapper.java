package io.github.ludongrong.dbcoder.excel.controller.dto;

import io.github.ludongrong.dbcoder.excel.entity.ExcelFileBo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExcelFileMapper {

    ExcelFileMapper INSTANCE = Mappers.getMapper(ExcelFileMapper.class);

    @Mappings({})
    ExcelFileBo vo2bo(ExcelFileVo excelFileVo);

    @Mappings({})
    ExcelFileVo bo2vo(ExcelFileBo excelFileBo);

}
