name := "DVMS"

version := "0.1"

organization := "org.discovery"

scalaVersion := "2.10.0"

crossPaths := false

retrieveManaged := true

//libraryDependencies += "choco" % "choco" % "2.1.4" % "test"

//libraryDependencies += "entropy" % "entropy-api" % "2.1.14" % "test"

//libraryDependencies += "entropy" % "entropy-core" % "2.1.14" % "test"

//libraryDependencies += "entropy" % "entropy" % "2.1.14" % "test"


// Excluding the following directories for compilation: scheduling/{dvms,dvms2,hubis}
excludeFilter in unmanagedSources := new sbt.FileFilter{
  def accept(f: File): Boolean = "(?s).*scheduling/dvms/.*|.*scheduling/hubis/.*".r.pattern.matcher(f.getAbsolutePath).matches
}
