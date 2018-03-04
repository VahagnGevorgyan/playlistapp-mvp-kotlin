package com.playlistappkotlin.di.component

import com.playlistappkotlin.di.PerService
import dagger.Component

@PerService
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ServiceModule::class))
interface ServiceComponent//    void inject(GcmIntentService service);