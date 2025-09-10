package jp.co.nova.gate.api.features.passenger.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jp.co.nova.gate.api.features.catalog.domain.Nationality;
import jp.co.nova.gate.api.features.passenger.exception.PersonalInformationException;

/**
 * A {@link AttributeConverter} for the {@link Nationality} class.
 */
@Converter
public class NationalityConverter implements AttributeConverter<Nationality, String> {
    @Override
    public String convertToDatabaseColumn(Nationality nationality) {
        return nationality.getKey();
    }

    @Override
    public Nationality convertToEntityAttribute(String isoCode) throws PersonalInformationException {
        return Nationality.create(isoCode);
    }
}
