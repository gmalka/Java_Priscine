For create class files:
javac -d ./target/ src/java/edu/school21/printer/*/*.java

For create jar file:
jar cvfm ./target/images-to-chars-printer.jar src/manifest.txt -C target edu/ -C src/ resources

For run jar file:
java -jar target/images-to-chars-printer.jar . 0
