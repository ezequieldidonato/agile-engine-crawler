Agile Engine Test
=================

Building
--------

sbt assembly

Running
--------

scala crawler.jar <input_origin_file_path> <input_other_sample_file_path> [element id]
Output will be left in a file named <input_other_sample_file_path>.txt

The output will will contain a list of possible candidates from <input_other_sample_file_path>
