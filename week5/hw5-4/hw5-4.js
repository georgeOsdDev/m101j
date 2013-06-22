use m101

db.zips.aggregate([
  {$project:
    {
      "city":1,
      "state":1,
      "_id":1,
      "pop":1,
      first_char: {$substr : ["$city",0,1]},
    }
  },
  {$match:
    {
      "first_char":{$gte:"0"},
      "first_char":{$lte:"9"}
    }
  },
  {$group:
    {
      "_id":0,
      "pop":{$sum:"$pop"}
    }
  }
])

