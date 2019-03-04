Assumptions:

1. If tuple size is more size of either of the files, we consider it invalid.
2. Tuples are matched only if they are in order. Ex: "I am rock" tuple won't match with "rock am I".
3. I am assuming that all the characters in the files are alpha numeric. If not, they will be considered as delimiters.
4. I am assuming that the synonyms file is in a proper format where synonyms are divided by spaces.

Note:

* You can execute this from command line using "java -jar ntuple-0.0.1-SNAPSHOT.jar synonyms.txt file1.txt file2.txt"
Just make sure the file names you give are present in src/main/resources since the application is reading files from the resources      directory. 

* I also created a rest "GET" endpoint "/plagiarism/{id}" in PlagiarismResource.java. You can also get percentage using a web service 	 call. In an ideal case, I would pass in an id as a path param and retrieve the details from db if its already saved. For the sake of simplivity, I am just hardcoding the inputs in the resource and returning output. We can always add on top of it.