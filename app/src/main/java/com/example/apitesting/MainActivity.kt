package com.example.apitesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apitesting.adapter.LoadingStateAdapter
import com.example.apitesting.adapter.UserAdapter
import com.example.apitesting.databinding.ActivityMainBinding
import com.example.apitesting.viewModel.MainViewModel
import com.example.testingapp.utils.Constants.TAG

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() , SearchView.OnQueryTextListener
{

    private var _binding: ActivityMainBinding?=null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private  lateinit var adapter:UserAdapter
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = UserAdapter(this,binding.buttonContinue)
        setupRecyclerView(binding.recycleViewMain)

    } // onCreate closed


    override fun onStart()
    {
        super.onStart()
        showAllUsers()
    } // onStart closed

    private fun showAllUsers()
    {
        viewModel.allUsers.observe({lifecycle})
        {
            lifecycleScope.launch{
                adapter.loadStateFlow.collect {
                    Log.d(TAG, "showAllUsers: "+adapter.itemCount)
                }
            }

            adapter.submitData(lifecycle,it)
        } // userObserver closed

    } // showAllUsers closed


    private fun setupRecyclerView(recycleViewMain: RecyclerView)
    {
        recycleViewMain.layoutManager = LinearLayoutManager(applicationContext)
        recycleViewMain.adapter = adapter.withLoadStateFooter(LoadingStateAdapter())
        recycleViewMain.addItemDecoration(
                DividerItemDecoration(
                        applicationContext,
                        resources.configuration.orientation
                )  // DividerItemDecoration closed
        ) // addItemDecoration closed
    } // setupRecyclerView closed


    // Search Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {

        menuInflater.inflate(R.menu.menu_search,menu)

        val menuItem = menu?.findItem(R.id.menuSearchUsers)
        val searchView = menuItem?.actionView  as? SearchView

        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)

    } // onCreate optionsMenu



    override fun onQueryTextSubmit(query: String?): Boolean
    {
        if(query!=null)
        {
            searchUser(query)
            Log.d(TAG, "onQueryTextSubmit: "+query)
        }
        return true
    }

    private fun searchUser(query: String)
    {
        viewModel.searchUsers(query)
        viewModel.getSearchedUsers.observe({lifecycle})
        {
            adapter.submitData(lifecycle,it)
        } // getSearchedUsers Observer closed

    } // searchUser closed


    override fun onQueryTextChange(newText: String): Boolean
    {
        if(newText.isEmpty())
        {
            showAllUsers()
        } // if closed
        return true
    } // onQueryTextChange closed




} // class closed