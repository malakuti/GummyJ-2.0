The GummyJ language is an extension to Java to support modular event-driven programming based on the concept of gummy modules (http://somayehmalakuti.com/event-based-modularization/).
GummyJ 2.0 supports composite gummy modules, as well as primitive emergent gummy modules. More details about these concepts can be found at: 
http://somayehmalakuti.com/wp-content/uploads/2013/12/Techreport_Malakuti.pdf
http://dl.acm.org/citation.cfm?id=2658764
A brief description of the uploaded files:
“gmprocess” is an executable file that compile GummyJ programs. This has been tested under Linux and Windows (gmprocess.exe).
“lib-core.jar” is the jar file of GummyJ runtime, which is required to execute GummyJ programs.
“file.gm” is an example code in GummyJ, which must be compiled by gmprocess. This results in a set of Java classes, which can be used to execute the example. "lib-generated.jar" contains generated files for our example.
“Tester.java” is an example tester program in Java, which makes use of these generated files, to produce events and send them to gummy modules.
“lib-test.jar” and “app-tester.jar” are helper jar files, which are required to execute the example.
