package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: oshidatakeharu
 * Date: 5/22/13
 * Time: 1:09 AM
 */
public class hw2_2 {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("students");
        DBCollection collection = db.getCollection("grades");

        removeLowest(collection);

        //> db.grades.count()
        //600
        long count = collection.count();
        System.out.println(count);

        //>db.grades.find().sort({'score':-1}).skip(100).limit(1)
        //{ "_id" : ObjectId("513257f68d6e7cb63d7b1ead"), "student_id" : 164, "type" : "exam", "score" : 87.06518186605459 }
        DBCursor cursor = collection.find().sort(new BasicDBObject("score",-1)).skip(100).limit(1);
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            cursor.close();
        }


        //> db.grades.find({},{'student_id':1, 'type':1, 'score':1, '_id':0}).sort({'student_id':1, 'score':1, }).limit(5)
        //{ "student_id" : 0, "type" : "quiz", "score" : 16.28337833467709 }
        //{ "student_id" : 0, "type" : "exam", "score" : 64.40706888325151 }
        //{ "student_id" : 0, "type" : "homework", "score" : 80.31845193864314 }
        //{ "student_id" : 1, "type" : "quiz", "score" : 11.45004974085635 }
        //{ "student_id" : 1, "type" : "homework", "score" : 31.56114538077717 }
        DBCursor cursor2 = collection.find(new BasicDBObject(),
                                            new BasicDBObject("student_id", 1)
                                                    .append("type", 1)
                                                    .append("score", 1)
                                                    .append("_id", 0)
                                          ).sort(new BasicDBObject("student_id",1).append("score", 1)).limit(5);

        try {
            while (cursor2.hasNext()) {
                DBObject cur = cursor2.next();
                System.out.println(cur);
            }
        } finally {
            cursor2.close();
        }

    }

    private static void removeLowest(DBCollection collection) {
        DBCursor cursor = collection.find(new BasicDBObject("type","homework"))
                .sort(new BasicDBObject("student_id",1).append("score", 1));

        int student_id = -1;
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                if (student_id != (Integer) cur.get("student_id")){
                    collection.remove(cur);
                }
                student_id = (Integer) cur.get("student_id");
            }
        } finally {
            cursor.close();
        }
    }

}
