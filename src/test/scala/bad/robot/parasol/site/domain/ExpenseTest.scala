package bad.robot.parasol.site.domain

import java.time.LocalDate

import org.specs2.mutable.Specification

class ExpenseTest extends Specification {

  "Create an expense from a date" >> {
    Expense("8th February 2017", "£25.33", None) must_== Expense(LocalDate.of(2017, 2, 8), 25.33D, None)
    Expense("08th February 2017", "£25.33", None) must_== Expense(LocalDate.of(2017, 2, 8), 25.33D, None)
    Expense("18th February 2017", "£25.33", None) must_== Expense(LocalDate.of(2017, 2, 18), 25.33D, None)
  }
  
  "Amounts" >> {
    Expense("8th February 2017", "£2.33", None) must_== Expense(LocalDate.of(2017, 2, 8), 2.33D, None)
    Expense("8th February 2017", "£25.33", None) must_== Expense(LocalDate.of(2017, 2, 8), 25.33D, None)
    Expense("8th February 2017", "£ 25.33", None) must_== Expense(LocalDate.of(2017, 2, 8), 25.33D, None)
    Expense("8th February 2017", "$25.33", None) must_== Expense(LocalDate.of(2017, 2, 8), 25.33D, None)
    Expense("8th February 2017", "25.33", None) must_== Expense(LocalDate.of(2017, 2, 8), 25.33D, None)
    Expense("8th February 2017", " 25.33", None) must_== Expense(LocalDate.of(2017, 2, 8), 25.33D, None)
  }
  
  "Bad amounts" >> {
    Expense("8th February 2017", "25.3", None) must_== Expense(LocalDate.of(2017, 2, 8), 0D, None)
    Expense("8th February 2017", "text", None) must_== Expense(LocalDate.of(2017, 2, 8), 0D, None)
  }
  
  "Last date in a list of dates is used (remainder are thrown away)" >> {
    Expense("7th, 8th, 10th June 2016", "£25.33", None) must_== Expense(LocalDate.of(2016, 6, 10), 25.33D, None)
  }

}
