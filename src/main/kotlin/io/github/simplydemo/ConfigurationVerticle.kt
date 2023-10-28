package io.github.simplydemo

import io.github.simplydemo.utils.JsonResolver
import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.AbstractVerticle
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj


class ConfigurationVerticle(private var config: HashMap<String, Any>) : AbstractVerticle() {
    constructor() : this(HashMap<String, Any>())

    companion object {
        const val CHANNEL = "config.channel"
        const val PROFILE = "vertx.profiles.active"
    }

    override fun start() {
        val eventBus = vertx.eventBus()
        val profile = System.getProperty(PROFILE, "default")
        val options = ConfigStoreOptions().setType("file").setFormat("yaml")
            .setConfig(
                json {
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
                val json = ar.result()
                println("profile: $profile")
                config = JsonResolver(HashMap<String, Any>(), profile).get(json)
                // val configMap: String = JsonObject(config).encode() // JsonObject(config).encodePrettily()
                // println("configMap: $configMap")
                // vertx.eventBus().publish(CHANNEL, config, DeliveryOptions().)
                // eventBus.send(CHANNEL, configMap)
                eventBus.send(CHANNEL, "initialized configurationVerticle")
            }
        }

    }

    fun getConfigMap(): HashMap<String, Any> {
        return this.config
    }
}
