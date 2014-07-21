zombie-gis
==========
A skeleton for GIS implementation using [OpenMap](http://openmap.bbn.com) and the [NetBeans Platform](http://www.netbeans.org).

This project uses Fugue Icons by [Yusuke Kamiyamane](http://p.yusukekamiyamane.com/) licensed under [CC-BY-3.0](http://creativecommons.org/licenses/by/3.0/).

Try it out
----------
1. Compile:

        mvn clean install

   This will produce Windows executables (`zgis.exe` (32-bit) and `zgis64.exe` (64-bit)) and the shell script (`zgis`) in the `application/target` folder.

2. Run:

        mvn nbm:run-platform

