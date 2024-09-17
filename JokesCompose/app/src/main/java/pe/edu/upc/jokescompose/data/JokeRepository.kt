package pe.edu.upc.jokescompose.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.jokescompose.common.Resource
import pe.edu.upc.jokescompose.domain.Joke

class JokeRepository(private val service: JokeService) {

    suspend fun getJoke(): Resource<Joke> = withContext(Dispatchers.IO){
        val response = service.getJoke()

        if (response.isSuccessful) {
            response.body()?.let { heroDto ->
                return@withContext Resource.Success(data = heroDto.toJoke())
            }
            return@withContext  Resource.Error(message = response.message())
        }
        return@withContext Resource.Error(message = response.message())
    }
}