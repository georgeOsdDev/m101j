#/usr/bin sh
mongoimport -d m101j -c city --drop < zips.json
mongo < hw5-2.js
