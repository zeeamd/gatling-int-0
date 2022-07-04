package com.xxxxxxxxx.pt.xxxxxxg2

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasexxxxxxG2Scenario extends Simulation {

  val httpConf = http
    .shareConnections
    .baseUrl("http://xxxxxxg2.xxxxxx.mxd.abcd.io/")
    // .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    // .doNotTrackHeader("1")
    // .acceptLanguageHeader("en-US,en;q=0.5")
    // .acceptEncodingHeader("gzip, deflate")
    // .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    // .disableFollowRedirect

    val feeder = csv("colterms.csv").circular

    val xxxxxxes = exec(
        http("xxxxxxing ${Collection}")
        .get("/${Collection}/select")
        .queryParam("q", "${Terms}")
        .queryParam("rows", "10")
        .queryParam("fl", "*")
        .queryParam("wt", "json")
        .queryParam("indent", "true")
    ).pause(1)

    val scn = scenario("Execute xxx request to run a xxxxxx")
        .feed(feeder)
        .exec(xxxxxxes)

}

class xxxxxxG2ScenarioTest extends BasexxxxxxG2Scenario {
  setUp(
    scn.inject(
      constantUsersPerSec(500) during (60 seconds) randomized,
      constantUsersPerSec(1000) during (60 seconds) randomized,
      constantUsersPerSec(2000) during (60 seconds) randomized

    ).protocols(httpConf)
  )
}

class xxxxxxG2ScenarioRamp extends BasexxxxxxG2Scenario {
  setUp(
    scn.inject(
      rampUsersPerSec(100) to (5000) during (900 seconds)
    ).protocols(httpConf)
  )
}

class xxxxxxG2Scenario extends BasexxxxxxG2Scenario {
  setUp(
    scn.inject(
      constantUsersPerSec(100) during (600 seconds) randomized,
      constantUsersPerSec(200) during (600 seconds) randomized,
      constantUsersPerSec(500) during (600 seconds) randomized,
      constantUsersPerSec(1000) during (600 seconds) randomized,
      constantUsersPerSec(2000) during (600 seconds) randomized,
      constantUsersPerSec(5000) during (600 seconds) randomized,
      constantUsersPerSec(10000) during (600 seconds) randomized,
      constantUsersPerSec(20000) during (600 seconds) randomized

    ).protocols(httpConf)
  )
}
