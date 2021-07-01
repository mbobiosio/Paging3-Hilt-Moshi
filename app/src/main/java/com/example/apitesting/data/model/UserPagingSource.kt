package com.example.apitesting.data.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.apitesting.data.api.TestingApi
import retrofit2.Response

const val NETWORK_PAGE_SIZE = 10
private const val INITIAL_LOAD_SIZE = 0

class UserPagingSource(private val api: TestingApi, private val query: String? = null) :
    PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: INITIAL_LOAD_SIZE
        val offset =
            if (params.key != null) ((page - 1) * NETWORK_PAGE_SIZE) + 1 else INITIAL_LOAD_SIZE

        return try {

            // here response will be assigned based on if we have query or not

            val response: Response<ApiResponse> = if (query != null) {
                api.searchUser(offset = offset, limit = params.loadSize, query = query)
            } else {
                api.getAllUsers(offset = offset, limit = params.loadSize)
            }

            val nextKey = if (response.body()?.data?.users!!.isEmpty()) {
                null
            } else {
                page + (params.loadSize / NETWORK_PAGE_SIZE)
            }

            LoadResult.Page(
                data = response.body()?.data?.users!!,
                prevKey = null,
                nextKey = nextKey
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        } // catch closed


    } // load closed


} // UserPagingSource