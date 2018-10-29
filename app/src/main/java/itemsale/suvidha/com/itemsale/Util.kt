package itemsale.suvidha.com.itemsale

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Double.round2Decimal(): Double {
  return Math.round(this * 10.0) / 10.0
}


fun Date.convertToString(
  locale: Locale = Locale.getDefault()
): String {
  val formatter = SimpleDateFormat("dd/M/yyyy", locale)
  return formatter.format(this)
}

fun getCurrentDateTime(): Date {
  return Calendar.getInstance()
      .time
}

