package bad.robot.parasol.site.page

object ExpenseCategories {
  
  class Category(val shortName: String) {
    def id = s"ctl00_ctl00_mainContent_MainContent_claimList_${shortName}_expenseAddItems"
    def description = s"ctl00_ctl00_mainContent_MainContent_claimList_${shortName}_summaryType"
    def details = s"ctl00_ctl00_mainContent_MainContent_claimList_${shortName}_detailsItems"
    def expander = s"ctl00_ctl00_mainContent_MainContent_claimList_${shortName}_accordionExpandImage"
  }
  case object Mileage extends Category("mileage")
  case object CarParking extends Category("carParking")
  case object ProfessionalDevelopment extends Category("professionalDevelopment")
  case object ElectronicEquipment extends Category("electronicEquipment")
  case object TravelAndCarHire extends Category("travelCarHire")
  case object Accommodation extends Category("accommodation")
  case object FoodAndDrink extends Category("foodAndDrink")
  case object Stationery extends Category("stationery")
  case object CallCosts extends Category("callCosts")
  case object ProfessionalSubscriptions extends Category("professionalSubscriptions")
  case object BusinessEntertainment extends Category("businessEntertainment")
  case object Postage extends Category("postage")
  case object SafetyEquipment extends Category("safetyEquipment")

  val all = List(
    Mileage, 
    CarParking, 
    ProfessionalDevelopment, 
    ElectronicEquipment, 
    TravelAndCarHire,
    Accommodation,
    FoodAndDrink, 
    Stationery, 
    CallCosts,
    ProfessionalSubscriptions, 
    BusinessEntertainment, 
    Postage, 
    SafetyEquipment
  )
}
