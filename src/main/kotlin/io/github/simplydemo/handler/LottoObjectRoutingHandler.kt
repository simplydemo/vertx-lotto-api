package io.github.simplydemo.handler

import io.vertx.core.Handler
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext
import java.util.stream.Collectors

object LottoObjectRoutingHandler {
    private fun generate(): MutableList<Int> {
        val numbers = (1..45).toMutableList()
        numbers.shuffle()
        return numbers.stream().limit(6).sorted().collect(Collectors.toList())
    }

    val TAKE_ONE: Handler<RoutingContext> = Handler { context ->
        val result = generate()
        context.response().putHeader("content-type", "application/json").setStatusCode(200)
            .end(Json.encodePrettily(result))
    }

    val TAKE_NUMBERS: Handler<RoutingContext> = Handler { context ->
        val num: Int = context.pathParam("num").toInt()
        val result = mutableListOf<MutableList<Int>>()
        for (i in 1..num) {
            result.add(generate())
        }
        context.response().putHeader("content-type", "application/json").setStatusCode(200)
            .end(Json.encodePrettily(result))
    }


}