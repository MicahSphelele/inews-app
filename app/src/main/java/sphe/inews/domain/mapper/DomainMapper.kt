package sphe.inews.domain.mapper

interface DomainMapper<T,D> {

    fun mapToDomainModel(entity: T) : D

    //fun mapFromDomainModel(domainModel: D, category: String) : T
}