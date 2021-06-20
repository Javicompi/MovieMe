package es.jnsoft.movieme.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import es.jnsoft.movieme.data.network.api.ApiService
import es.jnsoft.movieme.data.network.model.trend.Trend
import es.jnsoft.movieme.data.network.model.trend.TrendMediaType
import es.jnsoft.movieme.data.network.model.trend.TrendTimeWindow

class TrendingPagingSource(
    //private val service: ApiService,
    private val networkDataSource: NetworkDataSourceImpl,
    private val mediaType: TrendMediaType,
    private val timeWindow: TrendTimeWindow
) : PagingSource<Int, Trend>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Trend> {
        return try {
            val nextPage = params.key ?: 1
            val response = networkDataSource.getTrendingPage(
                mediaType = mediaType,
                timeWindow = timeWindow,
                page = nextPage
            )
            LoadResult.Page(
                data = response.body()!!.results,
                prevKey = null,
                nextKey = response.body()!!.page + 1
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Trend>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}