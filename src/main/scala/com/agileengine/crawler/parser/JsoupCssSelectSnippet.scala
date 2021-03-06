package com.agileengine.crawler.parser

import java.io.File

import org.jsoup.Jsoup
import org.jsoup.select.Elements

import scala.util.Try

/**
  * Object for finding elements by query.
  * @author AgileEngine snippet
  */
object JsoupCssSelectSnippet {

  // Jsoup requires an absolute file path to resolve possible relative paths in HTML,
  // so providing InputStream through classpath resources is not a case
  //val resourcePath = "./samples/startbootstrap-freelancer-gh-pages-cut.html"
  //val cssQuery = "div[id=\"success\"] button[class*=\"btn-primary\"]"

  /**
    * Find base element by id exact match.
    *
    * @param htmlFile  input file to analyze.
    * @param cssQuery css query to get elements from input file.
    * @return a {@link Try} of {@link Elements} on success.
    */
  def findElementsByQuery(htmlFile: File, cssQuery: String): Try[Elements] = Try {
    Jsoup.parse(htmlFile, CHARSET_NAME, htmlFile.getAbsolutePath)
  }.map(_.select(cssQuery))

}
