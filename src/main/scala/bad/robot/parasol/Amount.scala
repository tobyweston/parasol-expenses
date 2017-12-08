package bad.robot.parasol

object Amount {
  def parse(amount: String) = """\d*\.\d{2}""".r.findFirstIn(amount).map(_.toDouble).getOrElse(0D)
}
