package bad.robot.parasol.site.page

object ExpenseCategories {

  val all = List(Mileage, CarParking, ProfessionalDevelopment, ElectronicEquipment, TravelAndCarHire, Stationery, ProfessionalSubscriptions, BusinessEntertainment, Postage, SafetyEquipment)

  class Category(val shortName: String) {
    def id = s"ctl00_ctl00_mainContent_MainContent_claimList_${shortName}_expenseAddItems"
    def description = s"ctl00_ctl00_mainContent_MainContent_claimList_${shortName}_summaryType"
    def details = s"ctl00_ctl00_mainContent_MainContent_claimList_${shortName}_detailsItems"
  }
  case object Mileage extends Category("mileage")
  case object CarParking extends Category("carParking")
  case object ProfessionalDevelopment extends Category("professionalDevelopment")
  case object ElectronicEquipment extends Category("electronicEquipment")
  case object TravelAndCarHire extends Category("travelCarHire")
  case object Accommodation extends Category("accommodation")
  case object FoodAndDrink extends Category("foodAndDrink")
  case object Stationery extends Category("stationery")
  case object CallCosts extends Category("callCost")
  case object ProfessionalSubscriptions extends Category("professionalSubscriptions")
  case object BusinessEntertainment extends Category("businessEntertainment")
  case object Postage extends Category("postage")
  case object SafetyEquipment extends Category("safetyEquipment")

}
