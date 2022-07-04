package com.xxxxxxxxx.pt.xxxxxxg2

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasexxxxxxG2Scenario5 extends Simulation {

  val httpConf = http
    .shareConnections
    .baseUrl("http://xxxxxx-servers.xxxxxx.mxd.abcd.io/")
    .disableWarmUp
    .disableCaching
    // .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    // .doNotTrackHeader("1")
    // .acceptLanguageHeader("en-US,en;q=0.5")
    // .acceptEncodingHeader("gzip, deflate")
    // .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
    // .disableFollowRedirect

    val feeder = csv("coltermsV2.csv").circular

    val xxxxxxes = exec(
        http("xxxxxxing ${Collection}")
        .get("/${Collection}/select")
        .queryParam("q", "*")
        //.queryParam("rows", "10")
        //.queryParam("fl", "*")
        //.queryParam("wt", "json")
        //.queryParam("indent", "true")
    ).pause(1)

    val scn = scenario("Execute xxx request to run a xxxxxx")
        .feed(feeder)
        .exec(xxxxxxes)

}

class xxxxxxG2ScenarioTestV5 extends BasexxxxxxG2Scenario5 {
  setUp(
    scn.inject(
      constantUsersPerSec(500) during (300 seconds) randomized
      //rampConcurrentUsers(800) to (2000) during (5 minutes)
      //atOnceUsers(1200)
    ).protocols(httpConf)
  )
}
