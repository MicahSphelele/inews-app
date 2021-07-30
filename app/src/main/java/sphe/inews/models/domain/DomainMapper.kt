package sphe.inews.models.domain

interface DomainMapper<T,D> {

    fun mapToDomainModel(entity: T) : D

    //fun mapFromDomainModel(domainModel: D, category: String) : T
}