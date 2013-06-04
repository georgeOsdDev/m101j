use m101
db.profile.find({"ns":"school2.students"},{"millis":true}).sort({"millis":-1}).limit(5)
