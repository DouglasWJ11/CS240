package spell;

public class Trie implements ITrie {
	
	private int nodeCount;
	private int wordCount;
	private Node rootNode;
	private StringBuilder wordList;
	public Trie(){
		rootNode=new Node();
		nodeCount=0;
		wordCount=0;
	}
	@Override
	public void add(String word) {
		// TODO Auto-generated method stub
		word=word.toLowerCase();
		loopAdd(word,0,rootNode);
	}
	public void loopAdd(String word,int index,Node node){
		if(index==word.length())
		{
			node.count++;
			wordCount++;
			return;
		}
		char c = word.charAt(index);
		int loc=c-97;
		if(node.Nodes[loc]==null)
		{
			node.Nodes[loc]=new Node();
			nodeCount++;
			int next=index+1;
			loopAdd(word,next,node.Nodes[loc]);
		}
		else if(node.Nodes[loc]!=null){
			int next=index+1;
			loopAdd(word,next,node.Nodes[loc]);
		}
	}
	public Node loopFind(String word, int index, Node node){
		if(index==word.length()){
			if(node.count>0) return node;
		}
		else{
			char c=word.charAt(index);
			int loc=c-97;
			if(node.Nodes[loc]!=null){
				int next=index+1;
				return loopFind(word,next,node.Nodes[loc]);
			}
			else return null;
		}
		return null;
	}
	@Override
	public ITrie.INode find(String word) {
		// TODO Auto-generated method stub
		return loopFind(word,0,rootNode);
	}

	@Override
	public int getWordCount() {
		// TODO Auto-generated method stub
		return wordCount;
	}

	@Override
	public int getNodeCount() {
		// TODO Auto-generated method stub
		return nodeCount;
	}
	public class Node implements INode{
		
		/**
		 * Returns the frequency count for the word represented by the node
		 * 
		 * @return The frequency count for the word represented by the node
		 */
		private Node(){
			Nodes=new Node[26];
			count=0;
		}
		private int count;
		private Node[] Nodes; 
		public int getValue(){
			return count;
		}
	}
	@Override
	public String toString(){
		StringBuilder word=new StringBuilder();
		wordList=new StringBuilder();
		loopWord(rootNode,word);
		return wordList.toString();
	}
	public String loopWord(Node n,StringBuilder sb){
		if(n==null)return null;
		else{
			for(int i=0;i<26;i++){
				if(n.Nodes[i]!=null){
					int ascii=i+97;
					sb.append(Character.toString((char)ascii));
					if(n.Nodes[i].count>0){
						wordList.append(sb.toString());
						wordList.append("\n");
					}
					loopWord(n.Nodes[i],sb);
				}
			}
			if(sb.length()!=0){
				sb.deleteCharAt(sb.length()-1);
			}
		}
		return sb.toString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nodeCount;
		Node g=null;
		while(g==null){
			for(int i=0;i<26;i++){
				Node n=rootNode.Nodes[i];
				if(n!=null){
					g=n;
					result=prime*result*i;
				}
			}
		}
		result = prime * result + ((rootNode == null) ? 0 : rootNode.hashCode());
		result = prime * result + wordCount;
		return result/nodeCount;
	}
	public Boolean loopNode(Node n, Node m){
		if(n==null||m==null) return null;
		else if(n.count!=m.count) return false;
		else{
			for(int i=0;i<26;i++){
				loopNode(n.Nodes[i],m.Nodes[i]);
			}
		}
		return true;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if(o instanceof Trie){
			Trie t=(Trie) o;
			if(this.wordCount!=t.wordCount) return false;
			if(this.nodeCount!=t.nodeCount) return false;
			return(loopNode(this.rootNode,t.rootNode));
		}
		else return false;

		
	}
}

