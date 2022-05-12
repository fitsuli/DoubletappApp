package ru.fitsuli.doubletappapp.repository

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import ru.fitsuli.doubletappapp.Priority
import ru.fitsuli.doubletappapp.Type
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Priority::class)
object PrioritySerializer : KSerializer<Priority> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Priority", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: Priority) {
        encoder.encodeInt(value.ordinal)
    }

    override fun deserialize(decoder: Decoder): Priority = try {
        val key = decoder.decodeInt()
        Priority.values().getOrElse(key) { Priority.HIGH }
    } catch (e: IllegalArgumentException) {
        Priority.HIGH
    }
}

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Type::class)
object TypeSerializer : KSerializer<Type> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Type", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: Type) {
        encoder.encodeInt(value.ordinal)
    }

    override fun deserialize(decoder: Decoder): Type = try {
        val key = decoder.decodeInt()
        Type.values().getOrElse(key) { Type.GOOD }
    } catch (e: IllegalArgumentException) {
        Type.GOOD
    }
}

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = OffsetDateTime::class)
object DateTimeUTCSerializer : KSerializer<OffsetDateTime> {
    override val descriptor = PrimitiveSerialDescriptor("OffsetDateTime", PrimitiveKind.LONG)

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        return OffsetDateTime.ofInstant(
            Instant.ofEpochSecond(decoder.decodeLong()),
            ZoneId.systemDefault()
        )
    }

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        encoder.encodeLong(value.toEpochSecond())
    }
}