package utils

import java.nio.file.Files
import java.nio.file.Paths

fun getLinesFromResources(name: String): List<String> {
    val url = String.javaClass.classLoader.getResource(name)
    if (url != null) {
        val lines = Files.readAllLines(Paths.get(url.toURI()))
        return lines
    }
    return emptyList()
}

