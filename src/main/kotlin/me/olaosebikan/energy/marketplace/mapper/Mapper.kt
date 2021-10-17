package me.olaosebikan.energy.marketplace.mapper

interface Mapper<D, E> {

    fun fromEntity(entity: E) : D
    fun toEntity(dto : D) : E
}