package modules

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import constants.Constants
import dagger.*
import setupConnection
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMongoClient(): MongoClient {
        val connectString = System.getenv(Constants.CONNECTION_URI)
        return MongoClients.create(connectString)
    }

    @Provides
    @Singleton
    fun provideMongoDatabase(client: MongoClient): MongoDatabase {
        return setupConnection(client)
    }
}

