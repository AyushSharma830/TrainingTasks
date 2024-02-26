
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import resource.SchoolResource
import resource.StudentResource
import java.net.URI

fun main() {
    val serverURI = URI.create("http://localhost:8080/")

    val studentResource = StudentResource()
    val schoolResource = SchoolResource()
    val resourceConfig = ResourceConfig()
        .register(studentResource)
        .register(schoolResource)

    val server = GrizzlyHttpServerFactory.createHttpServer(serverURI,resourceConfig)

    println("Server Started. Press Ctrl + C to stop")
    readLine()

    server.shutdownNow()
}

