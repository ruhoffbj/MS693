import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;


//http://stackoverflow.com/questions/32248522/how-to-interprete-rnncoreannotations-getpredictedclass-value
/*
0: "Very Negative"
1: "Negative" 
2: "Neutral" 
3: "Positive" 
4: "Very Positive"
*/

public class NLP {
    StanfordCoreNLP pipeline;

    public  void init() {
		 Properties props = new Properties();
	     props.put("annotators", "tokenize, ssplit, parse, sentiment");
	     pipeline = new StanfordCoreNLP(props);
    }

    public String findSentiment(String textMsg) {
    	StringBuilder sb = new StringBuilder();
        if (textMsg != null && textMsg.length() > 0) {
            Annotation annotation = this.pipeline.process(textMsg);

            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentAnnotatedTree.class);
                Integer sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                
                sb.append( sentiment.toString() + "=>" + sentence.toString() + "\n"  );
            }
            sb.append("[~DONE~]\n");
        }
        return sb.toString();
    }
}
