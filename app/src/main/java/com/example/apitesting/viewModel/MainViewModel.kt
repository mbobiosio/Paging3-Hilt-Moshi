package com.example.apitesting.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.apitesting.repository.Repository
import com.example.testingapp.utils.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (private val repository: Repository,application: Application) : AndroidViewModel(application)
{


    // get All Users
    val allUsers  =  repository.getAllUsers().cachedIn(viewModelScope)


    private val currentQuery = MutableLiveData("")

    val getSearchedUsers  = currentQuery.switchMap()
    {
        Log.d(TAG, ": viewModel"+it)
        repository.searchUsers(it).cachedIn(viewModelScope)
    }

    fun searchUsers(query:String)
    {
        currentQuery.value = query
    } // searchUsers closed



} // MainViewModel closed