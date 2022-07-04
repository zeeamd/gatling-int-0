package com.xxxxxxxxx.pt.xxxxxxg2

import scala.util.Random

import io.gatling.core.Predef._
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.http.Predef._

import scala.concurrent.duration._

class xxxxxxG2AutoCommit extends Simulation {
  val GeneratedRecordCount: Int = 50
  val xxxxxxPauseTime: Int = 4
  val xxxxCollectionName: String = "pc-test"

  val xxxUpdateEndpoint: String =
    s"https://xxx-svc.xxxxxxxxx.net/com.xxxxxxxxx.ppactlsub.services/collections/${xxxxCollectionName}/content/update"

  val xxxUpdateEndpointUsername: String = "xxx-xxx"
  val xxxUpdateEndpointPassword: String = "<REPLACE ME>"

  val xxxxxxxxxxUrl: String =
    s"https://xxxxxxg2-restricted.xxxxxxxxx.net/${xxxxCollectionName}/select/"

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
    scenario("xxxxxxG2 Autocommit")
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
        //constantConcurrentUsers(10) during (60 seconds),
        //rampConcurrentUsers(10) to (1000) during (30 minutes)
        atOnceUsers(1)
      )
  )
}
