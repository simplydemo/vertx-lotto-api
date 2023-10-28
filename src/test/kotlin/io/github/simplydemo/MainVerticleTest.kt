package io.github.simplydemo

import io.vertx.core.Vertx
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(VertxExtension::class)
class MainVerticleTest {

    @Test
    @DisplayName("Test get operation from CommonHandler")
    @Throws(Throwable::class)
    fun testMainVerticle(vertx: Vertx, testContext: VertxTestContext) {
        vertx.deployVerticle(MainVerticle(), testContext.succeeding {
            // Deployment is asynchronous and this this callback will be called when it's complete
            testContext.completeNow()
        })
    }
//    @Test
//    @DisplayName("Test get operation from CommonHandler")
//    @Throws(Throwable::class)
//    fun test_get(vertx: Vertx, context: VertxTestContext) {
//        val webClient = WebClient.create(vertx)
//        vertx.deployVerticle(MainVerticle(), context.succeeding<String> { id: String? ->
//            webClient[MainVerticle.PORT, "localhost", java.lang.String.format("%s/hello", CommonHandler.BASE_URI)] //.as(BodyCodec.string())
//                    .send(context.succeeding<HttpResponse<Buffer?>> { res: HttpResponse<Buffer?> ->
//                        val user = res.bodyAsJson(User::class.java)
//                        LOG.info(user)
//                        context.verify {
//                            Assertions.assertEquals(res.statusCode(), 200)
//                            context.completeNow()
//                        }
//                    })
//        })
//    }
}