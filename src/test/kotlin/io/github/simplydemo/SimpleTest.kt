package io.github.simplydemo

import io.github.simplydemo.utils.JsonResolver
import io.vertx.core.json.JsonObject
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*


class SimpleTest {


    @Test
    @Throws(Throwable::class)
    fun test_1001() {
        println("Hello World!")
    }


//    @Test
//    @Throws(Throwable::class)
//    fun `testReadFromYaml`() {
//
//    }


    @Test
    @Throws(Throwable::class)
    fun testReadFromJson() {

        val profile = System.getenv().getOrDefault("vertx.profiles.active", "dev")
        // File("src/test/resources/config2.json").writeText("HelloWorld!")

        val fileName = "src/test/resources/config.json"
        val fileContent = File(fileName).readText()
        val json = JsonObject(fileContent)
        println("JSON Object: $json")

        val properties: HashMap<String, Any> = JsonResolver(HashMap<String, Any>(), profile).get(json)
        println("properties --- " + properties)

    }

}