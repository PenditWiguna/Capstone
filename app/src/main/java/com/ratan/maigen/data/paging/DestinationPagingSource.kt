package com.ratan.maigen.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ratan.maigen.data.api.ApiServiceModel
import com.ratan.maigen.data.response.ListDestinationItem

class DestinationPagingSource(private val apiServiceModel: ApiServiceModel, val token: String) : PagingSource<Int, ListDestinationItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
        const val TAG = "DestinationPagingSource"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListDestinationItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiServiceModel.getRecommendations(token, page.toString())

            LoadResult.Page(
                data = responseData.listDestination,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (responseData.listDestination.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            Log.e(TAG, "Error paging: ${exception.localizedMessage}")
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListDestinationItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}