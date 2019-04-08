package com.agileengine.crawler

import java.io.File

import org.jsoup.nodes.Element

object Crawler extends App {

  val USAGE = "<platform> <program_path> <input_origin_file_path> <input_other_sample_file_path>"
  val argsString = args mkString ", "
  printf(s"Crawler started with args $argsString ...")

  if (args.length < 4) {
    println(s"Incorrect usage, please use: $USAGE", USAGE)
    System exit 1
  }

  val baseInputFile = args(1)
  val file2Process = args(2)

  val elementAttempt = JsoupFindByIdSnippet.findElementById(new File(baseInputFile), "make-everything-ok-button")

  if (elementAttempt.isFailure) {
    println(s"Impossible to read $baseInputFile")
    System exit 2
  }

  val baseElement: Element = elementAttempt get

  printf("Crawler finished.")
}
