package com.terrylmay.duckling.tokenizer;

import java.util.List;

public interface Tokenizer {
    List<String> cut(String target);
}
