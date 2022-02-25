package com.movieapps.mobile.coreandroid.exception

sealed class Failure {
    object NetworkException : Failure()
    data class ServerError(val message: String?) : Failure()
    object LocalDataNotFound : Failure()
}