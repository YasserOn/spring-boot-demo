package com.xkcoding.netty.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufOutputStream;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class ByteBufDeserializer extends StdDeserializer<ByteBuf> {
    public ByteBufDeserializer() {
        this(null);
    }

    public ByteBufDeserializer(Class<ByteBuf> vc) {
        super(vc);
    }

    public ByteBuf deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = StringUtils.trim(p.getText());
        if (StringUtils.isEmpty(text) || text.equals("null")) {
            return null;
        }

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        ByteBufOutputStream outputStream = new ByteBufOutputStream(buf);
        p.readBinaryValue(outputStream);
        return buf;
    }
}
