# Request-Analyzer

This is a proof of concept that I am creating for work.

I would like to:

1. Upload a file to a browser that has a column of URIs
2. Read the file
3. Write the responses from submitting each URI as a request to a separate column of either the original uploaded file or a new file in order to ensure that the URIs are valid.

Problems:
I am currently trying to iterate through the first column of a csv and submit requests to URIs to see if they are valid.
    * I will probably use the [clj-http](https://github.com/mmcgrana/clj-http) library. It seems to support what I need.

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server
