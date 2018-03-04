package com.playlistappkotlin.di.api

import javax.inject.Qualifier

/**
 * Allows for Dagger to precisely identify a Class.
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApiHttpClient