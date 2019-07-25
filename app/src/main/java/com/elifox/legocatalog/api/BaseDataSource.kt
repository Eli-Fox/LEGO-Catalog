package com.elifox.legocatalog.api

import com.elifox.legocatalog.data.Result
import retrofit2.Response

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected fun <T> getResult(response: Response<T>): Result<T> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return Result.success(body)
        }
        return Result.error("Network call has failed for a following reason:" +
                " ${response.code()} ${response.message()}")
    }
}
