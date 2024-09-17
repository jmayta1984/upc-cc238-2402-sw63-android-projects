package pe.edu.upc.superherocompose.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.superherocompose.common.Resource
import pe.edu.upc.superherocompose.data.local.HeroDao
import pe.edu.upc.superherocompose.data.local.HeroEntity
import pe.edu.upc.superherocompose.data.remote.HeroDto
import pe.edu.upc.superherocompose.data.remote.HeroService
import pe.edu.upc.superherocompose.data.remote.toHero
import pe.edu.upc.superherocompose.domain.Hero

class HeroRepository(
    private val heroService: HeroService,
    private val heroDao: HeroDao
) {

    private suspend fun isFavorite(id: String): Boolean = withContext(Dispatchers.IO) {
        heroDao.fetchHeroById(id)?.let {
            return@withContext true
        }
        return@withContext false
    }

    suspend fun searchHero(name: String): Resource<List<Hero>> = withContext(Dispatchers.IO) {

        val response = heroService.searchHero(name)
        if (response.isSuccessful) {

            response.body()?.heroes?.let { heroesDto ->
                val heroes = mutableListOf<Hero>()
                heroesDto.forEach { heroDto: HeroDto ->
                    val hero = heroDto.toHero()
                    hero.isFavorite = isFavorite(hero.id)
                    heroes.add(hero)
                }
                return@withContext Resource.Success(data = heroes)
            }
            return@withContext Resource.Error(
                message = response.body()?.error ?: "An error occurred"
            )

        }
        return@withContext Resource.Error(message = response.message())

    }

    suspend fun insertHero(hero: Hero) = withContext(Dispatchers.IO) {
        heroDao.insert(HeroEntity(hero.id, hero.name, hero.fullName, hero.poster))
    }

    suspend fun deleteHero(hero: Hero) = withContext(Dispatchers.IO) {
        heroDao.delete(HeroEntity(hero.id, hero.name, hero.fullName, hero.poster))
    }
}