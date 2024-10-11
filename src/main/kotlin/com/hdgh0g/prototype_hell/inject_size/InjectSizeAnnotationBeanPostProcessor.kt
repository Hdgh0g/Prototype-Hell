package com.hdgh0g.prototype_hell.inject_size

import java.util.*
import java.util.concurrent.locks.ReentrantLock
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import kotlin.concurrent.withLock

@Component
open class InjectSizeAnnotationBeanPostProcessor : BeanPostProcessor {

    private val infoMap: MutableMap<AnnotationInfo, SizeParams> = HashMap()
    private val lock = ReentrantLock()

    @Throws(BeansException::class)
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        bean.javaClass.fields
            .filter { field -> field.isAnnotationPresent(InjectSize::class.java) }
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
        val valueFromMap: Int

        lock.withLock {
            if (infoMap.containsKey(annotationInfo)) {
                val oldParams = infoMap.getValue(annotationInfo)
                val newParams: SizeParams = if (oldParams.count == COUNT_TO_CHANGE) {
                    val possibleNewSize = oldParams.size + annotationInfo.step
                    val newSize = if (possibleNewSize > annotationInfo.max) annotationInfo.min else possibleNewSize
                    SizeParams(0, newSize)
                } else {
                    SizeParams(oldParams.count + 1, oldParams.size)
                }
                infoMap[annotationInfo] = newParams
                valueFromMap = newParams.size
            } else {
                infoMap[annotationInfo] = SizeParams(0, annotationInfo.min)
                valueFromMap = annotationInfo.min
            }
        }

        return valueFromMap + Random().nextInt(valueFromMap / 10 * 2) - valueFromMap / 10
    }

    companion object {
        private const val COUNT_TO_CHANGE = 50
    }

}

private data class AnnotationInfo(val min: Int, val max: Int, val step: Int)
private data class SizeParams(val count: Int, val size: Int)