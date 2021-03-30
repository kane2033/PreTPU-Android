package ru.tpu.pretpu.features.news.domain.interactor

import ru.tpu.pretpu.core.base.domain.exception.Failure
import ru.tpu.pretpu.core.base.domain.functional.Either
import ru.tpu.pretpu.core.base.domain.interactor.UseCase
import ru.tpu.pretpu.features.news.domain.entity.LinkItem
import javax.inject.Inject

/**
 * [UseCase], использующийся
 * */
class GetLinkItems
@Inject constructor() :
    UseCase<Pair<String, List<LinkItem>>, LinkItem>() {
    override suspend fun run(params: LinkItem): Either<Failure, Pair<String, List<LinkItem>>> {
        val linkItems = params.children
        return if (linkItems != null) {
            Either.Right(Pair(params.name, linkItems))
        } else {
            Either.Left(Failure.MissingContentFailure)
        }
    }
}