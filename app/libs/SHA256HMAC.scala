package libs

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object SHA256HMAC {
  /**
   * @param sharedSecret secret key used to hash string
   * @param preHashString string to be hashed
   * @return hashed string
   */
  def generateHMAC(sharedSecret: String, preHashString: String): String = {
    val secret = new SecretKeySpec(sharedSecret.getBytes, "SHA256")
    val mac = Mac.getInstance("SHA256")
    mac.init(secret)
    val hashString: Array[Byte] = mac.doFinal(preHashString.getBytes)
    new String(hashString.map(_.toChar))
  }
}
