package com.qifan.lifecyclebehavior.behavior

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
/**
 * A container that manage behaviors
 * Initialize behaviors when lifecycle has reached create state
 * Destroy behaviors when lifeycle is in destroyed state
 */
class BehaviorContainer(private val owner: LifecycleOwner) : LifecycleEventObserver {
    private val behaviors = LinkedHashSet<Lazy<LifecycleObserver>>()

    /**
     * Subscribe observers to lifecycle
     */
    private fun addBehaviors(source: LifecycleOwner) {
        behaviors
            .map { it.value }
            .forEach { behavior ->
                Log.d("BehaviorContainer", "Add observer $behavior")
                source.lifecycle.addObserver(behavior)
            }
    }

    /**
     * Unsubscribe observers from lifecycle
     */
    private fun removeBehaviors(source: LifecycleOwner) {
        behaviors
            .map { it.value }
            .forEach { behavior ->
                Log.d("BehaviorContainer", "Remove observer $behavior")
                source.lifecycle.removeObserver(behavior)
            }
        behaviors.clear()
    }

    /**
     * Listen on lifecycle changes
     */
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_CREATE) {
            Log.d("BehaviorContainer", "Create container")
            addBehaviors(source)
        } else if (source.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            removeBehaviors(source)
            owner.lifecycle.removeObserver(this)
            Log.d("BehaviorContainer", "Destroy container")
        }
    }

    /**
     * Lazily add behaviors to this container
     */
    fun addBehavior(behavior: Lazy<LifecycleObserver>) {
        behaviors.add(behavior)
    }
}