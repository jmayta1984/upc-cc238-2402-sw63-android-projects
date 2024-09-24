package pe.edu.upc.appturismo.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.appturismo.common.Resource
import pe.edu.upc.appturismo.data.remote.PackageDto
import pe.edu.upc.appturismo.data.remote.PackageService
import pe.edu.upc.appturismo.data.remote.toPackage

class PackageRepository(private val service: PackageService) {

    suspend fun getPackages(place: String, type: String) = withContext(Dispatchers.IO) {
        try {
            val response = service.getPackages(place, type)
            if (response.isSuccessful) {
                response.body()?.let { packagesDto: List<PackageDto> ->
                    val packages = packagesDto.map { packageDto: PackageDto ->
                        packageDto.toPackage()
                    }.toList()
                    return@withContext Resource.Success(data = packages)
                }
            }
            return@withContext Resource.Error(message = response.message())


        } catch (e: Exception) {
            return@withContext Resource.Error(message = e.message ?: "An exception occurred.")
        }
    }
}