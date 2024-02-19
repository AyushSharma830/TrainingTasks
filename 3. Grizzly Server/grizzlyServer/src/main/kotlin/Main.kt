package org.example

import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import java.net.URI

fun main() {
    val baseUri = URI.create("http://localhost:8080/")
//    val config = MyApplication()
    val server: HttpServer = GrizzlyHttpServerFactory.createHttpServer(baseUri)

    println("Server started. Press Enter to stop.")
    readLine() // Keep the program running until user input (Ctrl+C)
    server.shutdownNow()
}