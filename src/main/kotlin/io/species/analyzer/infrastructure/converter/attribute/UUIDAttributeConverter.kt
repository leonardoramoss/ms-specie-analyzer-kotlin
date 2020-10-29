package io.species.analyzer.infrastructure.converter.attribute

import java.util.UUID
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class UUIDAttributeConverter : AttributeConverter<UUID?, String?> {

    override fun convertToDatabaseColumn(uuid: UUID?): String?
        = uuid?.toString()

    override fun convertToEntityAttribute(s: String?): UUID
        = UUID.fromString(s)
}