import com.mongodb.MongoException
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import resource.SchoolResource
import resource.StudentResource
import java.net.URI
import constants.Constants

fun main() {
    try{
        val serverURI = URI.create("http://localhost:8080/")

        val studentResource = StudentResource()
        val schoolResource = SchoolResource()
        val resourceConfig = ResourceConfig()
            .register(schoolResource)
            .register(studentResource)

        val server = GrizzlyHttpServerFactory.createHttpServer(serverURI, resourceConfig)

        println("Server Started. Press Ctrl + C to stop")
        readLine()

        server.shutdownNow()
    }catch(e : IllegalArgumentException){
        throw(IllegalArgumentException(e.message))
    }
}

fun setupConnection(): MongoDatabase {
    return try {
        val connectString = System.getenv(Constants.CONNECTION_URI)

        val client = MongoClients.create(connectString)
        val database = client.getDatabase(Constants.DATABASE_NAME)

        println("You are successfully connected to MongoDB!")
        database
    } catch (e: MongoException) {
        throw (MongoException(e.message))
    }
}

