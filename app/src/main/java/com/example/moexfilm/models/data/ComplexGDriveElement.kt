package com.example.moexfilm.models.data

import java.io.Serializable

data class ComplexGDriveElement (
    val parent:GDriveElement,
    val childs:List<GDriveElement>
    ):Serializable