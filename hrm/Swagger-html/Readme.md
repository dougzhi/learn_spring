#docker nginx
## 拉取镜像
docker pull nginx
## 运行容器 并将项目目录， 配置文件指向本地
docker run -p 80:80 --name my-nginx -v /Users/dong/IdeaProjects/javaLearn/hrm_parent/Swagger-html/nginx/conf/nginx.conf:/etc/nginx/nginx.conf:rw -v /Users/dong/IdeaProjects/javaLearn/hrm_parent/Swagger-html/nginx/html:/home/html:rw -d nginx