package io.github.ludongrong.dbcoder.powerdesigner.controller.dto;

import io.github.ludongrong.dbcoder.powerdesigner.entity.PdFileBo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PdFileMapper {

    PdFileMapper INSTANCE = Mappers.getMapper(PdFileMapper.class);

    @Mappings({})
    PdFileBo vo2bo(PdFileVo pdFileVo);

    @Mappings({})
    PdFileVo bo2vo(PdFileBo pdFileBo);

}
