package com.qifan.lifecyclebehavior.behavior

import androidx.lifecycle.LifecycleObserver
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

typealias BehaviorDelegateFactory<T> = () -> T

fun <T> behaviorFactory(callback: BehaviorDelegateFactory<T>): BehaviorDelegateProvider<T> where T : LifecycleObserver =
    BehaviorDelegateProvider(callback)

/**
 * A delegate that automatically add a behavior to a behavior container
 */
class BehaviorDelegateProvider<T>(private val callback: BehaviorDelegateFactory<T>) : ReadOnlyProperty<BehaviorSubscriber, T> where T : LifecycleObserver  {
    private lateinit var lazyBehavior: Lazy<T>

    /**
     * Operator that let you able to extend logic of delegated properties object
     * Called on the creation of property delegation host
     */
    operator fun provideDelegate(
        thisRef: BehaviorSubscriber,
        prop: KProperty<*>
    ): ReadOnlyProperty<BehaviorSubscriber, T> {
        // Lazily resolve delegate factory
        lazyBehavior = lazy(callback)
        // Add lazily behavior to container
        thisRef.behaviorContainer.addBehavior(lazyBehavior)
        // Create delegate
        return this
    }

    override fun getValue(thisRef: BehaviorSubscriber, property: KProperty<*>): T {
        return lazyBehavior.value
    }
}