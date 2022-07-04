package com.xxxxxxxxx.pt.xxxxxxg2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class BasexxxxxxG2Scenario3 extends Simulation {

   val httpProtocol = http
      .shareConnections
      //.baseUrl("https://xxxxxxg2.xxxxxx.mxd.abcd.io/abc-live")
      .baseUrl("https://xxxxxxg2.xxxxxx.mxd.abcd.io/abc-cde-def")
      .disableWarmUp
      .disableCaching
      // .basicAuth("pcreviewer","Mtn4Explore")
      // .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.116 Safari/537.36")

   val headerBlock = Map(
      "Accept" -> "text/html")

   val feeder = csv("xxxxxxg2_urls_v0.csv").circular

   val links = exec(
      http("${URL}")
      .get("${URL}")
      .headers(headerBlock)
    ).pause(5)

   val scn = scenario("xxxxxxxxx xxxxxxG2 Test v0")
      .feed(feeder)
      .exec(links)

}

class xxxxxxG2ScenarioTestV3 extends BasexxxxxxG2Scenario3 {
   setUp(
      scn.inject(
         constantUsersPerSec(1) during (10 seconds) randomized
      ).protocols(httpProtocol)
   )
}
