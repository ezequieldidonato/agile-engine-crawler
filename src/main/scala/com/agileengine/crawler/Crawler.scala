package com.agileengine.crawler

import java.io.{File, IOException}

import com.agileengine.crawler.analyzer.Analyzer
import com.agileengine.crawler.output.OutputWriter
import com.agileengine.crawler.parser.{JsoupCssSelectSnippet, JsoupFindByIdSnippet}
import com.typesafe.scalalogging.LazyLogging

import scala.util.{Failure, Success}

/**
  * Main bootstrap class.
  * @author Ezequiel Di Donato
  */
object Crawler extends App with LazyLogging {

  private val BASE_ID = "make-everything-ok-button"
  private val TXT = ".txt"

  val USAGE = """scala crawler.jar <input_origin_file_path> <input_other_sample_file_path> [element id]
                |Output will be left in a file named <input_other_sample_file_path>.txt
              """ stripMargin

  val argsString = args mkString ", "

  logger info s"Crawler started with args $argsString ..."

  if (args.length < 2 || args.length > 3) {
    logger error s"Incorrect usage, please use: $USAGE"
    System exit 1
  }

  val baseInputFile = args(0)
  val file2Process = args(1)
  val baseId = if (args.length == 3) args(2) else BASE_ID

  // find base element by id in base html
  JsoupFindByIdSnippet.findElementById(new File(baseInputFile), baseId) match {
    case Failure(e) =>
      logger error s"Impossible to read $baseInputFile due to $e"
      System exit 2

    case Success(baseElement) =>
      logger info s"Base element found by id: $baseElement"

      try {
        // find and pre-filter all elements being of same tag as base element like <a>
        JsoupCssSelectSnippet.findElementsByQuery(new File(file2Process), baseElement.tagName()) match {
          case Failure(e) =>
            logger error s"Impossible to read $file2Process due to $e"
            System exit 3

          case Success(elements) =>
            logger info s"Successfully read $file2Process"

            // Analysis
            val candidates = Analyzer.getCandidatesFromElements(baseElement, elements)

            // Output
            OutputWriter.writeOutput(file2Process + TXT, candidates)
        }
      } catch {
        case ioe: IOException => logger error s"Error while analyzing file $file2Process due to $ioe"
      }

      logger.info("Crawler finished.")
  }

}
