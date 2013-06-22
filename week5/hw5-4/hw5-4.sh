#/usr/bin sh
mongoimport -d m101j -c zips --drop < zips.json
mongo < hw5-4.js
