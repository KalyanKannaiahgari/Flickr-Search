package com.flickrsearch.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flickrsearch.data.Constants
import com.flickrsearch.models.Item
import com.flickrsearch.models.Resource
import com.flickrsearch.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(private val searchRepository: SearchRepository) : ViewModel() {
        private var _queryText = mutableStateOf("")
        private val _photosResponse = MutableLiveData<Resource<List<Item>>>()
        private val _photoDetails = MutableLiveData<Item>()

        val queryText: State<String>
            get() = _queryText
        val photosResponse: LiveData<Resource<List<Item>>>
            get() = _photosResponse
        val photoDetails: LiveData<Item>
            get() = _photoDetails

        fun setQueryText(query: String) {
            _queryText.value = query
            if (_queryText.value.trim().isNotEmpty()) {
                searchPhotos()
            } else {
                _photosResponse.postValue(Resource.success(emptyList()))
            }
        }

        private fun searchPhotos() =
            viewModelScope.launch {
                val searchTerm = _queryText.value
                _photosResponse.postValue(Resource.loading(null))
                searchRepository.searchPhotos(convertToCommaSeparated(searchTerm)).let {
                    if (it.isSuccessful) {
                        val photosList = it.body()?.items ?: emptyList()
                        if (photosList.isNotEmpty()) {
                            _photosResponse.postValue(Resource.success(photosList))
                        } else {
                            _photosResponse.postValue(
                                Resource.error(
                                    String.format(
                                        Constants.ERROR_NO_PHOTOS,
                                        searchTerm,
                                    ),
                                    null,
                                ),
                            )
                        }
                    } else {
                        _photosResponse.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            }

        private fun convertToCommaSeparated(searchTerm: String): String = searchTerm.split(" ").joinToString(",") { it }

        fun setPhotoItem(photo: Item) {
            _photoDetails.postValue(photo)
        }
    }
