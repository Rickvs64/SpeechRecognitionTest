import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

import java.io.*;

public class SampleApp {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();

        // Set path to acoustic model.
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        // Set path to dictionary.
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        // Set language model.
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        // Create StreamSpeechRecognizer.
        StreamSpeechRecognizer recognizer = null;
        try {
            recognizer = new StreamSpeechRecognizer(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream stream = null;
        try {
            // Read from test.wav (in project root directory).
            stream = new FileInputStream(new File("test.wav"));
        } catch (FileNotFoundException e) {
            // Display full stack trace if file cannot be located.
            e.printStackTrace();
        }

        recognizer.startRecognition(stream);
        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
            // Final (detected) output in English.
            System.out.format("Hypothesis: %s\n", result.getHypothesis());
        }
        // Needs to be called in order to close the application.
        recognizer.stopRecognition();
    }
}
