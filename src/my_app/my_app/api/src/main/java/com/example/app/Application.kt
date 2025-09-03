package com.example.app

import com.example.app.di.appModule
import com.example.app.route.apuRoutes
import com.example.app.route.authRoutes
import com.example.app.route.authorRoutes
import com.example.app.route.bbkRoutes
import com.example.app.route.bookRoutes
import com.example.app.route.favoriteRoutes
import com.example.app.route.issuanceRoutes
import com.example.app.route.publisherRoutes
import com.example.app.route.queueRoutes
import com.example.app.route.reservationRoutes
import com.example.app.route.userRoutes
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.netty.EngineMain
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.routing.routing
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) = EngineMain.main(args)


fun Application.module() {
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
    }
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
    routing {
        apuRoutes()
        authorRoutes()
        bbkRoutes()
        bookRoutes()
        publisherRoutes()
        userRoutes()
        reservationRoutes()
        queueRoutes()
        issuanceRoutes()
        favoriteRoutes()
        authRoutes()
    }
}

