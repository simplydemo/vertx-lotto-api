package io.github.simplydemo

import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(VertxExtension::class)
class ConfigurationVerticleTest {

    @BeforeEach
    fun deploy_verticle(vertx: Vertx, testContext: VertxTestContext) {/*
        vertx.deployVerticle(ConfigurationVerticle())
            .onSuccess { testContext.completeNow() }
            .onFailure { testContext.failNow(it) }

        vertx.deployVerticle(ConfigurationVerticle(), testContext.succeedingThenComplete())
        vertx.deployVerticle(ConfigurationVerticle(), testContext.succeeding<String> { _ -> testContext.completeNow() })
         */

        vertx.deployVerticle(ConfigurationVerticle()).onSuccess { testContext.completeNow() }
            .onFailure { testContext.failNow(it) }
    }


    @Test
    @DisplayName("Test vertx handler")
    @Throws(Throwable::class)
    fun testConfigurationVerticle(testContext: VertxTestContext) {
        testContext.completeNow()
    }

    @Test
    @DisplayName("Test get operation from CommonHandler")
    @Throws(Throwable::class)
    fun test_json(vertx: Vertx) {
        println("test_json")
        val options = ConfigStoreOptions().setType("file").setFormat("yaml").setConfig(json {
            obj(
                "path" to "application.yaml"
            )
        })

        val retrieverOptions = ConfigRetrieverOptions().addStore(options).setScanPeriod(10000)
        val configRetriever = ConfigRetriever.create(vertx, retrieverOptions)

        configRetriever.getConfig { ar ->
            if (ar.failed()) {
                println("Failed to retrieve the configuration")
            } else {
                println("AsyncResult")
                val config = ar.result()
                for (key in config.fieldNames()) {
                    println(key)
                }
            }
        }

        configRetriever.close()
    }

//    @Test
//    @DisplayName("Test get operation from CommonHandler")
//    @Throws(Throwable::class)
//    fun testConfigurationVerticle(vertx: Vertx, context: VertxTestContext) {
//        vertx.deployVerticle(ConfigurationVerticle(), DeploymentOptions().setMaxWorkerExecuteTime(1000).setWorker(true))
//            .onSuccess { context.completeNow() }
//            .onFailure { context.failNow(it) }
//        //.onComplete { vertx.close(context.succeeding { response -> context.completeNow() }) }
//        vertx.exceptionHandler().run {
//            println("")
//        }
//    }
}