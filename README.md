# Summary

## Challenge 2: Read file and create files and sort

Read each non-blank line of text from the file loomings.txt available at

https://gist.github.com/bobbae/259e195f11cea0183ea93d378946a737
Print the total number of non-blank lines.

Create a file with a unique name containing each  non-blank line of  text read from  the file.
For example you may end up with lots of files. File-1, File-2, File-3, etc.
File-1 will contain the first line read from the file. File-2 will contain the second line
read from the file, and so on.

Create a companion file for each file. Each companion file will be a hash digest of the content of the file.
For example, File-1 may have a companion file named File-1.hash which will contain hash digest of the File-1.

List the files sorted based on the size of the file, in order, from smallest to largest based on the size (total bytes) of the content within the file. Print the name and size of each file on each line of the output.

There will be one duplicate line in the loomings.txt file. Therefore, there will be two companion (hash) files that have the same hash digest content.  Print the name of the two files that have identical content.
Print  the original  text line which is duplicated in loomings.txt.

Remove the duplicate (second occurance) line from loomings.txt and create a new version of the file and call it loomings-clean.txt.  Perform diff loomings*txt and produce output.

# How to use it

1. Double click on run.bat to run the program.
2. New terminal will open up with output.
3. Press any key to exit.

# Challenge 2 Output in Terminal
1. First line prints out total non-blank line of text from the file loomings.txt
2. Second line prints out all the files in the current directory Filename and total size in bytes
3. Third line prints out both the hash digest files that have same hash digest and print out duplicate line from loomings.txt.
4. Generates loomings-clean.txt file in the current directory which contains all none-blank and non-repeating lines from loomings.txt.

## Source code 

Contains java source file, which contains main method and all the helper methods to achieve task.

## Data

Contains loomings.txt file which is used as a input for main program in source.

## Build

Contains class files of the compiled Java source code.