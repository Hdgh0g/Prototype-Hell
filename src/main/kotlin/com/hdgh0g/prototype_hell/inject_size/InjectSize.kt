package com.hdgh0g.prototype_hell.inject_size

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD

@Target(FIELD)
@Retention(RUNTIME)
annotation class InjectSize(
    val min: Int = 100,
    val max: Int = 100,
    val step: Int = 0
)

