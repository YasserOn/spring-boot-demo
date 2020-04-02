package com.xkcoding.netty.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public class ByteBufSerializer extends StdSerializer<ByteBuf> {
    public ByteBufSerializer() {
        this(null);
    }

    public ByteBufSerializer(Class<ByteBuf> t) {
        super(t);
    }

    public void serialize(ByteBuf value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        int readable = value.readableBytes();
        byte[] bytes = new byte[readable];
        value.readBytes(bytes);
        value.release();
        gen.writeBinary(bytes);
    }
}
