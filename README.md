# lein-riot

Leiningen plugin for running the Riot compiler in Clojure projects

## Installation

To enable lein-riot for your project, put the following in the
`:plugins` vector of your `project.clj` file:

![Latest version](https://clojars.org/lein-riot/latest-version.svg)

## Configuring Riot

``` clojure
  :riot { :tags [["resources/public/" "resources/public/seisei-tags.js"]]}
```

You can also specify other parameters for riot:
``` clojure
  :riot {
    :compact true
    :whitespace true
    :expr true
    :brackets <value>
    :ext <value>
    :type <value>
  }
```
Documentation of these features can be found in the [Riot Compiler Documentation](https://muut.com/riotjs/compiler.html).

## Invoking Riot

```sh
$ lein riot        # Runs the Riot Compiler
$ lein riot watch  # Runs Riot in watch mode on the FIRST tag set you've specified
$ lein riot help   # Shows Help
```

## Note

Riot uses lein-npm to automatically install Riot via the npm package manager if it's not installed already.


## License

Copyright 2015 Trever M. Shick

Licensed under the Apache License, Version 2.0 (the "License"); you
may not use this file except in compliance with the License. You may
obtain a copy of the License at
[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0).

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
implied. See the License for the specific language governing
permissions and limitations under the License.
