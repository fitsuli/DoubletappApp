package ru.fitsuli.doubletappapp.domain.models

data class SearchSortFilter(
    val sortBy: SortBy = SortBy.NONE,
    val filterStr: String = ""
)
