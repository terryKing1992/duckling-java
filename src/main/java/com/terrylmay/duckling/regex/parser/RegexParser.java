package com.terrylmay.duckling.regex.parser;

import com.terrylmay.duckling.context.Context;
import com.terrylmay.duckling.entity.BaseEntity;

public interface RegexParser {
    BaseEntity parse(String token, BaseEntity baseEntity, Context context);
}
