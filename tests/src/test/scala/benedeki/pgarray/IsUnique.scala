/*
 * Copyright 2026 David Benedeki, All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package benedeki.pgarray

import org.postgresql.util.PSQLException
import za.co.absa.db.balta.DBTestSuite
import za.co.absa.db.balta.classes.setter.CustomDBType

class IsUnique extends DBTestSuite {

  test("Return true for unique values") {
    val array = CustomDBType("{1,2,3}", "INTEGER[]")
    val isUnique = function("pgarray.is_unique").setParam(array)
    isUnique.execute{qr =>
      val result = qr.next()
      assert(result.getBoolean("is_unique").contains(true))
    }
  }

  test("Return false is repeated values are present") {
    val array = CustomDBType("{alpha,beta,gama,alpha}", "TEXT[]")
    val isUnique = function("pgarray.is_unique").setParam(array)
    isUnique.execute{qr =>
      val result = qr.next()
      assert(result.getBoolean("is_unique").contains(false))
    }
  }

  test("Nulls are not considered distinct by default, return false if repeated") {
    val array = CustomDBType("{3.14,NULL,2.71,NULL}", "FLOAT[]")
    val isUnique = function("pgarray.is_unique")
      .setParam("i_array", array)

    isUnique.execute{qr =>
      val result = qr.next()
      assert(result.getBoolean("is_unique").contains(false))
    }
  }

  test("If nulls are considered distinct, return true even if they are repeated") {
    println("Testing with NULL values")
    val array = CustomDBType("""{"a", NULL, NULL, "b", "c"}""", "CHAR[]")
    val isUnique = function("pgarray.is_unique")
      .setParam("i_array", array)
      .setParam("i_nulls_distinct", true)
    isUnique.execute{qr =>
      val result = qr.next()
      assert(result.getBoolean("is_unique").contains(true))
    }
  }

  test("NULL is not accepted") {
    intercept[PSQLException](
      function("pgarray.is_unique")
        .setParamNull("i_array")
        .perform()
    )
  }

}