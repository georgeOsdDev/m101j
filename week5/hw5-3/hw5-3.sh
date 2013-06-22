#/usr/bin sh
mongoimport -d m101j -c grades53 --drop < grades_5-3.js
mongo < hw5-3.js
