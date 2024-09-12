package pe.edu.upc.superherocompose.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.superherocompose.common.Resource
import pe.edu.upc.superherocompose.data.remote.HeroService
import pe.edu.upc.superherocompose.data.remote.toHero
import pe.edu.upc.superherocompose.domain.Hero

class HeroRepository(private val heroService: HeroService) {
    suspend fun searchHero(name: String): Resource<List<Hero>> = withContext(Dispatchers.IO) {

        val response = heroService.searchHero(name)
        if (response.isSuccessful) {

            response.body()?.heroes?.let { heroesDto ->
                val heroes = heroesDto.map { heroDto ->
                    heroDto.toHero()
                }.toList()
                return@withContext Resource.Success(data = heroes)
            }
            return@withContext Resource.Error(message = response.body()?.error?:"An error occurred")

        }
        return@withContext Resource.Error(message = response.message())

    }
}