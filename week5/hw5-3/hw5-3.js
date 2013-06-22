use m101j

db.grades53.aggregate([
	{$unwind:"$scores"},
	{
		$match:{
			'scores.type':{$ne:'quiz'}
		}
	},
	{
		$group:{
			"_id":{"student_id":"$student_id","class_id":"$class_id"},
			"student_avarage":{$avg:"$scores.score"}
		}
	},
	{
		$group:{
			"_id":"$_id.class_id",
			"class_avarage":{$avg:"$student_avarage"}
		}
	},
	{$sort:{"class_avarage":1}}
])
