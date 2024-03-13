import javax.inject.Singleton
import dagger.*
import modules.*
import org.glassfish.grizzly.http.server.HttpServer


@Singleton
@Component(modules = [
    DatabaseModule::class,
    HttpModule::class,
    ConfigModule::class
])
interface AppComponent {
    fun server() : HttpServer
}
