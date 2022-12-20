
operator fun <T, R> ((T) -> R).get(t: T): () -> R {
    return { this(t) }
}

operator fun <T, U, R> ((T, U) -> R).get(t: T): (U) -> R {
    return { u: U -> this(t, u) }
}
operator fun <T, U, R> ((T, U) -> R).get(t: T, u: U): () -> R {
    return { this(t, u) }
}

operator fun <T, U, V, R> ((T, U, V) -> R).get(t: T): (U, V) -> R {
    return { u: U, v: V -> this(t, u, v) }
}
operator fun <T, U, V, R> ((T, U, V) -> R).get(t: T, u: U): (V) -> R {
    return { v: V -> this(t, u, v) }
}
operator fun <T, U, V, R> ((T, U, V) -> R).get(t: T, u: U, v: V): () -> R {
    return { this(t, u, v) }
}

operator fun <T, U, V, W, R> ((T, U, V, W) -> R).get(t: T): (U, V, W) -> R {
    return { u: U, v: V, w: W -> this(t, u, v, w) }
}
operator fun <T, U, V, W, R> ((T, U, V, W) -> R).get(t: T, u: U): (V, W) -> R {
    return {v: V, w: W -> this(t, u, v, w) }
}
operator fun <T, U, V, W, R> ((T, U, V, W) -> R).get(t: T, u: U, v: V): (W) -> R {
    return {w: W -> this(t, u, v, w) }
}
operator fun <T, U, V, W, R> ((T, U, V, W) -> R).get(t: T, u: U, v: V, w: W): () -> R {
    return { this(t, u, v, w) }
}

operator fun <T, U, V, W, X, R> ((T, U, V, W, X) -> R).get(t: T): (U, V, W, X) -> R {
    return { u: U, v: V, w: W, x: X -> this(t, u, v, w, x) }
}
operator fun <T, U, V, W, X, R> ((T, U, V, W, X) -> R).get(t: T, u: U): (V, W, X) -> R {
    return { v: V, w: W, x: X -> this(t, u, v, w, x) }
}
operator fun <T, U, V, W, X, R> ((T, U, V, W, X) -> R).get(t: T, u: U, v: V): (W, X) -> R {
    return { w: W, x: X -> this(t, u, v, w, x) }
}
operator fun <T, U, V, W, X, R> ((T, U, V, W, X) -> R).get(t: T, u: U, v: V, w: W): (X) -> R {
    return { x: X -> this(t, u, v, w, x) }
}
operator fun <T, U, V, W, X, R> ((T, U, V, W, X) -> R).get(t: T, u: U, v: V, w: W, x: X): () -> R {
    return { this(t, u, v, w, x) }
}