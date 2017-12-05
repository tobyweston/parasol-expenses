package bad.robot

import java.time.Instant

import scala.concurrent.duration.Duration

case class Timeout(duration: Duration) {
  if (duration.toMillis <= 0) throw new IllegalArgumentException
  
  private val start = Instant.now()

  def hasExpired: Boolean = {
    Instant.now().minusMillis(start.toEpochMilli).toEpochMilli > duration.toMillis     
  }
}
