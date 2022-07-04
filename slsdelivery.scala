package com.xxxxxxxxx.pt.serverless

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class BaseServerlessScenario extends Simulation {

   val httpProtocol = http
      .shareConnections
      .baseUrl("https://xxxxxxxxx-dev.pc-acs.com")
      .basicAuth("usr","pw")
      // .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.116 Safari/537.36")

   val headerBlock = Map(
      "Accept" -> "text/html")

   val feeder = csv("xxxxxxxxx_urls.csv").circular

   val links = exec(
      http("${URL}")
      .get("${URL}")
      .headers(headerBlock)
    ).pause(5)

   val scn = scenario("xxxxxxxxx serverless delivery test")
      .feed(feeder)
      .exec(links)
}

class ServerlessScenarioConstant extends BaseServerlessScenario {
   setUp(
      scn.inject(
         constantUsersPerSec(100) during (60 seconds) randomized,
         constantUsersPerSec(200) during (60 seconds) randomized,
         constantUsersPerSec(500) during (60 seconds) randomized
      ).protocols(httpProtocol)
   )
}

class ServerlessScenarioStandardRamp extends BaseServerlessScenario {
   setUp(
      scn.inject(
         rampUsersPerSec(   10) to ( 20000) during (600 seconds)
      ).protocols(httpProtocol)
   )
}

class ServerlessScenarioLogRamp extends BaseServerlessScenario {
   setUp(
      scn.inject(
         rampUsersPerSec(    1) to (    10) during (600 seconds),
         rampUsersPerSec(   10) to (   100) during (600 seconds),
         rampUsersPerSec(  100) to (  1000) during (600 seconds),
         rampUsersPerSec( 1000) to ( 10000) during (600 seconds)
      ).protocols(httpProtocol)
   )
}

class ServerlessScenarioLinearRampMaintain extends BaseServerlessScenario {
   setUp(
      scn.inject(
         rampUsersPerSec(   10) to ( 20000) during (600 seconds),
         constantUsersPerSec( 20000) during (600 seconds)
      ).protocols(httpProtocol)
   )
}
