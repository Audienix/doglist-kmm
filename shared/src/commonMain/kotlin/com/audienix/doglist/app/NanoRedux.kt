package com.audienix.doglist.app

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface State
interface Action
interface Effect

/**
 * An interface representing a store for managing state, actions, and effects.
 *
 * @param S The state type.
 * @param A The action type.
 * @param E The effect type.
 */
interface Store<S : State, A : Action, E : Effect> {
    /**
     * Observe the current state of the store as a [StateFlow].
     *
     * @return A [StateFlow] representing the current state.
     */
    fun observeState(): StateFlow<S>

    /**
     * Observe the side effects produced by actions as a [Flow].
     *
     * @return A [Flow] representing the side effects.
     */
    fun observeSideEffect(): Flow<E>

    /**
     * Dispatch an action to the store, potentially triggering state changes and side effects.
     *
     * @param action The action to be dispatched.
     */
    fun dispatch(action: A)
}