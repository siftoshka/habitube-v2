package az.siftoshka.habitube.domain.usecases.remote

import az.siftoshka.habitube.data.remote.dto.toPerson
import az.siftoshka.habitube.domain.model.Person
import az.siftoshka.habitube.domain.repository.MovieDBApiRepository
import az.siftoshka.habitube.domain.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Use-case to get person (actor/director) from repository call.
 */
class GetPersonUseCase @Inject constructor(
    private val repository: MovieDBApiRepository
) {
    operator fun invoke(personId: Int): Flow<Resource<Person>> = flow {
        try {
            emit(Resource.Loading())
            val person = repository.getPerson(personId).toPerson()
            delay(150L)
            emit(Resource.Success(person))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "HTTP Error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}