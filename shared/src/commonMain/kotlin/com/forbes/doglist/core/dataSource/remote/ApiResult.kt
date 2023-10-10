package com.forbes.doglist.core.dataSource.remote

sealed class ApiResult<T>(
        val data: T? = null,
        val errorCode: Int? = null
) {
    class Success<T>(data: T? = null) : ApiResult<T>(data)
    class Loading<T> : ApiResult<T>()
    class Error<T>(data: T? = null, errorCode: Int) : ApiResult<T>(data, errorCode)
}