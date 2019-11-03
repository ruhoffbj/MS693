import java.util.Properties;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;


public class SecondTryCoreNLP {

	public static void main(String[] args) {
		 Properties props = new Properties();
	        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
	        @SuppressWarnings("unused")
			StanfordCoreNLP coreNLP = new StanfordCoreNLP(props);
	}

}
