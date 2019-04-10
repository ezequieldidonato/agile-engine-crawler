Agile Engine Test
=================

Environment
------------

Scala 2.12

Sbt 1.2.8

Directories and Files
---------------------

* lib: contains needed local jars
* samples: contains sample html files to test
* src: source files
* crawler.jar: binary to execute

Building
--------

sbt assembly

Running
--------

`scala crawler.jar <input_origin_file_path> <input_other_sample_file_path> [element id]`

Output will be left in a file named <input_other_sample_file_path>.txt

The output will will contain a list of possible candidates from <input_other_sample_file_path>

Sample Output
-------------

`$ scala crawler.jar samples/base.html samples/othermany.html`

```
Apr 10, 2019 8:41:24 PM com.agileengine.crawler.Crawler$ delayedEndpoint$com$agileengine$crawler$Crawler$1
INFO: Crawler started with args samples/base.html, samples/othermany.html ...
Apr 10, 2019 8:41:24 PM com.agileengine.crawler.Crawler$ delayedEndpoint$com$agileengine$crawler$Crawler$1
INFO: Base element found by id: <a id="make-everything-ok-button" class="btn btn-sucess" title="Make-Button" href="#ok"> Make everything OK </a>
Apr 10, 2019 8:41:24 PM com.agileengine.crawler.Crawler$ delayedEndpoint$com$agileengine$crawler$Crawler$1
INFO: Successfully read samples/othermany.html
Apr 10, 2019 8:41:24 PM com.agileengine.crawler.output.OutputWriter$ writeOutput
INFO: Output successfully  saved to samples/othermany.html.txt
Apr 10, 2019 8:41:24 PM com.agileengine.crawler.Crawler$ delayedEndpoint$com$agileengine$crawler$Crawler$1
INFO: Crawler finished.
```

`$ cat samples/othermany.html.txt`

```
 Button analysis listed from the highest score to be the right one to the lowest.


 tag: a, id: , classAtt: Set(btn, btn-sucess), href: #check-and-ok, text: Make everything OK, score: 5
 Path to element: html > body > div.panel > div.panel-body > a.btn.btn-sucess:nth-child(2)

 tag: a, id: , classAtt: Set(btn), href: , text: Make everything OK, score: 4
 Path to element: html > body > div.panel > a.btn

 tag: a, id: , classAtt: Set(btn, btn-sucess), href: #ok, text: Another Make OK, score: 4
 Path to element: html > body > div.panel > div.panel-body > a.btn.btn-sucess:nth-child(3)

 tag: a, id: , classAtt: Set(btn, btn-danger), href: #ok, text: Brake everyone's OK, score: 3
 Path to element: html > body > div.panel > div.panel-body > a.btn.btn-danger
```