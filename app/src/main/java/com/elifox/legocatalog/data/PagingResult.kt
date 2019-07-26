package com.elifox.legocatalog.data

/**
 * A class to notify UI about network paging Error or reaching end of data
 */

data class PagingResult(val status: Status, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        END_REACHED
    }

    companion object {
        fun success(): PagingResult {
            return PagingResult(Status.SUCCESS, null)
        }

        fun error(message: String): PagingResult {
            return PagingResult(Status.ERROR, message)
        }

        fun endReached(): PagingResult {
            return PagingResult(Status.END_REACHED, null)
        }
    }
}