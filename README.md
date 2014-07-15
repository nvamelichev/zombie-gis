zombie-gis
==========
A skeleton for GIS implementation using [OpenMap](http://openmap.bbn.com) and the [NetBeans Platform](http://www.netbeans.org).

Try it out
----------
1. Compile:

        mvn clean install
        
   This will produce Windows executables (`zgis.exe` (32-bit) and `zgis64.exe` (64-bit)) and the shell script (`zgis`) in the `application/target` folder.

2. Run:

        mvn nbm:run-platform

