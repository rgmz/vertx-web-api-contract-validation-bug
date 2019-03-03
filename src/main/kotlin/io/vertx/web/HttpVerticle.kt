package io.vertx.web

import io.vertx.ext.web.api.contract.RouterFactoryOptions
import io.vertx.kotlin.core.http.listenAwait
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.ext.web.api.contract.openapi3.OpenAPI3RouterFactory

class HttpVerticle : CoroutineVerticle() {
    override suspend fun start() {
        val routerFactory = OpenAPI3RouterFactory.createAwait(vertx, "petstore.yaml")
        routerFactory.addHandlerByOperationId("listPets") { it.response().end() }

        val factoryOptions = RouterFactoryOptions()
            .setMountResponseContentTypeHandler(true)
        val router = routerFactory.setOptions(factoryOptions).router

        vertx.createHttpServer()
            .requestHandler(router)
            .listenAwait(8080)
    }
}

