package top.goodyp.test;

//前缀树
public class PreTree {
    public String longestCommonPrefix(String[] strs){
        Tire tire = new Tire();
        for ( String str:strs ){
            if ( str.length()==0 )
                return "";
            tire.insert(str);
        }
        return  tire.searchMaxPrefix();
    }
    class Tire{
        class TireNode{
            TireNode[] child;
            boolean flag;
            public TireNode(){
                child = new TireNode[26];
                flag = false;
            }
        }
        //前缀树的根节点
        TireNode root;

        public Tire(){
            root = new TireNode();
        }

        //插入每一个单词进入前缀树
        public void insert(String str){
            TireNode cur = root;
            for (int i=0;i<str.length(); i++){
                if (cur.child[str.charAt(i)-'a']==null)
                    cur.child[str.charAt(i)-'a']=new TireNode();
                cur=cur.child[str.charAt(i)-'a'];
            }
            cur.flag = true;
        }

        //查找最长公共前缀
        public String searchMaxPrefix(){
            StringBuilder sb = new StringBuilder();
            TireNode cur = root;
            Character c = isPrefixNode(cur);
            while( !cur.flag && c!=null ){
                sb.append(c);
                cur = cur.child[c-'a'];
                c=isPrefixNode(cur);
            }
            return sb.toString();
        }

        //判断当前这个节点是否是整个树的前缀，也就是孩子只有一个字母
        private Character isPrefixNode(TireNode cur){
            Character c = null;
            int cnt=0;
            for ( int i=0;i<26;i++){
                if (cur.child[i]!=null){
                    c=(char)('a'+i);
                    cnt++;
                }
                if (cnt>1){
                    break;
                }
            }
            return cnt==1?c:null;
        }
    }

}
