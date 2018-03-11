package com.playlistappkotlin.di.component

import com.playlistappkotlin.di.PerService
import com.playlistappkotlin.di.module.ServiceModule
import dagger.Component

@PerService
@Component(dependencies = [(ApplicationComponent::class)], modules = [(ServiceModule::class)])
interface ServiceComponent//    void inject(GcmIntentService service);