
import UserResource.UserResource
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import java.net.URI

fun main() {
    val serverURI = URI.create("http://localhost:8080/")

    val userResource = UserResource()
    val resourceConfig = ResourceConfig()
        .register(userResource)

    val server = GrizzlyHttpServerFactory.createHttpServer(serverURI,resourceConfig)

    println("Server Started. Press Ctrl + C to stop")
    readLine()

    server.shutdownNow()
}