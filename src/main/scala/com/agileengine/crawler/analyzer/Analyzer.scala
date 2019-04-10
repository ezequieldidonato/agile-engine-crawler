package com.agileengine.crawler.analyzer

import scala.collection.JavaConverters._
import com.agileengine.crawler.model.MakeOkCandidate
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import scala.collection.mutable.ListBuffer

/**
  * Analyzer comparing elements in second file to the base element found in the base file.
  * @author Ezequiel Di Donato
  */
object Analyzer {

  private val HREF = "href"

  /**
    * From a list of possible html elements it will select and score suitable elements.
    * @param baseElement the element to match
    * @param otherElements possible element list to analyze against the base
    * @return a list of {@link MakeOkCandidate} models to output in result file.
    */
  def getCandidatesFromElements(baseElement: Element, otherElements: Elements): List[MakeOkCandidate] = {
    //initialize result
    val listBuffer: ListBuffer[MakeOkCandidate] = new ListBuffer[MakeOkCandidate]

    // build model from base element to compare others to
    val baseCandidate = new MakeOkCandidate(baseElement.tagName, baseElement.id, baseElement.classNames.asScala.toSet,
      baseElement.attr(HREF), baseElement.text)

    // for each candidate we calculate score and only output those having a score higher than 0 (threshould could be any)
    otherElements.forEach { e =>
      val otherCandidate = new MakeOkCandidate(e.tagName, e.id, e.classNames.asScala.toSet, e.attr(HREF), e.text)
      val score = calculateScoreInCandidate(baseCandidate, otherCandidate)
      if (score > 0) {
        otherCandidate.selector = e.cssSelector
        otherCandidate.score = score
        listBuffer += otherCandidate
      }
    }

    listBuffer.toList.sortWith(_ > _) // returns from highest score to lowest
  }

  /**
    * Calculate a score for a candidate element.
    * @param base model acting as the standard base element
    * @param otherCandidate other candidate model to compare to base
    * @return the candidate's score
    */
  def calculateScoreInCandidate(base: MakeOkCandidate, otherCandidate: MakeOkCandidate): Int = {
    // code could be improved with pattern matching, case classes and extractors...

    var score: Int = 0;

    // id analysis, matching gives high score 5 points.
    score += (if (base.id.toLowerCase.replaceAll("[\\s-_]","")
                  == otherCandidate.id.toLowerCase.replaceAll("[\\s-_]", "")) 5
              else 0)

    // class analysis
    score += base.classAtt.intersect(otherCandidate.classAtt).size // each matching class is one point

    // href analysis
    score += (if (base.href == otherCandidate.href) 2 // exact matching 2 points
              else 0)

    // text analysis
    score += (if (base.text equalsIgnoreCase otherCandidate.text) 3 // exact matching 3 points
    //  by word matching 1 point per word
    else base.id.toLowerCase.split("\\s").intersect(
         otherCandidate.id.toLowerCase().split("\\s")).size)

    score
  }

}
