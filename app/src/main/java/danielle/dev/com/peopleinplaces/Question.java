package danielle.dev.com.peopleinplaces;

/**
 * Created by daniellevass on 04/07/15.
 */
public class Question {

    public enum QuestionType {
        POPULATION, BOYS, GIRLS,
        BIRTHS, BIRTHS_U18, DEATHS;
    }

    private String question;
    private RecordModel one;
    private RecordModel two;
    private QuestionType questionType;

    public String getQuestion() {
        return question;
    }

    public RecordModel getOne() {
        return one;
    }

    public RecordModel getTwo() {
        return two;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOne(RecordModel one) {
        this.one = one;
    }

    public void setTwo(RecordModel two) {
        this.two = two;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }


}
