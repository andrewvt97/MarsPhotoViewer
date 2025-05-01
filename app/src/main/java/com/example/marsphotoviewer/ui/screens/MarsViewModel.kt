package com.example.marsphotoviewer.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphotoviewer.MarsPhotoViewerApplication
import com.example.marsphotoviewer.data.MarsPhotosRepository
import com.example.marsphotoviewer.data.NetworkMarsPhotosRepository
import com.example.marsphotoviewer.network.MarsPhoto
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MarsUiState{
    data class Success(val photos: List<MarsPhoto>) : MarsUiState
    object Error: MarsUiState
    object Loading: MarsUiState
}

class MarsViewModel (private val marsPhotosRepository: MarsPhotosRepository) : ViewModel() {
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    init {
        getMarsPhotos()
    }

    companion object {
        val Factory:ViewModelProvider.Factory = viewModelFactory{
            initializer{
                val application = (this[APPLICATION_KEY]
                        as MarsPhotoViewerApplication)
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotosRepository = marsPhotosRepository)
            }
        }
    }

    private fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState =  try{
                val listResult = marsPhotosRepository.getMarsPhotos()
                MarsUiState.Success(listResult)
            }
            catch (e: IOException){
                MarsUiState.Error
            }

        }
    }
}