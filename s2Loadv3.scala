package com.xxxxxxxxx.pt.xxxxxxg2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class BasexxxxxxG2Scenario4 extends Simulation {

   val httpProtocol = http
      .baseUrl("http://dev-abc-def.abcd.io")
      .disableWarmUp
      .disableCaching

   val scn = scenario("xxxxxxxxx xxxxxxG2 Test v4")
      .exec(
        http("GET request")
        .get("/")
        .check(status.is(200))
        )

}

class xxxxxxG2ScenarioTestV4 extends BasexxxxxxG2Scenario4 {
   setUp(
      scn.inject(
         constantUsersPerSec(900) during (1800 seconds) randomized
      ).protocols(httpProtocol)
   )
}
