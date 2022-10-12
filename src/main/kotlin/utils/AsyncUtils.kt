package utils

import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

fun awaitAll(vararg suppliers: Supplier<out Any>) {
    val futures = suppliers.map {
        CompletableFuture.supplyAsync(it)
    }.toTypedArray()
    CompletableFuture.allOf(*futures).join()
}