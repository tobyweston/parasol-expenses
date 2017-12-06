package bad.robot.parasol.site.domain

import java.time.LocalDate

import org.specs2.mutable.Specification

class ExpenseTest extends Specification {

  "Create an expense from a date" >> {
    Expense("8th February 2017", "£25.33", None) must_== Expense(LocalDate.of(2017, 2, 8), 25.33D, None)
    Expense("08th February 2017", "£25.33", None) must_== Expense(LocalDate.of(2017, 2, 8), 25.33D, None)
    Expense("18th February 2017", "£25.33", None) must_== Expense(LocalDate.of(2017, 2, 18), 25.33D, None)
  }

}
