val <T, U, R, F: ((T, U) -> R)> F.curry: F
    get() = this
val <T, U, V, R, F: ((T, U, V) -> R)> F.curry: F
    get() = this
val <T, U, V, W, R, F: ((T, U, V, W) -> R)> F.curry: F
    get() = this
val <T, U, V, W, X, R, F: ((T, U, V, W, X) -> R)> F.curry: F
    get() = this

operator fun <T, U, R> ((T, U) -> R).invoke(t: T): (U) -> R {
    return { u: U -> this(t, u) }
}

operator fun <T, U, V, R> ((T, U, V) -> R).invoke(t: T): (U, V) -> R {
    return { u: U, v: V -> this(t, u, v) }
}
operator fun <T, U, V, R> ((T, U, V) -> R).invoke(t: T, u: U): (V) -> R {
    return { v: V -> this(t, u, v) }
}

operator fun <T, U, V, W, R> ((T, U, V, W) -> R).invoke(t: T): (U, V, W) -> R {
    return { u: U, v: V, w: W -> this(t, u, v, w) }
}
operator fun <T, U, V, W, R> ((T, U, V, W) -> R).invoke(t: T, u: U): (V, W) -> R {
    return {v: V, w: W -> this(t, u, v, w) }
}
operator fun <T, U, V, W, R> ((T, U, V, W) -> R).invoke(t: T, u: U, v: V): (W) -> R {
    return {w: W -> this(t, u, v, w) }
}

operator fun <T, U, V, W, X, R> ((T, U, V, W, X) -> R).invoke(t: T): (U, V, W, X) -> R {
    return { u: U, v: V, w: W, x: X -> this(t, u, v, w, x) }
}
operator fun <T, U, V, W, X, R> ((T, U, V, W, X) -> R).invoke(t: T, u: U): (V, W, X) -> R {
    return { v: V, w: W, x: X -> this(t, u, v, w, x) }
}
operator fun <T, U, V, W, X, R> ((T, U, V, W, X) -> R).invoke(t: T, u: U, v: V): (W, X) -> R {
    return { w: W, x: X -> this(t, u, v, w, x) }
}
operator fun <T, U, V, W, X, R> ((T, U, V, W, X) -> R).invoke(t: T, u: U, v: V, w: W): (X) -> R {
    return { x: X -> this(t, u, v, w, x) }
}