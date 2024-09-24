package pe.edu.upc.jokescompose.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.jokescompose.common.Resource
import pe.edu.upc.jokescompose.data.local.JokeDao
import pe.edu.upc.jokescompose.data.local.JokeEntity
import pe.edu.upc.jokescompose.data.remote.JokeService
import pe.edu.upc.jokescompose.data.remote.toJoke
import pe.edu.upc.jokescompose.domain.Joke

class JokeRepository(
    private val service: JokeService,
    private val dao: JokeDao
) {

    suspend fun insertJoke(joke: Joke) = withContext(Dispatchers.IO) {
        dao.insert(JokeEntity(joke.description, joke.score))
    }

    suspend fun deleteJoke(joke: Joke) = withContext(Dispatchers.IO) {
        dao.delete(JokeEntity(joke.description, joke.score))
    }

    suspend fun updateJoke(joke: Joke) = withContext(Dispatchers.IO) {
        dao.update(JokeEntity(joke.description, joke.score))
    }

    suspend fun getJoke(): Resource<Joke> = withContext(Dispatchers.IO) {
        try {
            val response = service.getJoke()

            if (response.isSuccessful) {
                response.body()?.let { jokeDto ->

                    val joke = jokeDto.toJoke()
                    dao.fetchById(joke.description)?.let { jokeEntity ->
                        joke.score = jokeEntity.score
                    }
                    return@withContext Resource.Success(data = joke)
                }
                return@withContext Resource.Error(message = response.message())
            }
            return@withContext Resource.Error(message = response.message())
        } catch (e: Exception) {
            return@withContext Resource.Error(message = e.message ?: "An exception occurred")

        }


    }

    suspend fun getJokes(): Resource<List<Joke>> = withContext(Dispatchers.IO) {

        try {
            val jokesEntity = dao.fetchAll()
            val jokes = jokesEntity.map { jokeEntity: JokeEntity ->
                Joke(jokeEntity.description, jokeEntity.score)
            }.toList()
            return@withContext Resource.Success(data = jokes)

        } catch (e: Exception) {
            return@withContext Resource.Error(message = e.message ?: "An exception occurred")

        }
    }
}