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

package benedeki.pgutils

import za.co.absa.db.balta.DBTestSuite

class GlobalId extends DBTestSuite {
  test("Generates an id, syntax check") {
    val globalId = function("pgutils.global_id")
    globalId.execute{qr =>
      val result = qr.next()
      val id = result.getLong("global_id").get
      assert(id > 1000000000000000L,
        "Generated ID should be a positive number bigger thna the first block to skip 32 bit timestamp and 22 bit sequence number")
    }
  }

}