package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: oshidatakeharu
 * Date: 5/30/13
 * Time: 1:09 AM
 */
public class hw3_1 {
	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB db = client.getDB("school");
		DBCollection collection = db.getCollection("students");

		removeLowest(collection);

//> db.students.count()
//200
		long count = collection.count();
		System.out.println(count);

		//db.students.find({_id:100}).pretty()
		//	{
		//		"_id" : 100,
		//			"name" : "Demarcus Audette",
		//			"scores" : [
		//		{
		//			"type" : "exam",
		//				"score" : 30.61740640636871
		//		},
		//		{
		//			"type" : "quiz",
		//				"score" : 14.232338213537322
		//		},
		//		{
		//			"type" : "homework",
		//				"score" : 31.414212985763324
		//		}
		//		]
		//	}
		DBObject result = collection.findOne(new BasicDBObject("_id", 100));
		System.out.println(result);

	}

	private static void removeLowest(DBCollection collection) {
		DBCursor cursor = collection.find(new BasicDBObject()).sort(new BasicDBObject("student_id", 1));

		try {
			while (cursor.hasNext()) {
				DBObject cur = cursor.next();
				int removeIdx = -1;
				List<Map> scores = (List<Map>) cur.get("scores");
				for (int i = 0;i < scores.size();i++){
					Double lower = 100.00;
					Map score = scores.get(i);
					if("homework".equals((String)score.get("type")) &&
							(Double)score.get("score") < lower ){
						lower = (Double)score.get("score");
						removeIdx = i;
					}
				}
				scores.remove(removeIdx);
				collection.update(new BasicDBObject("_id", cur.get("_id")),
						new BasicDBObject("$set", new BasicDBObject("scores", scores)),true,false);
			}
		} finally {
			cursor.close();
		}
	}

}
