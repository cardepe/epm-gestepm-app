package com.epm.gestepm.lib.utils;

import java.io.Serializable;
import org.apache.commons.lang3.SerializationUtils;

public class ObjectCopyUtils {

    private ObjectCopyUtils() {
    }

    public static <T extends Serializable> T copy(T obj) {
        return SerializationUtils.clone(obj);
    }

}
