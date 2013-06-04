#/usr/bin sh
mongoimport -d blog -c posts --drop < posts.json
mongo < ensureIndex.js
python validate.py
