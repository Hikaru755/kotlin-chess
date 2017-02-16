package com.rrpictureproductions.chess

operator fun <K1, K2, V> Map<K1, Map<K2, V>>.get(k1: K1, k2: K2): V? = get(k1)?.get(k2)

fun <T, U> Sequence<T>.crossproduct(other: Sequence<U>): Sequence<Pair<T, U>> = flatMap { t -> other.map { u -> t to u } }

/**
 * Dummy value to discard the value it's called on. Useful when you want to use an expression body with a function that
 * must return Unit:
 * ```kotlin
 * override fun foo() = bar.apply { /* something */ }.discard
 * ```
 * Or for the last line in a lambda that must return Unit.
 */
val Any?.discard: Unit
get() = Unit