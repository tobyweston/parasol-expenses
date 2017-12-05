package bad

import java.io.File

package object robot {

  implicit class FileOps(file: File) {
    def /(child: String): File = new File(file, child)
  }

}

