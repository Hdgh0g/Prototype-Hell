package com.hdgh0g.prototype_hell.inject_size

import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.locks.ReentrantLock

@Component
open class InjectSizeAnnotationBeanPostProcessor : BeanPostProcessor {

    private val COUNT_TO_CHANGE = 50

    private val infoMap : MutableMap<AnnotationInfo, SizeParams> = HashMap()
    private val lock = ReentrantLock()

    @Throws(BeansException::class)
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        bean.javaClass.fields
                .filter{ field -> field.isAnnotationPresent(InjectSize::class.java)}
                .forEach { field ->
                    val annotation = field.getAnnotation(InjectSize::class.java)
                    val computedSize = processAnnotation(annotation)
                    field.isAccessible = true
                    field.set(bean, computedSize)
                }
        return bean
    }

    private fun processAnnotation(annotation: InjectSize): Int {
        val annotationInfo = AnnotationInfo(annotation.min, annotation.max, annotation.step)
        val valueFromMap : Int?

        lock.lock() //need this
        if (infoMap.containsKey(annotationInfo)) {
            val oldParams = infoMap[annotationInfo]!!
            val newParams : SizeParams?
            if (oldParams.count == COUNT_TO_CHANGE) {
                val possibleNewSize = oldParams.size + annotationInfo.step
                val newSize = if (possibleNewSize > annotationInfo.max) annotationInfo.min else possibleNewSize
                newParams = SizeParams(0,  newSize)
            } else {
                newParams = SizeParams(oldParams.count + 1, oldParams.size)
            }
            infoMap.put(annotationInfo, newParams)
            valueFromMap =  newParams.size
        } else {
            infoMap.put(annotationInfo, SizeParams(0, annotationInfo.min))
            valueFromMap = annotationInfo.min
        }
        lock.unlock()

        return valueFromMap + Random().nextInt(valueFromMap / 10 * 2) - valueFromMap / 10
    }

}

private data class AnnotationInfo(val min : Int, val max : Int, val step : Int)
private data class SizeParams(val count : Int, val size : Int)