package bad.robot.parasol

import java.io.File
import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.Files

import bad.robot.parasol.site.domain.Claim
import argonaut.Argonaut._
import bad.robot._

object Save {
  private val downloadLocation = new File(System.getProperty("user.home")) / "Downloads"

  def save(claim: Claim) = {
    val target = claim.summary.period.map(_.toString()).getOrElse(claim.summary.period.left.get)
    val folder = downloadLocation / target

    if (!folder.exists())
      folder.mkdirs()

    val json = claim.jencode.spaces2
    Files.write((folder / "expenses.json").toPath, json.getBytes(UTF_8))
  }

}
