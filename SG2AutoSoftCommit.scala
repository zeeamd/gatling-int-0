package com.xxxxxxxxx.pt.xxxxxxxx

import io.gatling.core.Predef._
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.http.Predef._

import scala.util.Random

class xxxxxxxxAutoSoftCommit extends Simulation {
  val GeneratedRecordCount: Int = 50
  val xxxxxxPauseTime: Int = 1000
  val xxxxCollectionName: String = "tp-live"

  val xxxUpdateEndpoint: String =
    s"https://xxx-xxx.xxxxxx.mxd.abcd.io/com.xxxxxxxxx.ppactlsub.services/collections/${xxxxCollectionName}/content/update"

  val xxxUpdateEndpointUsername: String = "user-name"
  val xxxUpdateEndpointPassword: String = "pw"

  val xxxxxxxxxxUrl: String =
    s"https://xxxxxxxx-restricted.xxxxxx.mxd.abcd.io/${xxxxCollectionName}/select/"

  val randomFeeder: Iterator[Map[String, String]] =
    Iterator.continually(Map("id" -> (Random.alphanumeric.take(20).mkString)))

  val lineFeeder: BatchableFeederBuilder[String] = csv(
    "enwiki.random.lines.csv"
  ).random

  val orQueryGenerator: String = {
    (for (n <- 1 to GeneratedRecordCount)
      yield "id:${id"+ n + "}").mkString(s"${xxxxxxxxxxUrl}?q=(", " OR ", ")&wt=json")
  }

  val scn =
    scenario("xxxxxxxx Autocommit")
      .feed(randomFeeder, GeneratedRecordCount)
      .feed(lineFeeder, GeneratedRecordCount)
      .exec(
        http("add a new record")
          .post(xxxUpdateEndpoint)
          .basicAuth(xxxUpdateEndpointUsername, xxxUpdateEndpointPassword)
          .header("Accept", "application/json")
          .body(ElFileBody(s"${GeneratedRecordCount}xxxxRecords.json"))
          .asJson
          .check(status.is(200))
      )
      .pause(xxxxxxPauseTime)
      .exec(
        http("check the new record has been added")
          .get(
            orQueryGenerator
          )
          .check(status.is(200))
          .check(
            jsonPath("$.response.numFound").ofType[Int].is(GeneratedRecordCount)
          )
      )
      .exec { session =>
        session
      }
  setUp(
    scn
      .inject(
        atOnceUsers(1)
      )
  )
}
