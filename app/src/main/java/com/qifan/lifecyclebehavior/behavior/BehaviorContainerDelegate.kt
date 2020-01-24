package com.qifan.lifecyclebehavior.behavior

import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun bindContainerToLifecycle(): BehaviorContainerDelegate = BehaviorContainerDelegate()

/**
 * A delegate that subscribe behavior container to lifecycle
 */
class BehaviorContainerDelegate: ReadOnlyProperty<LifecycleOwner, BehaviorContainer> {
    lateinit var behaviorContainer: BehaviorContainer

    /**
     * Operator that let you able to extend logic of delegated properties object
     * Called on the creation of property delegation host
     */
    operator fun provideDelegate(
        thisRef: LifecycleOwner,
        prop: KProperty<*>
    ): ReadOnlyProperty<LifecycleOwner, BehaviorContainer> {
        // Initialize the container from lifecycle owner
        behaviorContainer = BehaviorContainer(thisRef)
        // Subscribe this observer to lifecycle of the lifecycle owner
        thisRef.lifecycle.addObserver(behaviorContainer)
        // Create delegate
        return this
    }

    override fun getValue(thisRef: LifecycleOwner, property: KProperty<*>): BehaviorContainer {
        return behaviorContainer
    }
}