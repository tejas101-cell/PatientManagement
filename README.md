> **Kafka docker configuration**

docker run -d ^
   
   --name kafka ^
   
   --network internal ^
   
   -p 9092:9092 ^
   
   -p 9094:9094 ^
   
   -e KAFKA_NODE_ID=0 ^
   
   -e KAFKA_PROCESS_ROLES=broker,controller ^
   
   -e KAFKA_CONTROLLER_QUORUM_VOTERS=0@kafka:9093 ^
   
   -e KAFKA_CONTROLLER_LISTENER_NAMES=CONTROLLER ^
   
   -e KAFKA_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093,EXTERNAL://:9094 ^
   
   -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092,EXTERNAL://localhost:9094 ^
   
   -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT,EXTERNAL:PLAINTEXT ^
   
   -e KAFKA_INTER_BROKER_LISTENER_NAME=PLAINTEXT ^
   
   -e CLUSTER_ID=WYr3YkBFSmS-mslJP4vuwQ ^
   
   confluentinc/cp-kafka:7.6.1
