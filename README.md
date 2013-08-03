e4-rendering
============
e4-rendering is a proof of concept for providing new rendering engines
for the Eclipse 4 Application platform. I provide engines for
* JavaFX 2.x
* Swing
* SWT (alternative renderer to prove that the generic concepts are working with SWT)

The current source code is compatible with Eclipse 4.3 and efxclipse nightly builds

Setup
-----
* Install the latest JDK 7 (this includes the current JavaFX)
* Clone this git repo
* Import all projects from this git repo into an Eclipse IDE
* Set the target platform from the project com.toedter.e4.demo.contacts.target
* Use the predefined run configurations to launch the JavaFX, Swing and SWT demos

License
-------
All Source Code Files are licensed under the Eclipse Public License - v 1.0
http://www.eclipse.org/legal/epl-v10.html
