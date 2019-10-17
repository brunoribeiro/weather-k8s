package ribaspt.k8s

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
class K8sApplication

fun main(args: Array<String>) {
    runApplication<K8sApplication>(*args)
}


@RestController
class K8sController {
    @RequestMapping("/")
    fun hello(): String = """
        Hello k8s!
      ${System.getenv("POD_NAME") ?: ""}
      ${System.getenv("POD_IP") ?: ""}
    """.trimIndent()
}
