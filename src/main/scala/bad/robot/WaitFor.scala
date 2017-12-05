package bad.robot

import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeoutException

import scala.concurrent.duration.Duration

object WaitFor {
  val DefaulSleepPeriod = Duration(50, MILLISECONDS)

  def waitOrTimeout(condition: => Boolean, timeout: Timeout): Unit = {
    waitOrTimeout(condition, timeout, DefaulSleepPeriod)
  }

  def waitOrTimeout(condition: => Boolean, timeout: Timeout, sleep: Duration): Unit = {
    if (!success(condition, timeout, sleep)) throw new TimeoutException()
  }

  def waitOrTimeout[A, T](condition: => Boolean, onTimeout: => T, timeout: Timeout): Unit = {
    try
      waitOrTimeout(condition, timeout)
    catch {
      case e: TimeoutException => onTimeout
    }
  }

  def waitFor(condition: => Boolean, timeout: Timeout): Unit = {
    waitOrTimeout(condition, println(s"timed out after $timeout"), timeout)
  }

  def waitUntil(timeout: Timeout): Unit = {
    while (!timeout.hasExpired) {
      Thread.sleep(DefaulSleepPeriod.toMillis)
    }
  }

  private def success(condition: => Boolean, timeout: Timeout, sleep: Duration): Boolean = {
    while (!timeout.hasExpired) {
      if (condition) {
        println("ok: carry on")
        return true
      }
      println("failed: sleeping")
      Thread.sleep(DefaulSleepPeriod.toMillis)
    }
    false
  }
}
