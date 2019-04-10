package com.agileengine.crawler.model

import scala.collection.mutable.ListBuffer

/**
  * Model for one HTML element considered a candidate for being the "make everything OK" button.
  */
class MakeOkCandidate(tag: String, id: String, classAtt: Set[String], href: String, text: String)
  extends Ordered [MakeOkCandidate] {

  // return 0 if the same; negative if this < that; positive if this > that
  def compare (that: MakeOkCandidate) = this.score compare that.score

  private val PARENT_SEP = " > "

  var score: Int = 0

  private[this] val parents: ListBuffer[String] = ListBuffer.empty[String]

  override def toString: String = s"tag: $tag id: $id classAtt: $classAtt href: $href text: $text score: $score"

  def addParent(parentTag: String) = parents + parentTag

  def addManyParents(parentTags: List[String]) = parents ++ parentTags

  def buildParentString: String = parents mkString PARENT_SEP

}
