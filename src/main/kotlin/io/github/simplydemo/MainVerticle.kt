package io.github.simplydemo

import io.github.simplydemo.handler.LottoObjectRoutingHandler
import io.vertx.core.AbstractVerticle
import io.vertx.core.http.HttpHeaders.CONTENT_TYPE
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.CorsHandler

class MainVerticle : AbstractVerticle() {

    override fun start() {

        val eventBus = vertx.eventBus()

        val router: Router = Router.router(vertx)
        router.route().handler(
            CorsHandler.create().addOrigin("*")
                .allowedMethods(setOf(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS, HttpMethod.PUT))
                .allowedHeaders(setOf(CONTENT_TYPE.toString()))
        )

        router.route("/health").handler { rc -> rc.response().setStatusCode(200).end("OK") }
        router.route("/lotto/v1/take").handler(LottoObjectRoutingHandler.TAKE_ONE)
        router.route("/lotto/v1/takes/:num").handler(LottoObjectRoutingHandler.TAKE_NUMBERS)

        val configurationVerticle = ConfigurationVerticle()
        vertx.deployVerticle(configurationVerticle) {
            if (it.succeeded()) {
                println("ConfigurationVerticle running ......")
            } else {
                println("ConfigurationVerticle failed to deploy: ${it.cause()}")
            }
        }

        eventBus.consumer<HashMap<String, Any>>(ConfigurationVerticle.CHANNEL) { message ->
            println("Received message: ${message.body()}")
            val configMap = configurationVerticle.getConfigMap()
            val host = configMap.getOrDefault("server.host", "0.0.0.0") as String
            val port = configMap.getOrDefault("server.port", 8080) as Int
            vertx.createHttpServer().requestHandler(router).listen(port, host) { result ->
                if (result.succeeded()) {
                    println("Server started on [host: $host , port $port]")
                } else {
                    println("Server failed to start")
                }
            }
        }

    }

}
