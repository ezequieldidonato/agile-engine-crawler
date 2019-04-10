package com.agileengine.crawler.model

/**
  * Model for one HTML element considered a candidate for being the "make everything OK" button.
  * @author Ezequiel Di Donato
  */
class MakeOkCandidate(val tag: String, val id: String, val classAtt: Set[String], val href: String, val text: String)
  extends Ordered [MakeOkCandidate] {

  // return 0 if the same; negative if this < that; positive if this > that
  def compare (that: MakeOkCandidate) = this.score compare that.score

  var score: Int = 0

  var selector: String = ""

  override def toString: String = s"tag: $tag, id: $id, classAtt: $classAtt, href: $href, text: $text, score: $score"

}
