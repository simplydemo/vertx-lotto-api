package io.github.simplydemo.utils

import io.vertx.core.json.JsonObject

class JsonResolver(val properties: HashMap<String, Any>, val profile: String) {

    private fun resolve(key: String, parentKey: String?, json: JsonObject?) {
        val value = json?.getValue(key)
        if (value is JsonObject) {
            value.fieldNames().forEach { k ->
                if (parentKey == null) {
                    resolve(k, key, value)
                } else {
                    if ("profiles" == parentKey && key == profile) {
                        resolve(k, null, value)
                    } else if ("profiles" != parentKey) {
                        resolve(k, "$parentKey.$key", value)
                    }
                }
            }
        } else {
            if (parentKey == null) {
                properties[key] = value as Any
            } else {
                properties["$parentKey.$key"] = value as Any
            }
        }
    }

    fun get(json: JsonObject): HashMap<String, Any> {
        json.fieldNames().forEach { k ->
            resolve(k, null, json)
        }
        return properties
    }
}
