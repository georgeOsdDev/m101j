#/usr/bin sh
mongoimport -d m101 -c profile --drop < sysprofile.json
mongo < hw4-4.js
