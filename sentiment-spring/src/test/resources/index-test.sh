# /bin/bash

for i in $(seq 1 10000)
     do echo '"sentiment'$i'"';
     # 192.168.32.17:20000,  36.7.150.150:28094
     curl -X POST -H 'Content-Type:application/json' http://192.168.32.17:20000/sentiment/index --data '{"num":1,"records":[{"id":"sentiment'$i'","platform":9,"mid":"123456789987654321","username":"zxsoft","nickname":"中新舆情","original_id":"original_sentiment","original_uid":"original_zxsoft","original_name":"original_中新软件","original_title":"original_标题","original_url":"http://www.orignal_url.com","url":"http://www.url.com","home_url":"http://www.home_url.com","title":"标题","type":"所属类型","isharmful":true,"content":"测试内容","comment_count":10,"read_count":20,"favorite_count":30,"attitude_count":40,"repost_count":50,"video_url":"http://www.video_url.com","pic_url":"htpp://www.pic_url.com","voice_url":"http://www.voice_url.com","timestamp":1419755627695,"source_id":70,"lasttime":1419842027695,"server_id":90,"identify_id":100,"identify_md5":"abcdefg123456789","keyword":"关键词","first_time":1419928427695,"update_time":1420014827695,"ip":"192.168.32.45","location":"安徽省合肥市","geo":"经纬度信息","receive_addr":"receive@gmail.com","append_addr":"append@gmail.com","send_addr":"send@gmail.com","source_name":"新浪微博","source_type":121,"country_code":122,"location_code":123,"province_code":124,"city_code":125}]}';
done;
