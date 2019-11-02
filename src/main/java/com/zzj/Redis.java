package com.zzj;


import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

public class Redis {

        public static void main(String[] args) {
     Jedis jedis=new Jedis("localhost");
            Post post=new Post();
            post.setAuthor("aaa");
            post.setContent("网络1701");
            post.setTitle("计算机网络");
            Long postId=savePost(jedis,post);
            System.out.println("保存成功");
            Post myPost=getPost(jedis,postId);
            System.out.println(myPost);
            getPost(jedis,postId);
            deletePost(1,jedis);
            update(jedis,1);
}
    public static Long savePost(Jedis jedis,Post post) {
            Long postId=jedis.incr("posts");
            String poststr= JSON.toJSONString(post);
            jedis.set("post:"+postId+"data:",poststr);
            return postId;
    }
    public static Post getPost(Jedis jedis,Long postId) {
        String post=jedis.get("post:"+postId+"data:");
        Post post1=JSON.parseObject(post,Post.class);
              return post1;
    }

    public static void deletePost(Integer postId,Jedis jedis){
        jedis.del("post:" + postId + ":data");
        System.out.println("删除成功");
    }

    public static void update(Jedis jedis,Integer postId){
        String postjson=jedis.get("article"+postId+"Data");
        Post post=JSON.parseObject(postjson,Post.class);
        post.setTitle("bbbbb");
        post.setContent("jake");
        String post1=JSON.toJSONString(post);
        jedis.set("post"+postId+"Data",post1);
        System.out.println("修改成功");

    }





}