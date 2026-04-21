package benedeki.pgutils

import za.co.absa.db.balta.DBTestSuite

class LockMutex extends DBTestSuite {
  test("Syntax check") {
    val lockMutex = function("pgutils.lock_mutex").setParam("My lock name")
    lockMutex.perform()
  }
}
