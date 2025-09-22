package com.contrack.contrack_app.mapper.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@MapperConfig(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MapStructCentralConfig {}