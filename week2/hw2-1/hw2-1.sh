#/usr/bin sh
mongoimport -d students -c grades --drop < grades.json
mongo < hw2-1.txt
