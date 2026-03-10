package com.example.recorladora.viewmodel.navigation

object Routes {
    const val LIST = "list"
    const val DETAIL = "detail/{id}"
    const val EDIT = "edit?id={id}"

    fun detail(id: Long) = "detail/$id"

    fun edit(id: Long?) = if (id == null) "edit?=" else "edit?id=$id"
}