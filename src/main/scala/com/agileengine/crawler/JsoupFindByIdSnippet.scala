package com.agileengine.crawler

import java.io.File

import org.jsoup.Jsoup
import org.jsoup.nodes.Element

import scala.util.Try

/**
  * Object for finding base element by id.
  */
object JsoupFindByIdSnippet {

  // Jsoup requires an absolute file path to resolve possible relative paths in HTML,
  // so providing InputStream through classpath resources is not a case
  //val resourcePath = "./samples/startbootstrap-freelancer-gh-pages-cut.html"
  //val targetElementId = "sendMessageButton"

  private val CHARSET_NAME = "utf8"

  /**
    * Finds base element by id exact match.
    * @param htmlFile input file containing base element.
    * @param targetElementId id value to search for.
    * @return a {@link Try} of {@link Element} on success.
    */
  def findElementById(htmlFile: File, targetElementId: String): Try[Element] = Try {
    Jsoup.parse(htmlFile, CHARSET_NAME, htmlFile.getAbsolutePath)
  }.map(_.getElementById(targetElementId))

}

