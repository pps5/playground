package io.github.pps5.kakaosampleapp.data.entity

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import io.github.pps5.kakaosampleapp.data.converter.ZonedDateTimeConverter
import org.threeten.bp.ZonedDateTime

@Xml
data class Entry(
    @PropertyElement(name = "summary")
    var summary: String,
    @PropertyElement(name = "published", converter = ZonedDateTimeConverter::class)
    var published: ZonedDateTime,
    @PropertyElement(name = "id")
    var id: String,
    @PropertyElement(name = "title")
    var title: String,
    @PropertyElement(name = "updated", converter = ZonedDateTimeConverter::class)
    var updated: ZonedDateTime
)