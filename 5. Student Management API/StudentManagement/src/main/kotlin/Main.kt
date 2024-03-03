import com.mongodb.MongoException
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoDatabase
import constants.Constants

fun main() {
    try{
        val appComponent = DaggerAppComponent.builder().build()

        val server = appComponent.server()
        server.start()

        println("Server Started. Press Ctrl + C to stop")
        readLine()

        server.shutdownNow()
    }catch(e : IllegalArgumentException){
        throw(IllegalArgumentException(e.message))
    }
}

fun setupConnection(client : MongoClient): MongoDatabase {
    return try {
        val database = client.getDatabase(Constants.DATABASE_NAME)

        println("You are successfully connected to MongoDB!")
        database
    } catch (e: MongoException) {
        throw (MongoException(e.message))
    }
}

