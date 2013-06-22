#/usr/bin sh
killall mongod
mkdir -p data/rs1 data/rs2 data/rs3 logs
mongod --replSet m101 --logpath "logs/1.log" --dbpath data/rs1 --port 27017 --smallfiles --fork
mongod --replSet m101 --logpath "logs/2.log" --dbpath data/rs2 --port 27018 --smallfiles --fork
mongod --replSet m101 --logpath "logs/3.log" --dbpath data/rs3 --port 27019 --smallfiles --fork
mongo --port 27017 << 'EOF'
config = { _id: "m101", members:[
          { _id : 0, host : "localhost:27017"},
          { _id : 1, host : "localhost:27018"},
          { _id : 2, host : "localhost:27019"} ]
};
rs.initiate(config)
EOF

#python validate.py
#killall mongod
#rm -rf data/*
#rm -rf logs/*
