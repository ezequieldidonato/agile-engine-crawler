package com.agileengine.crawler

import java.io.{BufferedWriter, File, FileWriter, IOException}

import com.agileengine.crawler.analyzer.Analyzer
import com.agileengine.crawler.parser.{JsoupCssSelectSnippet, JsoupFindByIdSnippet}
import com.typesafe.scalalogging.LazyLogging

import scala.util.{Failure, Success}

/**
  * Main bootstrap class.
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

  JsoupFindByIdSnippet.findElementById(new File(baseInputFile), baseId) match {
    case Failure(e) =>
      logger error s"Impossible to read $baseInputFile due to $e"
      System exit 2

    case Success(baseElement) =>
      logger info s"Base element found by id: $baseElement"

      JsoupCssSelectSnippet.findElementsByQuery(new File(file2Process), baseElement.tagName()) match {
        case Failure(e) =>
          logger error s"Impossible to read $file2Process due to $e"
          System exit 3

        case Success(elements) =>
          logger info s"Successfully read $file2Process"

          // Output
          val outputFileName = file2Process + TXT
          val bw = new BufferedWriter(new FileWriter(outputFileName))
          try {
            bw write "Button analysis listed from the highest score to be the right one to the lowest."
            (1 to 3).foreach(_ => bw newLine)

            Analyzer.getCandidatesFromElements(baseElement, elements).foreach(l => {
              bw write l.toString
              bw newLine

              bw write "Path to element: " + l.buildParentString
              (1 to 2).foreach(_ => bw newLine) // separates next element with another new line
            })
          } catch {
            case ioe: IOException =>
              logger error s"Error while writing to output file $outputFileName due to exception $ioe"
            case t: Throwable => logger error s"Unknown exception found while processing file: $t"
          } finally {
            if (bw != null) bw close
          }

          logger info s"Output successfully  saved to $outputFileName"

      }

      logger.info("Crawler finished.")
  }

}
