package com.agileengine.crawler.output

import java.io.{BufferedWriter, FileWriter, IOException}

import com.agileengine.crawler.model.MakeOkCandidate
import com.typesafe.scalalogging.LazyLogging

/**
  * Output object to format and render results into a file.
  * @author Ezequiel Di Donato
  */
object OutputWriter extends LazyLogging {

  /**
    * Write the list of candidates to file.
    * @param outputFileName filename to store results
    * @param candidates list of candidate models
    */
  def writeOutput(outputFileName: String, candidates: List[MakeOkCandidate]) = {
    val bw = new BufferedWriter(new FileWriter(outputFileName))

    try {
      bw write "Button analysis listed from the highest score to be the right one to the lowest."
      (1 to 3).foreach(_ => bw newLine)

      // Analysis and fetching candidates
      candidates.foreach(l => {
        bw write l.toString
        bw newLine

        bw write "Path to element: " + l.selector
        (1 to 2).foreach(_ => bw newLine) // separates next element with another new line
      })

    } catch {
      case ioe: IOException =>
        logger error s"Error while writing to output file $outputFileName due to exception $ioe"

    } finally {
      if (bw != null)
        try {
          bw close;
          logger info s"Output successfully  saved to $outputFileName"
        }
        catch {
          case ioe: IOException =>
            logger error s"Error while closing file $outputFileName due to exception $ioe"
        }
    }
  }

}
