package com.aihangxunxi.aitalk.storage.config;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * @author chenqingze107@163.com
 * @version 2.0 2020/10/13 11:56 AM
 */
public class EnumCodecRegistry implements CodecRegistry {

	@Override
	public <T> Codec<T> get(Class<T> clazz) {
		if (Enum.class.isAssignableFrom(clazz)) {
			return new EnumCodec(clazz);
		}
		return null;
	}

	@Override
	public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
		return get(clazz);
	}

	private static class EnumCodec<T extends Enum<T>> implements Codec<T> {

		private final Class<T> clazz;

		EnumCodec(final Class<T> clazz) {
			this.clazz = clazz;
		}

		@Override
		public void encode(final BsonWriter writer, final T value, final EncoderContext encoderContext) {
			writer.writeString(value.name());
		}

		@Override
		public Class<T> getEncoderClass() {
			return clazz;
		}

		@Override
		public T decode(final BsonReader reader, final DecoderContext decoderContext) {
			return Enum.valueOf(clazz, reader.readString());
		}

	}

}
