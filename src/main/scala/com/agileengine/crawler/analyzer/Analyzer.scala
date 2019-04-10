package com.agileengine.crawler.analyzer

import scala.collection.JavaConverters._

import com.agileengine.crawler.model.MakeOkCandidate
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import scala.collection.mutable.ListBuffer

/**
  * Analyzer comparing elements in second file to the base element found in the base file.
  */
object Analyzer {

  private val HREF = "href"

  def getCandidatesFromElements(baseElement: Element, otherElements: Elements): List[MakeOkCandidate] = {
    val listBuffer: ListBuffer[MakeOkCandidate] = new ListBuffer[MakeOkCandidate]

    val baseCandidate = new MakeOkCandidate(baseElement.tagName, baseElement.id, baseElement.classNames.asScala.toSet,
      baseElement.attr(HREF), baseElement.text)

    otherElements.forEach { e =>
      val otherCandidate = new MakeOkCandidate(e.tagName, e.id, e.classNames.asScala.toSet, e.attr(HREF), e.text)
      // TODO: add proper logic
      otherCandidate addParent e.cssSelector
      otherCandidate.score += 1
      listBuffer += otherCandidate
    }

    listBuffer.toList.sortWith(_ > _) // returns from highest score to lowest
  }

}
