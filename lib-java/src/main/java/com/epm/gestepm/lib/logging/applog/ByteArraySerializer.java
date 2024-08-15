package com.epm.gestepm.lib.logging.applog;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ByteArraySerializer extends StdSerializer<byte[]> {

    public ByteArraySerializer() {
        this(null);
    }

    protected ByteArraySerializer(Class<byte[]> t) {
        super(t);
    }

    @Override
    public void serialize(byte[] bytes, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStringField("content", String.format("byte[%d]", bytes.length));
    }

}
