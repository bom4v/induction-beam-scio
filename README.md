Induction to Apache Beam SCIO
=============================

[![Build Status](https://travis-ci.com/bom4v/induction-beam-scio.svg?branch=master)](https://travis-ci.com/bom4v/induction-beam-scio)

# References
* [This README](https://github.com/bom4v/induction-beam-scio)
* [scio.g8](https://github.com/spotify/scio.g8).
* [How to install Python virtual environments with Pyenv and `pipenv`](http://github.com/machine-learning-helpers/induction-python/tree/master/installation/virtual-env)

# Overview
That repository aims to demonstrate the famous word count application,
implemented with [Apache Beam SCIO](https://github.com/spotify/scio.g8),
using a Scala application and Apache Spark as the execution engine.

# Installation
A convenient way to get the Spark ecosystem and CLI (command-line interface)
tools (_e.g._, `spark-submit`, `spark-shell`, `spark-sql`, `beeline`,
`pyspark` and `sparkR`) is through
[PySpark](https://spark.apache.org/docs/latest/api/python/pyspark.html).
PySpark is a Python wrapper around Spark libraries, run through
a Java Virtual Machine (JVM) handily provided by
[OpenJDK](https://openjdk.java.net/).

To guarantee a full reproducibility with the Python stack, `pyenv`
and `pipenv` are used here.
Also, `.python_version` and `Pipfile` are part of the project.
The user has therefore just to install `pyenv` and `pipenv`,
and then all the commands described in this document become easily
accessible and reproducible.

Follow the instructions on
[how-to install Python and Java for Spark](http://github.com/machine-learning-helpers/induction-python/tree/master/installation/virtual-env)
for more details. Basically:
* The `pyenv` utility is installed from
  [GitHub](https://github.com/pyenv/pyenv.git)
* Specific versions of Python (namely, at the time of writing, 2.7.15
  and 3.7.2) are installed in the user account file-system.
  Those specific Python frameworks provide the `pip` package management tool
* The Python `pipenv` package is installed thanks to `pip`
* The `.python_version`, `Pipfile` and `Pipfile.lock` files, specific
  per project folder, fully drive the versions of all the Python packages
  and of Python itself, so as to guarantee full reproducibility
  on all the platforms

## Clone the Git repository
```bash
$ mkdir -p ~/dev/infra && cd ~/dev/infra
$ git clone https://github.com/bom4v/induction-beam-scio.git beam-induction-scio
$ cd ~/dev/infra/beam-induction-scio
```

## Java
* Once an OpenJDK JVM has been installed, specify `JAVA_HOME` accordingly
  in `~/.bashrc`
  
* Maven also needs to be installed

### Debian/Ubuntu
```bash
$ sudo aptitude -y install openjdk-8-jdk maven
$ export JAVA_HOME="/usr/lib/jvm/java-8-openjdk-amd64"
```

### Fedora/CentOS/RedHat
```bash
$ sudo dnf -y install java-1.8.0-openjdk maven
$ export JAVA_HOME="/usr/lib/jvm/java-1.8.0-openjdk"
```

### MacOS
* Visit https://jdk.java.net/8/, download and install the MacOS package
```bash
$ export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
$ brew install maven
```

## SBT
[Download and install SBT](https://www.scala-sbt.org/download.html)

## Python-related dependencies
* `pyenv`:
```bash
$ git clone https://github.com/pyenv/pyenv.git ${HOME}/.pyenv
$ cat >> ~/.bashrc << _EOF

# Pyenv
# git clone https://github.com/pyenv/pyenv.git \${HOME}/.pyenv
export PATH=\${PATH}:\${HOME}/.pyenv/bin
if command -v pyenv 1>/dev/null 2>&1
then
  eval "\$(pyenv init -)"
fi

_EOF
$ . ~/.bashrc
$ pyenv install 2.7.15 && pyenv install 3.7.2
```

* `pip` and `pipenv`:
```bash
$ cd ~/dev/infra/spark-submit-sql
$ pyenv versions
  system
  2.7.15
* 3.7.2 (set by ~/dev/infra/beam-induction-scio/.python-version)
$ python --version
Python 3.7.2
$ pip install -U pip pipenv
$ pipenv install
Creating a virtualenv for this project...
Pipfile: ~/dev/infra/beam-induction-scio/Pipfile
Using ~/.pyenv/versions/3.7.2/bin/python (3.7.2) to create virtualenv...
⠇ Creating virtual environment...Using base prefix '~/.pyenv/versions/3.7.2'
New python executable in ~/.local/share/virtualenvs/beam-induction-scio-nFz46YtK/bin/python
Installing setuptools, pip, wheel...
done.
Running virtualenv with interpreter ~/.pyenv/versions/3.7.2/bin/python
✔ Successfully created virtual environment! 
Virtualenv location: ~/.local/share/virtualenvs/beam-induction-scio-nFz46YtK
Installing dependencies from Pipfile.lock (d2363d)...
  �   ▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉▉ 2/2 — 00:00:20
To activate this project's virtualenv, run pipenv shell.
Alternatively, run a command inside the virtualenv with pipenv run.
```

# Contribute to that project

## Simple test
* Compile and package the word count application:
```bash
$ sbt 'set isSnapshot := true' compile package publishM2 publishLocal
[info] Loading settings for project global-plugins from gpg.sbt ...
[info] Loading global plugins from ~/.sbt/1.0/plugins
[info] Loading settings for project beam-induction-scio-build from plugins.sbt ...
[info] Loading project definition from ~/dev/infra/beam-induction-scio/project
[info] Loading settings for project root from build.sbt ...
[info] Set current project to beam-induction (in build file:~/dev/infra/beam-induction-scio/)
[info] Executing in batch mode. For better performance use sbt's shell
[info] Defining isSnapshot
[info] The new value will be used by makeIvyXmlConfiguration, makeIvyXmlLocalConfiguration and 5 others.
[info] 	Run `last` for details.
[info] Reapplying settings...
[info] Set current project to beam-induction (in build file:~/dev/infra/beam-induction-scio/)
[info] Compiling 1 Scala source to ~/dev/infra/beam-induction-scio/target/scala-2.12/classes ...
[info] Done compiling.
[success] Total time: 7 s, completed May 8, 2019 6:40:03 PM
[info] Packaging ~/dev/infra/beam-induction-scio/target/scala-2.12/beam-induction_2.12-0.0.1.jar ...
[info] Done packaging.
[success] Total time: 1 s, completed May 8, 2019 6:40:04 PM
[info] Packaging ~/dev/infra/beam-induction-scio/target/scala-2.12/beam-induction_2.12-0.0.1-sources.jar ...
[info] Done packaging.
[info] Wrote ~/dev/infra/beam-induction-scio/target/scala-2.12/beam-induction_2.12-0.0.1.pom
[info] Main Scala API documentation to ~/dev/infra/beam-induction-scio/target/scala-2.12/api...
model contains 5 documentable templates
[info] Main Scala API documentation successful.
[info] Packaging ~/dev/infra/beam-induction-scio/target/scala-2.12/beam-induction_2.12-0.0.1-javadoc.jar ...
[info] Done packaging.
[info] 	published beam-induction_2.12 to file:~/.m2/repository/org/bom4v/ti/beam-induction_2.12/0.0.1/beam-induction_2.12-0.0.1.pom
[info] 	published beam-induction_2.12 to file:~/.m2/repository/org/bom4v/ti/beam-induction_2.12/0.0.1/beam-induction_2.12-0.0.1.jar
[info] 	published beam-induction_2.12 to file:~/.m2/repository/org/bom4v/ti/beam-induction_2.12/0.0.1/beam-induction_2.12-0.0.1-sources.jar
[info] 	published beam-induction_2.12 to file:~/.m2/repository/org/bom4v/ti/beam-induction_2.12/0.0.1/beam-induction_2.12-0.0.1-javadoc.jar
[success] Total time: 2 s, completed May 8, 2019 6:40:06 PM
[info] Wrote ~/dev/infra/beam-induction-scio/target/scala-2.12/beam-induction_2.12-0.0.1.pom
[info] Main Scala API documentation to ~/dev/infra/beam-induction-scio/target/scala-2.12/api...
model contains 5 documentable templates
[info] Main Scala API documentation successful.
[info] Packaging ~/dev/infra/beam-induction-scio/target/scala-2.12/beam-induction_2.12-0.0.1-javadoc.jar ...
[info] Done packaging.
[info] :: delivering :: org.bom4v.ti#beam-induction_2.12;0.0.1 :: 0.0.1 :: integration :: Wed May 08 18:40:08 CEST 2019
[info] 	delivering ivy file to ~/dev/infra/beam-induction-scio/target/scala-2.12/ivy-0.0.1.xml
[info] 	published beam-induction_2.12 to ~/.ivy2/local/org.bom4v.ti/beam-induction_2.12/0.0.1/poms/beam-induction_2.12.pom
[info] 	published beam-induction_2.12 to ~/.ivy2/local/org.bom4v.ti/beam-induction_2.12/0.0.1/jars/beam-induction_2.12.jar
[info] 	published beam-induction_2.12 to ~/.ivy2/local/org.bom4v.ti/beam-induction_2.12/0.0.1/srcs/beam-induction_2.12-sources.jar
[info] 	published beam-induction_2.12 to ~/.ivy2/local/org.bom4v.ti/beam-induction_2.12/0.0.1/docs/beam-induction_2.12-javadoc.jar
[info] 	published ivy to ~/.ivy2/local/org.bom4v.ti/beam-induction_2.12/0.0.1/ivys/ivy.xml
[success] Total time: 2 s, completed May 8, 2019 6:40:08 PM
```

* The above command generates JAR artefacts (mainly
  `beam-induction_2.12-0.0.1.jar`) locally in the project
  `target` directory, as well as in the Maven and Ivy2 user repositories
  (`~/.m2` and `~/.ivy2` respectively).

* The `set isSnapshot := true` option allows to silently override
  any previous versions of the JAR artefacts in the Maven and Ivy2 repositories
  
* Check that the artefacts have been produced
  + Locally (`package` command):
```bash
$ ls -laFh target/scala-2.12/beam-induction_2.12-0.0.1.jar
-rw-r--r--  1 user  group   4.7K May  8 18:40 target/scala-2.12/beam-induction_2.12-0.0.1.jar
```

  + In the local Maven repository (`publishM2` task):
```bash
$ ls -laFh ~/.m2/repository/org/bom4v/ti/beam-induction_2.12/0.0.1/beam-induction_2.12-0.0.1.jar
-rw-r--r--  1 user  group   4.7K May  8 18:40 ~/.m2/repository/org/bom4v/ti/beam-induction_2.12/0.0.1/beam-induction_2.12-0.0.1.jar
```

  + In the local Ivy2 repository (`publishLocal` task):
```bash
$ ls -laFh ~/.ivy2/local/org.bom4v.ti/beam-induction_2.12/0.0.1/jars/beam-induction_2.12.jar
-rw-r--r--  1 user  group   4.7K May  8 18:40 ~/.ivy2/local/org.bom4v.ti/beam-induction_2.12/0.0.1/jars/beam-induction_2.12.jar
```

* Clean any previous data:
```bash
$ rm -rf wc
```

* Launch the job in the SBT JVM:
```bash
$ sbt "run --output=wc"
[info] Loading settings for project global-plugins from gpg.sbt ...
[info] Loading global plugins from ~/.sbt/1.0/plugins
[info] Loading settings for project beam-induction-scio-build from plugins.sbt ...
[info] Loading project definition from ~/dev/infra/beam-induction-scio/project
[info] Loading settings for project root from build.sbt ...
[info] Set current project to beam-induction (in build file:~/dev/infra/beam-induction-scio/)
[info] Running org.bom4v.ti.WordCount --output=wc
[run-main-0] INFO org.apache.beam.sdk.io.FileBasedSource - Filepattern gs://dataflow-samples/shakespeare/kinglear.txt matched 1 files with total size 157283
[run-main-0] INFO org.apache.beam.sdk.io.FileBasedSource - Splitting filepattern gs://dataflow-samples/shakespeare/kinglear.txt into bundles of size 13106 took 163 ms and produced 1 files and 12 bundles
[direct-runner-worker] INFO org.apache.beam.sdk.io.WriteFiles - Opening writer 030e9a82-6a94-41c1-b25c-efafa2522c78 for window org.apache.beam.sdk.transforms.windowing.GlobalWindow@1a577354 pane PaneInfo{isFirst=true, isLast=true, timing=ON_TIME, index=0, onTimeIndex=0} destination null
[direct-runner-worker] INFO org.apache.beam.sdk.io.WriteFiles - Opening writer ed967016-924a-4f07-9b13-b8c5f7cfb89e for window org.apache.beam.sdk.transforms.windowing.GlobalWindow@1a577354 pane PaneInfo{isFirst=true, isLast=true, timing=ON_TIME, index=0, onTimeIndex=0} destination null
[direct-runner-worker] INFO org.apache.beam.sdk.io.WriteFiles - Opening writer 353331fe-3a51-40da-83a0-4bb1176cc547 for window org.apache.beam.sdk.transforms.windowing.GlobalWindow@1a577354 pane PaneInfo{isFirst=true, isLast=true, timing=ON_TIME, index=0, onTimeIndex=0} destination null
[direct-runner-worker] INFO org.apache.beam.sdk.io.WriteFiles - Opening writer 84f30817-0f8a-4ded-82c3-adbc422696e1 for window org.apache.beam.sdk.transforms.windowing.GlobalWindow@1a577354 pane PaneInfo{isFirst=true, isLast=true, timing=ON_TIME, index=0, onTimeIndex=0} destination null
[direct-runner-worker] INFO org.apache.beam.sdk.io.WriteFiles - Opening writer 28811dc7-e814-44e1-82ca-b3ef59862254 for window org.apache.beam.sdk.transforms.windowing.GlobalWindow@1a577354 pane PaneInfo{isFirst=true, isLast=true, timing=ON_TIME, index=0, onTimeIndex=0} destination null
[direct-runner-worker] INFO org.apache.beam.sdk.io.FileBasedSink$Writer - Successfully wrote temporary file ~/dev/infra/beam-induction-scio/wc/.temp-beam-2019-05-08_16-49-08-1/353331fe-3a51-40da-83a0-4bb1176cc547
[direct-runner-worker] INFO org.apache.beam.sdk.io.FileBasedSink$Writer - Successfully wrote temporary file ~/dev/infra/beam-induction-scio/wc/.temp-beam-2019-05-08_16-49-08-1/030e9a82-6a94-41c1-b25c-efafa2522c78
[direct-runner-worker] INFO org.apache.beam.sdk.io.FileBasedSink$Writer - Successfully wrote temporary file ~/dev/infra/beam-induction-scio/wc/.temp-beam-2019-05-08_16-49-08-1/ed967016-924a-4f07-9b13-b8c5f7cfb89e
[direct-runner-worker] INFO org.apache.beam.sdk.io.FileBasedSink$Writer - Successfully wrote temporary file ~/dev/infra/beam-induction-scio/wc/.temp-beam-2019-05-08_16-49-08-1/84f30817-0f8a-4ded-82c3-adbc422696e1
[direct-runner-worker] INFO org.apache.beam.sdk.io.FileBasedSink$Writer - Successfully wrote temporary file ~/dev/infra/beam-induction-scio/wc/.temp-beam-2019-05-08_16-49-08-1/28811dc7-e814-44e1-82ca-b3ef59862254
[direct-runner-worker] INFO org.apache.beam.sdk.io.WriteFiles - Finalizing 5 file results
[direct-runner-worker] INFO org.apache.beam.sdk.io.FileBasedSink - Finalizing for destination null num shards 5.
[direct-runner-worker] INFO org.apache.beam.sdk.io.FileBasedSink - Will copy temporary file FileResult{tempFilename=~/dev/infra/beam-induction-scio/wc/.temp-beam-2019-05-08_16-49-08-1/353331fe-3a51-40da-83a0-4bb1176cc547, shard=0, window=org.apache.beam.sdk.transforms.windowing.GlobalWindow@1a577354, paneInfo=PaneInfo{isFirst=true, isLast=true, timing=ON_TIME, index=0, onTimeIndex=0}} to final location ~/dev/infra/beam-induction-scio/wc/part-00000-of-00005.txt
[direct-runner-worker] INFO org.apache.beam.sdk.io.FileBasedSink - Will copy temporary file FileResult{tempFilename=~/dev/infra/beam-induction-scio/wc/.temp-beam-2019-05-08_16-49-08-1/030e9a82-6a94-41c1-b25c-efafa2522c78, shard=4, window=org.apache.beam.sdk.transforms.windowing.GlobalWindow@1a577354, paneInfo=PaneInfo{isFirst=true, isLast=true, timing=ON_TIME, index=0, onTimeIndex=0}} to final location ~/dev/infra/beam-induction-scio/wc/part-00004-of-00005.txt
[direct-runner-worker] INFO org.apache.beam.sdk.io.FileBasedSink - Will copy temporary file FileResult{tempFilename=~/dev/infra/beam-induction-scio/wc/.temp-beam-2019-05-08_16-49-08-1/84f30817-0f8a-4ded-82c3-adbc422696e1, shard=3, window=org.apache.beam.sdk.transforms.windowing.GlobalWindow@1a577354, paneInfo=PaneInfo{isFirst=true, isLast=true, timing=ON_TIME, index=0, onTimeIndex=0}} to final location ~/dev/infra/beam-induction-scio/wc/part-00003-of-00005.txt
[direct-runner-worker] INFO org.apache.beam.sdk.io.FileBasedSink - Will copy temporary file FileResult{tempFilename=~/dev/infra/beam-induction-scio/wc/.temp-beam-2019-05-08_16-49-08-1/ed967016-924a-4f07-9b13-b8c5f7cfb89e, shard=1, window=org.apache.beam.sdk.transforms.windowing.GlobalWindow@1a577354, paneInfo=PaneInfo{isFirst=true, isLast=true, timing=ON_TIME, index=0, onTimeIndex=0}} to final location ~/dev/infra/beam-induction-scio/wc/part-00001-of-00005.txt
[direct-runner-worker] INFO org.apache.beam.sdk.io.FileBasedSink - Will copy temporary file FileResult{tempFilename=~/dev/infra/beam-induction-scio/wc/.temp-beam-2019-05-08_16-49-08-1/28811dc7-e814-44e1-82ca-b3ef59862254, shard=2, window=org.apache.beam.sdk.transforms.windowing.GlobalWindow@1a577354, paneInfo=PaneInfo{isFirst=true, isLast=true, timing=ON_TIME, index=0, onTimeIndex=0}} to final location ~/dev/infra/beam-induction-scio/wc/part-00002-of-00005.txt
[direct-runner-worker] INFO org.apache.beam.sdk.io.FileBasedSink - Will remove known temporary file ~/dev/infra/beam-induction-scio/wc/.temp-beam-2019-05-08_16-49-08-1/353331fe-3a51-40da-83a0-4bb1176cc547
[direct-runner-worker] INFO org.apache.beam.sdk.io.FileBasedSink - Will remove known temporary file ~/dev/infra/beam-induction-scio/wc/.temp-beam-2019-05-08_16-49-08-1/84f30817-0f8a-4ded-82c3-adbc422696e1
[direct-runner-worker] INFO org.apache.beam.sdk.io.FileBasedSink - Will remove known temporary file ~/dev/infra/beam-induction-scio/wc/.temp-beam-2019-05-08_16-49-08-1/28811dc7-e814-44e1-82ca-b3ef59862254
[direct-runner-worker] INFO org.apache.beam.sdk.io.FileBasedSink - Will remove known temporary file ~/dev/infra/beam-induction-scio/wc/.temp-beam-2019-05-08_16-49-08-1/ed967016-924a-4f07-9b13-b8c5f7cfb89e
[direct-runner-worker] INFO org.apache.beam.sdk.io.FileBasedSink - Will remove known temporary file ~/dev/infra/beam-induction-scio/wc/.temp-beam-2019-05-08_16-49-08-1/030e9a82-6a94-41c1-b25c-efafa2522c78
[direct-runner-worker] WARN org.apache.beam.sdk.io.FileBasedSink - Failed to match temporary files under: [~/dev/infra/beam-induction-scio/wc/.temp-beam-2019-05-08_16-49-08-1/].
[success] Total time: 15 s, completed May 8, 2019 6:49:17 PM
```

* It generates key-value pairs (representing the counts for every word
  in the [input text](https://github.com/cs109/2015/blob/master/Lectures/Lecture15b/sparklect/shakes/kinglear.txt))
  as text files in the `wc` directory:
```bash
$ wc -l wc/part-0000*.txt
     955 wc/part-00000-of-00005.txt
     959 wc/part-00001-of-00005.txt
     956 wc/part-00002-of-00005.txt
     957 wc/part-00003-of-00005.txt
     957 wc/part-00004-of-00005.txt
    4784 total
```

* Run the tests with `sbt test`:
```bash
$ sbt test
[info] Loading settings for project global-plugins from gpg.sbt ...
[info] Loading global plugins from ~/.sbt/1.0/plugins
[info] Loading settings for project beam-induction-scio-build from plugins.sbt ...
[info] Loading project definition from ~/dev/infra/beam-induction-scio/project
[info] Loading settings for project root from build.sbt ...
[info] Set current project to beam-induction (in build file:~/dev/infra/beam-induction-scio/)
[info] WordCountTest:
[info] WordCount
[info] - should work
[info] ScalaTest
[info] Run completed in 5 seconds, 201 milliseconds.
[info] Total number of tests run: 1
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 1, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[info] Passed: Total 1, Failed 0, Errors 0, Passed 1
[success] Total time: 8 s, completed May 8, 2019 7:01:32 PM
```
* Build and install a launcher script:
```bash
$ sbt pack
$ pushd target/pack && make install && popd
~/dev/infra/beam-induction-scio/target/pack ~/dev/infra/beam-induction-scio
Clean up ~/local/beam-induction/beam-induction-0.0.1/lib folder
if [ -d "~/local/beam-induction/beam-induction-0.0.1/lib" ]; then rm -rf "~/local/beam-induction/beam-induction-0.0.1/lib"; fi
install -d "~/local/beam-induction/beam-induction-0.0.1"
cp -pr ./* "~/local/beam-induction/beam-induction-0.0.1"
ln -sfn "beam-induction-0.0.1" "~/local/beam-induction/current"
install -d "~/local/bin"
ln -sf "../beam-induction/current/bin/word-count" "~/local/bin/word-count"
~/dev/infra/beam-induction-scio
```

* Launch the job with the wrapper script:
```bash
$ ~/local/bin/word-count --output=wc
$ wc -l wc/part-0000*.txt
    1597 wc/part-00000-of-00003.txt
    1594 wc/part-00001-of-00003.txt
    1593 wc/part-00002-of-00003.txt
    4784 total
```

* Publish the signed packages (into
  [Maven Central repsoitory](https://repo.maven.apache.org/maven2/org/bom4v/ti/)):
```bash
$ sbt publishSigned
[info] Loading settings for project global-plugins from gpg.sbt ...
[info] Loading global plugins from ~/.sbt/1.0/plugins
[info] Loading settings for project beam-induction-scio-build from plugins.sbt ...
[info] Loading project definition from ~/dev/infra/beam-induction-scio/project
[info] Loading settings for project root from build.sbt ...
[info] Set current project to beam-induction (in build file:~/dev/infra/beam-induction-scio/)
[info] Wrote ~/dev/infra/beam-induction-scio/target/scala-2.12/beam-induction_2.12-0.0.1.pom
Please enter PGP passphrase (or ENTER to abort): *********
[info] 	published beam-induction_2.12 to https://oss.sonatype.org/service/local/staging/deploy/maven2/org/bom4v/ti/beam-induction_2.12/0.0.1/beam-induction_2.12-0.0.1.pom.asc
[info] 	published beam-induction_2.12 to https://oss.sonatype.org/service/local/staging/deploy/maven2/org/bom4v/ti/beam-induction_2.12/0.0.1/beam-induction_2.12-0.0.1.jar
[info] 	published beam-induction_2.12 to https://oss.sonatype.org/service/local/staging/deploy/maven2/org/bom4v/ti/beam-induction_2.12/0.0.1/beam-induction_2.12-0.0.1-sources.jar.asc
[info] 	published beam-induction_2.12 to https://oss.sonatype.org/service/local/staging/deploy/maven2/org/bom4v/ti/beam-induction_2.12/0.0.1/beam-induction_2.12-0.0.1.jar.asc
[info] 	published beam-induction_2.12 to https://oss.sonatype.org/service/local/staging/deploy/maven2/org/bom4v/ti/beam-induction_2.12/0.0.1/beam-induction_2.12-0.0.1-javadoc.jar.asc
[info] 	published beam-induction_2.12 to https://oss.sonatype.org/service/local/staging/deploy/maven2/org/bom4v/ti/beam-induction_2.12/0.0.1/beam-induction_2.12-0.0.1-sources.jar
[info] 	published beam-induction_2.12 to https://oss.sonatype.org/service/local/staging/deploy/maven2/org/bom4v/ti/beam-induction_2.12/0.0.1/beam-induction_2.12-0.0.1-javadoc.jar
[info] 	published beam-induction_2.12 to https://oss.sonatype.org/service/local/staging/deploy/maven2/org/bom4v/ti/beam-induction_2.12/0.0.1/beam-induction_2.12-0.0.1.pom
[success] Total time: 39 s, completed May 8, 2019 6:51:56 PM
```

* Scala style
Find style configuration in `scalastyle-config.xml`. To enforce style run:
```bash
$ sbt scalastyle
[info] Loading settings for project global-plugins from gpg.sbt ...
[info] Loading global plugins from ~/.sbt/1.0/plugins
[info] Loading settings for project beam-induction-scio-build from plugins.sbt ...
[info] Loading project definition from ~/dev/infra/beam-induction-scio/project
[info] Loading settings for project root from build.sbt ...
[info] Set current project to beam-induction (in build file:~/dev/infra/beam-induction-scio/)
[info] scalastyle using config ~/dev/infra/beam-induction-scio/scalastyle-config.xml
[info] scalastyle Processed 1 file(s)
[info] scalastyle Found 0 errors
[info] scalastyle Found 0 warnings
[info] scalastyle Found 0 infos
[info] scalastyle Finished in 4 ms
[success] created output: ~/dev/infra/beam-induction-scio/target
[success] Total time: 1 s, completed May 8, 2019 7:03:56 PM
```

* REPL
To experiment with current codebase in [Scio REPL](https://github.com/spotify/scio/wiki/Scio-REPL)
simply:
```bash
$ sbt repl/run
```

