package com.example.marsphotoviewer.data
import com.example.marsphotoviewer.network.MarsApiService
import com.example.marsphotoviewer.network.MarsPhoto

// makes network requests instead of the view model

interface MarsPhotosRepository {
    suspend fun getMarsPhotos(): List<MarsPhoto>
}

class NetworkMarsPhotosRepository (
    private val marsApiService: MarsApiService
) : MarsPhotosRepository{

    override suspend fun getMarsPhotos(): List<MarsPhoto> = marsApiService.getPhotos()
}