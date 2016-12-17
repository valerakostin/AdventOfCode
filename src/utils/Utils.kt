package utils

import java.nio.file.Files
import java.nio.file.Paths
import java.security.MessageDigest

fun getLinesFromResources(name: String): List<String> {
    val url = String.javaClass.classLoader.getResource(name)
    if (url != null) {
        val lines = Files.readAllLines(Paths.get(url.toURI()))
        return lines
    }
    return emptyList()
}

fun computeMD5Hash(passCode: String): String {

    val inst = MessageDigest.getInstance("MD5")
    val byteArray = inst.digest(passCode.toByteArray())
    val hexString = byteArray.convertToHexString()

    return hexString
}


private fun ByteArray.convertToHexString(): String {
    val sb = StringBuilder(2 * this.size)
    for (b in this) {
        sb.append(String.format("%02x", b and 0xff.toByte()))
    }
    return sb.toString()
}


