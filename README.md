# aether-demo
Aether project repository (aether-demo)

This is a fork from https://github.com/eclipse/aether-demo, which is a demo library that uses the Aether API (http://www.eclipse.org/aether/developers/) to create a dependency graph (and later, a tree) from a single artifact, using data in Maven Central.

Added a Spark lilghtweight HTTP server to allow creating a demo backend service, which a client (like a maven plugin) can use to comminucate with.
