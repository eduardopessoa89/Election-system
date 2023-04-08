package com.teste.pautateste.utils;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public final class MapperUtil {

    private static final ModelMapper modelMapper = modelFactory();

    public static ModelMapper modelFactory() {
        return new ModelMapper();
    }

    public static  <T, C> C convert(T source, Class<C> finalClass) {
        return modelMapper.map(source, finalClass);
    }

    public static <T, C> List<C> convertList(List<T> sourceList, Class<C> finalClass) {
        List<C> list = new ArrayList<>();
        sourceList.forEach(value -> list.add(convert(value, finalClass)));
        return list;
    }

}
