# To create Directories
mkdir target
# To create Class files from Java files
javac -cp .:./lib/JColor-5.5.1.jar:./lib/jcommander-1.82.jar -d ./target/ src/java/edu/school21/printer/*/*.java
# To unpack libs jar files
cd target ; jar xf ../lib/JColor-5.5.1.jar com ; jar xf ../lib/jcommander-1.82.jar com ; cd ..
# To create Jar file from our Class files
jar -cvfm ./target/images-to-chars-printer.jar src/manifest.txt -C target .
# To run our programm
java -jar target/images-to-chars-printer.jar --white=BRIGHT_BLACK --black=WHITE
