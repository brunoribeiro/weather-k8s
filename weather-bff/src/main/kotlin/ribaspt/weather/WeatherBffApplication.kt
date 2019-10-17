package ribaspt.weather

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import javax.servlet.*


@SpringBootApplication
class WeatherBffApplication

fun main(args: Array<String>) {
    runApplication<WeatherBffApplication>(*args)
}


@Component
class CorsFilter : Filter {

    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        val response = res as HttpServletResponse
        response.setHeader("Access-Control-Allow-Origin", "*")
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE")
        response.setHeader("Access-Control-Max-Age", "3600")
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With")
        chain.doFilter(req, res)
    }
}
@RestController
class WeatherController(val restConfig: RestConfig) {
    @RequestMapping("/{id}")
    fun weather(@PathVariable id: String): ResponseEntity<WeatherInfoResponse?>? {
        val restTemplate = CustomRestTemplate()
        return restTemplate
                .getForObject("${restConfig.geo?.url}?address=$id&key=${restConfig.geo?.key}", GeoResponse::class.java)
                ?.results
                ?.firstOrNull()
                ?.let { g ->
                    restTemplate
                            .getForObject("${restConfig.weather?.url}?lat=${g.geometry.location.lat}&lon=${g.geometry.location.lng}&key=${restConfig.weather?.key}", WeatherResponse::class.java)
                            ?.data
                            ?.random()
                            ?.let { w ->
                                restTemplate
                                        .getForObject("${restConfig.giphy?.url}?q=weather%20${w.weather.description}&api_key=${restConfig.giphy?.key}", GiphyResponse::class.java)
                                        ?.data
                                        ?.random()
                                        ?.images
                                        ?.get("original_mp4")
                                        ?.let { gh ->

                                            ResponseEntity(WeatherInfoResponse(g.geometry.location, w.temp, w.weather.description, gh["mp4"]), HttpStatus.OK)
                                        }


                            }

                }

    }
}

data class WeatherInfoResponse(val location: Location, val temp: String, val description: String, val image: String?)

class CustomRestTemplate : RestTemplate() {
    private val objectMapper: ObjectMapper = ObjectMapper()
            .registerModule(KotlinModule())

    override fun <T : Any?> getForEntity(url: String, responseType: Class<T>, vararg uriVariables: Any?): ResponseEntity<T> {
        return ResponseEntity.ok().body(
                objectMapper.readValue(super.getForObject(url, String::class.java), responseType)
        )
    }
}

data class GeoResponse(val results: List<GeoResult>)
data class GeoResult(val geometry: Geometry)
data class Geometry(val location: Location)
data class Location(val lat: String, val lng: String)

data class WeatherResponse(val data: List<WeatherResult>)
data class WeatherResult(val temp: String, val weather: Weather)
data class Weather(val description: String)


data class GiphyResponse(val data: List<GiphyResult>)
data class GiphyResult(val type: String, val images: Map<String, Map<String, String>>)

@Configuration
@ConfigurationProperties(prefix = "rest.clients")
class RestConfig {
    var geo: RestClientConfig? = null
    var weather: RestClientConfig? = null
    var giphy: RestClientConfig? = null
}

class RestClientConfig {
    var url: String? = null
    var key: String? = null
}
