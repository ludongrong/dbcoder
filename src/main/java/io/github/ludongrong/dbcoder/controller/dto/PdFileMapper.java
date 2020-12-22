package io.github.ludongrong.dbcoder.controller.dto;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import io.github.ludongrong.dbcoder.entity.PdFileBo;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PdFileMapper {

    PdFileMapper INSTANCE = Mappers.getMapper(PdFileMapper.class);

    @Mappings({})
    PdFileBo vo2bo(PdFileVo pdFileVo);

    @Mappings({})
    PdFileVo bo2vo(PdFileBo pdFileBo);

    List<PdFileVo> bo2vo(List<PdFileBo> pdFileBoList);
}
