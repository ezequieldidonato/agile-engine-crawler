package com.agileengine.crawler

import java.io.File

import com.typesafe.scalalogging.LazyLogging

import scala.util.{Failure, Success}

/**
  * Main bootstrap class.
  */
object Crawler extends App with LazyLogging {

  private val BASE_ID = "make-everything-ok-button"

  val USAGE = "scala crawler.jar <input_origin_file_path> <input_other_sample_file_path>"
  val argsString = args mkString ", "
  logger.info(s"Crawler started with args $argsString ...")

  if (args.length != 2) {
    logger.error(s"Incorrect usage, please use: $USAGE")
    System exit 1
  }

  val baseInputFile = args(0)
  val file2Process = args(1)

  JsoupFindByIdSnippet.findElementById(new File(baseInputFile), BASE_ID) match {
    case Failure(e) =>
      logger.error(s"Impossible to read $baseInputFile due to $e")
      System exit 2
    case Success(baseElement) =>
      logger.info(s"Base element found by id: $baseElement")
      // TODO : continue
      logger.info("Crawler finished.")
  }

}
