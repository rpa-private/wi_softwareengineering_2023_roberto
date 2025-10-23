At the time of writing (Summer 2023), there is an IntelliJ bug that prevents reading resource files in a
project that uses the Java module system. This means that this.getClass().getResource() always returns null.

See https://youtrack.jetbrains.com/issue/IDEA-293191/Cant-read-resource-files-in-multi-module-Gradle-Java-9-project-with-module-info.java

For this reason, the CSV files in this package must to be copied somewhere outside of the project, and
then accessed as external files. In this code (for Linux or MacOS), I assume that the files have been copied
to the "/tmp" directory.

If you come up with a solution to this problem, please let me know!