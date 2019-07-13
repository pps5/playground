package io.github.pps5.kakaosampleapp.data.converter

import com.tickaroo.tikxml.TypeConverter
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class ZonedDateTimeConverter : TypeConverter<ZonedDateTime> {

    companion object {
        private const val ZONED_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssxxxxx"
    }

    private val formatter = DateTimeFormatter.ofPattern(ZONED_DATE_TIME_FORMAT)

    override fun write(value: ZonedDateTime?): String = formatter.format(value)

    override fun read(value: String?): ZonedDateTime = ZonedDateTime.parse(value, formatter)
}