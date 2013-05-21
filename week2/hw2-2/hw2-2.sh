#/usr/bin sh
mongoimport -d students -c grades --drop < grades.json
mvn compile exec:java -Dexec.mainClass=com.tengen.hw2_2
mongo < hw2-2.txt
