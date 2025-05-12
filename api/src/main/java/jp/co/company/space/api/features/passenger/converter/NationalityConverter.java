package jp.co.company.space.api.features.passenger.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jp.co.company.space.api.features.catalog.domain.Nationality;
import jp.co.company.space.api.features.passenger.exception.PersonalInformationException;

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
