package com.hdgh0g.prototype_hell.inject_size

import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import java.util.*

@Component
open class InjectSizeAnnotationBeanPostProcessor : BeanPostProcessor {

    private val COUNT_TO_CHANGE = 50

    private val infoMap : MutableMap<AnnotationInfo, SizeParams> = HashMap()

    @Throws(BeansException::class)
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        bean.javaClass.fields
                .map { field -> Pair(field, field.getAnnotation(InjectSize::class.java)) }
                .filter { it.second != null }
                .forEach { pair ->
                    val computedSize = processAnnotation(pair.second)
                    val field = pair.first
                    field.isAccessible = true
                    field.set(bean, computedSize)
                }
        return bean
    }

    private fun processAnnotation(annotation: InjectSize): Int {
        val annotationInfo = AnnotationInfo(annotation.min, annotation.max, annotation.step)
        val valueFromMap : Int?
        if (infoMap.containsKey(annotationInfo)) {
            val oldParams = infoMap[annotationInfo]!!
            val newParams : SizeParams?
            if (oldParams.count == COUNT_TO_CHANGE) {
                println("")
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
        return valueFromMap + Random().nextInt(valueFromMap / 10 * 2) - valueFromMap / 10
    }

}

private data class AnnotationInfo(val min : Int, val max : Int, val step : Int)
private data class SizeParams(val count : Int, val size : Int)