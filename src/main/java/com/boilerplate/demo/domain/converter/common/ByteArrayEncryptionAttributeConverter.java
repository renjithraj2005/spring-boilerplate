package com.boilerplate.demo.domain.converter.common;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ByteArrayEncryptionAttributeConverter extends GenericEncryptionAttributeConverter implements AttributeConverter<byte[], byte[]> {

    public ByteArrayEncryptionAttributeConverter() {
        super();
    }

    @Override
    public byte[] convertToDatabaseColumn(final byte[] attribute) {
        final byte[] encrypted = super.encrypt(attribute);
        return encrypted;
    }

    @Override
    public byte[] convertToEntityAttribute(final byte[] dbData) {
        try{
            return super.decrypt(dbData);
        }catch (Exception exp){
            exp.printStackTrace();
            return dbData;
        }
    }
}
