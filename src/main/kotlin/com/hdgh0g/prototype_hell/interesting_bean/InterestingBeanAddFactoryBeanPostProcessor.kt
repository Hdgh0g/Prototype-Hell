package com.hdgh0g.prototype_hell.interesting_bean

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.stereotype.Component

@Component
open class InterestingBeanAddFactoryBeanPostProcessor : BeanFactoryPostProcessor {

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        beanFactory.registerSingleton("interestingBean", object : InterestingBean {
            override fun doSomethingInteresting() {
                println("Singleton Names")
                beanFactory.singletonNames.forEach(::println)
            }
        })
    }
}
