package modules

import constants.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ConfigModule {

    @Provides
    @Named("host")
    fun provideHost(): String {
        val hostName: String = Constants.HOST_NAME
        return hostName
    }

    @Provides
    @Named("port")
    fun providePort(): Int {
        val port: Int = Constants.PORT
        return port
    }

    // to do provide gson mapper
}