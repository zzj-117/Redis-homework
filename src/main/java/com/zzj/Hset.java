package com.zzj;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class Hset {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        Post post=new Post();
        post.setAuthor("bbb");
        post.setContent("网络1701");
        post.setTitle("计算机网络");
        Long postId=savePost(post,jedis);
        System.out.println("保存成功");
        Post myPost=getPost(postId,jedis);
        System.out.println(myPost);
        updatePost(1,"title","cdcdmk",jedis);
        selPost(1,2,jedis);
        del(jedis,1);

    }
    static Long savePost(Post post,Jedis jedis) {
        Long postId=jedis.incr("posts");
        Map<String,String> blog=new HashMap<String, String>();
        blog.put("title",post.getTitle());
        blog.put("author",post.getAuthor());
        blog.put("content",post.getContent());
        jedis.hmset("post"+postId+"data:",blog);
        return postId;
    }
    static Post getPost(Long postId,Jedis jedis) {
        Map<String,String> myblog=jedis.hgetAll("post:"+postId+":data");
        Post post=new Post();
        post.setTitle(myblog.get("title"));
        post.setContent(myblog.get("content"));
        post.setAuthor(myblog.get("author"));
        return post;

    }

    public static void del(Jedis jedis,Integer id){

        jedis.hdel("article"+id+"Data");
    }
    public static void updatePost(int postID,String key,String val,Jedis jedis){
        Map<String, String> properties = jedis.hgetAll("Article:" + postID);
        properties.put(key,val);
        jedis.hmset("Article:" + postID,properties);
    }

    public static void selPost(int page,int size,Jedis jedis){
        /**分页.**/
        jedis.lrange("list:article",(page-1)*size,page*size-1);
        System.out.println(jedis.lrange("list:article",(page-1)*size,page*size-1));
    }
}
