package com.terrylmay.duckling.extractor;

import com.terrylmay.duckling.entity.BaseEntity;

import java.util.List;

public interface EntityExtractor {
    List<? extends BaseEntity> extract(String target);
}
