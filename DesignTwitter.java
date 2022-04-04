class Twitter {
    HashMap<Integer, List<Integer>> follow;
    HashMap<Integer, PriorityQueue<Pair<Integer, Integer>>> tweet;
    int time=0;
    
    public Twitter() {
        follow= new HashMap<>();
        tweet = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        
        if(tweet.containsKey(userId)){
            PriorityQueue<Pair<Integer, Integer>> pq=tweet.get(userId);
            pq.add(new Pair <> (tweetId,time));
            time++;
            if(pq.size()> 10){
                pq.poll();
            }
        }
        else{
             PriorityQueue<Pair<Integer, Integer>> pq= new PriorityQueue<>((a,b)-> a.getValue()-b.getValue());
            pq.add(new Pair <Integer,Integer> (tweetId,time));
            time++;
            tweet.put(userId, pq);
        }
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Pair<Integer, Integer>> pq= new PriorityQueue<>((a,b)-> a.getValue()-b.getValue() );
        if(tweet.containsKey(userId)) {
            for(Pair<Integer,Integer> followee_tweets : tweet.get(userId)) {
                        pq.add(followee_tweets);
                        if(pq.size()>10)
                            pq.poll();
                    }
        }
        
        if (follow.containsKey(userId)){
            List<Integer> fList = follow.get(userId);
            for(int followee : fList) {
                if(tweet.containsKey(followee)) {
                    for(Pair<Integer,Integer> followee_tweets : tweet.get(followee)) {
                        pq.add(followee_tweets);
                        if(pq.size()>10)
                            pq.poll();
                    }
                }
            }
        }
        
        List<Integer> result = new ArrayList<>();
        while(pq.size()>0) {
            Pair<Integer, Integer> pp= pq.poll();
            result.add(0,pp.getKey());
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(follow.containsKey(followerId)){
            List<Integer> ll= follow.get(followerId);
            if(!ll.contains(followeeId))
                ll.add(followeeId);
        }
        else{
            List<Integer> ll= new ArrayList<>();
            ll.add(followeeId);
            follow.put(followerId, ll);
        }   
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(follow.containsKey(followerId)){
            List<Integer> ll= follow.get(followerId);
            int index = ll.indexOf(followeeId);
            ll.remove(index);
        }
    }
}

