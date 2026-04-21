package benedeki.pgutils

import za.co.absa.db.balta.DBTestSuite

class LogToConsole extends DBTestSuite{
  test("Syntax check with default parameter for timestamp") {
    val logToConsole = function("pgutils.log_to_console").setParam("Hello world!")
    logToConsole.perform()
  }

  test("Syntax check with timestamp off") {
    val logToConsole = function("pgutils.log_to_console")
      .setParam("Hello world!")
      .setParam(false)
    logToConsole.perform()
  }

}
