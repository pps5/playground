package io.github.pps5.kakaosampleapp.data.entity

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class Entry(
    @PropertyElement(name = "summary")
    var summary: String,
    @PropertyElement(name = "published")
    var published: String,
    @PropertyElement(name = "id")
    var id: String,
    @PropertyElement(name = "title")
    var title: String,
    @PropertyElement(name = "updated")
    var updated: String
)