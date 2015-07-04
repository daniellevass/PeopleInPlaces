package danielle.dev.com.peopleinplaces;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.johnpersano.supertoasts.SuperCardToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;

import java.util.List;

import cyd.awesome.material.AwesomeButton;
import cyd.awesome.material.AwesomeText;
import cyd.awesome.material.FontCharacterMaps;


public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = "DANIELLE_TAG";
    private Question question;
    private List<RecordModel> allRecordModels;

    private TextView lblTitle;
    private Button btnOne;
    private Button btnTwo;
    private AwesomeText lblIcon;

    private TextView lblScore;
    private AwesomeText lblLife1;
    private AwesomeText lblLife2;
    private AwesomeText lblLife3;


    private int score;
    private int lives;

    private String finalString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblTitle = (TextView)findViewById(R.id.lblQuestion);
        btnOne = (Button)findViewById(R.id.btnOne);
        btnTwo = (Button)findViewById(R.id.btnTwo);
        lblIcon = (AwesomeText)findViewById(R.id.lblIcon);

        lblScore = (TextView)findViewById(R.id.lblScore);

        lblLife1 = (AwesomeText)findViewById(R.id.lblLives1);
        lblLife2 = (AwesomeText)findViewById(R.id.lblLives2);
        lblLife3 = (AwesomeText)findViewById(R.id.lblLives3);


        allRecordModels = RecordModel.listAll(RecordModel.class);


        if (allRecordModels != null &&
                allRecordModels.size() > 0){
            //we have data

            Log.d(LOG_TAG, "we have "+allRecordModels.size()+" records");

        } else {

            Toast.makeText(this, "Reading data", Toast.LENGTH_SHORT).show();

            Log.d(LOG_TAG, "reading in data...");
            readAllData();

            allRecordModels = RecordModel.listAll(RecordModel.class);

        }

        score = 0;
        lives = 3;

        generateQuestion();

        displayQuestion();

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickedOption(0);
            }
        });

        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickedOption(1);
            }
        });

    }

    private int randomNumber(int low, int high){
        return (int) (Math.random() * (high - low)) + low;
    }

    private void generateQuestion(){

        int random = randomNumber(0,6);

        Log.d(LOG_TAG, "random number = " + random);

        question = new Question();


        //0 population
        //1 boys
        //2 girls

        //3 births
        //4 births U18

        //5 deaths

        switch(random){
            case 0:
                question.setQuestion("Which has more people?");
                question.setQuestionType(Question.QuestionType.POPULATION);
                break;
            case 1:
                question.setQuestion("Which has more boys?");
                question.setQuestionType(Question.QuestionType.BOYS);
                break;
            case 2:
                question.setQuestion("Which has more girls?");
                question.setQuestionType(Question.QuestionType.GIRLS);
                break;
            case 3:
                question.setQuestion("Which has the highest birth rate?");
                question.setQuestionType(Question.QuestionType.BIRTHS);
                break;
            case 4:
                question.setQuestion("Which has the highest birth rate for under 18s?");
                question.setQuestionType(Question.QuestionType.BIRTHS_U18);
                break;

            case 5:
                question.setQuestion("Which has the highest death rate?");
                question.setQuestionType(Question.QuestionType.DEATHS);
                break;

        }


        int a = randomNumber(0, allRecordModels.size());
        int b = randomNumber(0, allRecordModels.size());

        question.setOne(allRecordModels.get(a));
        question.setTwo(allRecordModels.get(b));

    }

    private void displayQuestion(){
        lblTitle.setText(question.getQuestion());

        btnOne.setText(question.getOne().getName());
        btnTwo.setText(question.getTwo().getName());


        switch(question.getQuestionType()){
            case POPULATION:
                lblIcon.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_PEOPLE);
                lblIcon.setTextColor( getResources().getColor(R.color.md_indigo_600));
                break;
            case BOYS:
                lblIcon.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_PERSON);
                lblIcon.setTextColor(getResources().getColor(R.color.md_blue_700));
                break;
            case GIRLS:
                lblIcon.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_FACE_UNLOCK);
                lblIcon.setTextColor(getResources().getColor(R.color.md_pink_700));
                break;
            case BIRTHS:
                lblIcon.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_LOCAL_HOSPITAL);
                lblIcon.setTextColor(getResources().getColor(R.color.md_yellow_700));
                break;
            case BIRTHS_U18:
                lblIcon.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_LOCAL_BAR);
                lblIcon.setTextColor(getResources().getColor(R.color.md_indigo_600));
                break;
            case DEATHS:
                lblIcon.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_NEW_RELEASES);
                lblIcon.setTextColor(getResources().getColor(R.color.md_red_700));
                break;
        }
    }


    private void pickedOption(int answer){


        double answer1 = 0;
        double answer2 = 0;


        switch(question.getQuestionType()){
            case POPULATION:
                answer1 = (double)question.getOne().getPopulation();
                answer2 = (double)question.getTwo().getPopulation();
                break;
            case BOYS:
                answer1 = (double)question.getOne().getBoys();
                answer2 = (double)question.getTwo().getBoys();
                break;
            case GIRLS:
                answer1 = (double)question.getOne().getGirls();
                answer2 = (double)question.getTwo().getGirls();
                break;
            case BIRTHS:
                answer1 = (double)question.getOne().getBirths();
                answer2 = (double)question.getTwo().getBirths();
                break;
            case BIRTHS_U18:
                answer1 = (double)question.getOne().getBirthsUnder18();
                answer2 = (double)question.getTwo().getBirthsUnder18();
                break;
            case DEATHS:
                answer1 = (double)question.getOne().getDeaths();
                answer2 = (double)question.getTwo().getDeaths();
                break;
        }




        if (answer == 0){

            //picked one to be higher

            if (answer1 >= answer2){

                finalString = "You were right "+question.getOne().getName()+" has "+ answer1
                        +" which is is more than "+answer2+" in " + question.getTwo().getName();

                SuperCardToast.create(MainActivity.this,finalString, SuperToast.Duration.LONG,
                        Style.getStyle(Style.GREEN, SuperToast.Animations.FLYIN)).show();

                wasRight();
            } else {

                finalString = "You were wrong "+question.getTwo().getName()+" has "+ answer2
                        +" which is is more than "+answer1+" in " + question.getOne().getName();

                SuperCardToast.create(MainActivity.this, finalString, SuperToast.Duration.LONG,
                        Style.getStyle(Style.RED, SuperToast.Animations.FLYIN)).show();
                wasWrong();
            }

        } else {

            if (answer1 <= answer2){

                finalString = "You were right "+question.getTwo().getName()+" has "+ answer2
                        +" which is is more than "+answer1+" in " + question.getOne().getName();

                SuperCardToast.create(MainActivity.this, finalString, SuperToast.Duration.LONG,
                        Style.getStyle(Style.GREEN, SuperToast.Animations.FLYIN)).show();
                wasRight();
            } else {

                finalString = "You were wrong!  "+question.getOne().getName()+" has "+ answer1
                        +" which is is more than "+answer2+" in " + question.getTwo().getName();

                SuperCardToast.create(MainActivity.this, finalString, SuperToast.Duration.LONG,
                        Style.getStyle(Style.RED, SuperToast.Animations.FLYIN)).show();
                wasWrong();
            }
        }

        generateQuestion();
        displayQuestion();

    }


    private void wasRight(){
        score ++;
        //display score

        lblScore.setText("Score = " + score);
    }

    private void wasWrong(){
        lives --;

        if (lives == 2){
            lblLife3.setTextColor(getResources().getColor(R.color.md_red_600_disabled));
        }

        if (lives == 1){
            lblLife2.setTextColor(getResources().getColor(R.color.md_red_600_disabled));
        }


        if (lives == 0){
            //has lost
            lblLife1.setTextColor(getResources().getColor(R.color.md_red_600_disabled));

            String message = finalString + "\nYour score was : "+ score;

            new MaterialDialog.Builder(this)
                    .title("You lose :-(")
                    .content(message)
                    .positiveText("Ok")
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {

                        }
                    })
                    .show();

        }


    }


    private static void readAllData(){

        try {
            //delete anything already in the database
            RecordModel.deleteAll(RecordModel.class);

            new RecordModel("E06000001", "Hartlepool", "NORTH EAST", 92590, 45207, 47383, (double) 58.9, (double) 8.5, (double) 1093.9).save();
            new RecordModel("E06000002", "Middlesbrough", "NORTH EAST", 139119, 68370, 70749, (double) 69.8, (double) 15.3, (double) 1192.2).save();
            new RecordModel("E06000003", "Redcar and Cleveland", "NORTH EAST", 135042, 65623, 69419, (double) 64.9, (double) 11.2, (double) 1068.3).save();
            new RecordModel("E06000004", "Stockton-on-Tees", "NORTH EAST", 194119, 95081, 99038, (double) 64.9, (double) 16.3, (double) 1036.8).save();
            new RecordModel("E06000005", "Darlington", "NORTH EAST", 105367, 51178, 54189, (double) 62.2, (double) 8.2, (double) 1058.6).save();
            new RecordModel("E06000006", "Halton", "NORTH WEST", 126354, 61652, 64702, (double) 65.8, (double) 8.8, (double) 1232.7).save();
            new RecordModel("E06000007", "Warrington", "NORTH WEST", 206428, 102406, 104022, (double) 61.1, (double) 7, (double) 1142.1).save();
            new RecordModel("E06000008", "Blackburn with Darwen", "NORTH WEST", 146743, 73133, 73610, (double) 75.6, (double) 9.7, (double) 1166.1).save();
            new RecordModel("E06000009", "Blackpool", "NORTH WEST", 140501, 69083, 71418, (double) 66.9, (double) 12, (double) 1260.3).save();
            new RecordModel("E06000010", "Kingston upon Hull", "YORKSHIRE AND THE HUMBER", 257710, 129320, 128390, (double) 67.5, (double) 15.2, (double) 1219.2).save();
            new RecordModel("E06000011", "East Riding of Yorkshire", "YORKSHIRE AND THE HUMBER", 337115, 165166, 171949, (double) 55.2, (double) 8.6, (double) 980.8).save();
            new RecordModel("E06000012", "North East Lincolnshire", "YORKSHIRE AND THE HUMBER", 159804, 78386, 81418, (double) 65.6, (double) 14.8, (double) 1102.1).save();
            new RecordModel("E06000013", "North Lincolnshire", "YORKSHIRE AND THE HUMBER", 169247, 83587, 85660, (double) 61.3, (double) 9.5, (double) 1054.4).save();
            new RecordModel("E06000014", "York", "YORKSHIRE AND THE HUMBER", 204439, 99901, 104538, (double) 46.3, (double) 9.4, (double) 954.2).save();
            new RecordModel("E06000015", "Derby", "EAST MIDLANDS", 252463, 125035, 127428, (double) 64.6, (double) 9, (double) 1065.7).save();
            new RecordModel("E06000016", "Leicester", "EAST MIDLANDS", 337653, 167332, 170321, (double) 64.9, (double) 11.7, (double) 1157.6).save();
            new RecordModel("E06000017", "Rutland", "EAST MIDLANDS", 38022, 19554, 18468, (double) 58.6, (double) 5.7, (double) 742.2).save();
            new RecordModel("E06000018", "Nottingham", "EAST MIDLANDS", 314268, 158693, 155575, (double) 55.8, (double) 13, (double) 1114.1).save();
            new RecordModel("E06000019", "Herefordshire", "WEST MIDLANDS", 187160, 92519, 94641, (double) 60.3, (double) 5.9, (double) 882.7).save();
            new RecordModel("E06000020", "Telford and Wrekin", "WEST MIDLANDS", 169440, 83968, 85472, (double) 67.3, (double) 13.9, (double) 1084.5).save();
            new RecordModel("E06000021", "Stoke-on-Trent", "WEST MIDLANDS", 251027, 125075, 125952, (double) 70.9, (double) 16.8, (double) 1195.5).save();
            new RecordModel("E06000022", "Bath and North East Somerset", "SOUTH WEST", 182021, 89621, 92400, (double) 51, (double) 4.7, (double) 908.3).save();
            new RecordModel("E06000023", "Bristol", "SOUTH WEST", 442474, 220926, 221548, (double) 63.1, (double) 9.8, (double) 1083.5).save();
            new RecordModel("E06000024", "North Somerset", "SOUTH WEST", 208154, 101230, 106924, (double) 64, (double) 5.8, (double) 931.3).save();
            new RecordModel("E06000025", "South Gloucestershire", "SOUTH WEST", 271556, 134830, 136726, (double) 59.5, (double) 3.9, (double) 871.4).save();
            new RecordModel("E06000026", "Plymouth", "SOUTH WEST", 261546, 129958, 131588, (double) 59.2, (double) 13.9, (double) 1035.1).save();
            new RecordModel("E06000027", "Torbay", "SOUTH WEST", 132984, 64332, 68652, (double) 69.3, (double) 10, (double) 997.9).save();
            new RecordModel("E06000028", "Bournemouth", "SOUTH WEST", 191390, 96277, 95113, (double) 58.7, (double) 6.9, (double) 995).save();
            new RecordModel("E06000029", "Poole", "SOUTH WEST", 150109, 73642, 76467, (double) 61.7, (double) 5.5, (double) 913.9).save();
            new RecordModel("E06000030", "Swindon", "SOUTH WEST", 215799, 107565, 108234, (double) 67.3, (double) 7.4, (double) 1020.6).save();
            new RecordModel("E06000031", "Peterborough", "EAST", 190461, 94952, 95509, (double) 81.5, (double) 13.6, (double) 1013.9).save();
            new RecordModel("E06000032", "Luton", "EAST", 210962, 106427, 104535, (double) 76.5, (double) 7.8, (double) 984.6).save();
            new RecordModel("E06000033", "Southend-on-Sea", "EAST", 177931, 87216, 90715, (double) 66.3, (double) 10.9, (double) 1023.7).save();
            new RecordModel("E06000034", "Thurrock", "EAST", 163270, 80424, 82846, (double) 68.7, (double) 8, (double) 1044).save();
            new RecordModel("E06000035", "Medway", "SOUTH EAST", 274015, 136005, 138010, (double) 63.5, (double) 10.5, (double) 1020.9).save();
            new RecordModel("E06000036", "Bracknell Forest", "SOUTH EAST", 118025, 58621, 59404, (double) 61.3, (double) 2.2, (double) 842.4).save();
            new RecordModel("E06000037", "West Berkshire", "SOUTH EAST", 155732, 77248, 78484, (double) 61.4, (double) 4.9, (double) 919.6).save();
            new RecordModel("E06000038", "Reading", "SOUTH EAST", 160825, 80882, 79943, (double) 68.6, (double) 9.9, (double) 1055.7).save();
            new RecordModel("E06000039", "Slough", "SOUTH EAST", 144575, 72421, 72154, (double) 78.7, (double) 5, (double) 1048.5).save();
            new RecordModel("E06000040", "Windsor and Maidenhead", "SOUTH EAST", 147400, 72910, 74490, (double) 61.7, (double) 1.9, (double) 876.6).save();
            new RecordModel("E06000041", "Wokingham", "SOUTH EAST", 159097, 78603, 80494, (double) 61.3, (double) 3.2, (double) 859.4).save();
            new RecordModel("E06000042", "Milton Keynes", "SOUTH EAST", 259245, 128347, 130898, (double) 72.7, (double) 7.2, (double) 997.6).save();
            new RecordModel("E06000043", "Brighton and Hove", "SOUTH EAST", 281076, 140929, 140147, (double) 44.7, (double) 5.3, (double) 965.2).save();
            new RecordModel("E06000044", "Portsmouth", "SOUTH EAST", 209085, 105904, 103181, (double) 59.6, (double) 12.1, (double) 1085.8).save();
            new RecordModel("E06000045", "Southampton", "SOUTH EAST", 245290, 124648, 120642, (double) 57.5, (double) 13.8, (double) 1060).save();
            new RecordModel("E06000046", "Isle of Wight", "SOUTH EAST", 139105, 67951, 71154, (double) 60, (double) 14.6, (double) 955.2).save();
            new RecordModel("E06000056", "Central Bedfordshire", "EAST", 269076, 133245, 135831, (double) 64.5, (double) 6.9, (double) 908.9).save();
            new RecordModel("E06000055", "Bedford", "EAST", 163924, 80688, 83236, (double) 65.7, (double) 9.5, (double) 925).save();
            new RecordModel("E07000004", "Aylesbury Vale", "SOUTH EAST", 184560, 91121, 93439, (double) 61.8, (double) 8, (double) 909.9).save();
            new RecordModel("E07000005", "Chiltern", "SOUTH EAST", 93972, 45588, 48384, (double) 56, (double) 2.2, (double) 766.2).save();
            new RecordModel("E07000006", "South Bucks", "SOUTH EAST", 68512, 33057, 35455, (double) 62.1, (double) 2.5, (double) 797.3).save();
            new RecordModel("E07000007", "Wycombe", "SOUTH EAST", 174878, 86043, 88835, (double) 63.8, (double) 4, (double) 789.1).save();
            new RecordModel("E07000008", "Cambridge", "EAST", 128515, 66544, 61971, (double) 42.8, (double) 6.3, (double) 945.7).save();
            new RecordModel("E07000009", "East Cambridgeshire", "EAST", 86685, 42658, 44027, (double) 64.9, (double) 4.6, (double) 791).save();
            new RecordModel("E07000010", "Fenland", "EAST", 97732, 48280, 49452, (double) 69.5, (double) 11.6, (double) 986).save();
            new RecordModel("E07000011", "Huntingdonshire", "EAST", 173605, 86358, 87247, (double) 62.8, (double) 4.8, (double) 893.8).save();
            new RecordModel("E07000012", "South Cambridgeshire", "EAST", 153281, 75710, 77571, (double) 65.4, (double) 5.8, (double) 796.2).save();
            new RecordModel("E06000050", "Cheshire West and Chester", "NORTH WEST", 332210, 161836, 170374, (double) 58.9, (double) 7.7, (double) 964.8).save();
            new RecordModel("E06000049", "Cheshire East", "NORTH WEST", 374179, 183245, 190934, (double) 58.9, (double) 6.9, (double) 923.3).save();
            new RecordModel("E06000052", "Cornwall", "SOUTH WEST", 545335, 264718, 280617, (double) 61.8, (double) 8.8, (double) 969.8).save();
            new RecordModel("E06000053", "Isles of Scilly", "SOUTH WEST", 2280, 1148, 1132, (double) 61.8, (double) 8.8, (double) 969.8).save();
            new RecordModel("E07000026", "Allerdale", "NORTH WEST", 96471, 47437, 49034, (double) 56, (double) 7.8, (double) 992.8).save();
            new RecordModel("E07000027", "Barrow-in-Furness", "NORTH WEST", 67648, 33444, 34204, (double) 63.3, (double) 11.4, (double) 1151.2).save();
            new RecordModel("E07000028", "Carlisle", "NORTH WEST", 108022, 52770, 55252, (double) 58.8, (double) 5.5, (double) 992.4).save();
            new RecordModel("E07000029", "Copeland", "NORTH WEST", 69832, 35081, 34751, (double) 63.7, (double) 10.9, (double) 1162.8).save();
            new RecordModel("E07000030", "Eden", "NORTH WEST", 52630, 26086, 26544, (double) 55.6, (double) 0, (double) 848.7).save();
            new RecordModel("E07000031", "South Lakeland", "NORTH WEST", 103271, 50439, 52832, (double) 51.8, (double) 4.5, (double) 887.8).save();
            new RecordModel("E07000032", "Amber Valley", "EAST MIDLANDS", 123942, 60844, 63098, (double) 58, (double) 5.8, (double) 1009.2).save();
            new RecordModel("E07000033", "Bolsover", "EAST MIDLANDS", 77155, 38033, 39122, (double) 62.7, (double) 7.1, (double) 1127.1).save();
            new RecordModel("E07000034", "Chesterfield", "EAST MIDLANDS", 104288, 51173, 53115, (double) 58.2, (double) 12.4, (double) 1078.1).save();
            new RecordModel("E07000035", "Derbyshire Dales", "EAST MIDLANDS", 71281, 35114, 36167, (double) 47.4, (double) 3.9, (double) 894.9).save();
            new RecordModel("E07000036", "Erewash", "EAST MIDLANDS", 114048, 55770, 58278, (double) 59.2, (double) 8.1, (double) 1015.6).save();
            new RecordModel("E07000037", "High Peak", "EAST MIDLANDS", 91364, 44883, 46481, (double) 54.3, (double) 5.4, (double) 987).save();
            new RecordModel("E07000038", "North East Derbyshire", "EAST MIDLANDS", 99352, 48657, 50695, (double) 51, (double) 4.2, (double) 931.8).save();
            new RecordModel("E07000039", "South Derbyshire", "EAST MIDLANDS", 98374, 48633, 49741, (double) 60.5, (double) 7.3, (double) 1048.5).save();
            new RecordModel("E07000040", "East Devon", "SOUTH WEST", 136374, 65854, 70520, (double) 58.1, (double) 5.3, (double) 857.7).save();
            new RecordModel("E07000041", "Exeter", "SOUTH WEST", 124328, 61278, 63050, (double) 48.6, (double) 7.3, (double) 915.1).save();
            new RecordModel("E07000042", "Mid Devon", "SOUTH WEST", 79198, 38821, 40377, (double) 60.7, (double) 6.8, (double) 768.8).save();
            new RecordModel("E07000043", "North Devon", "SOUTH WEST", 94059, 45995, 48064, (double) 63.7, (double) 10.2, (double) 932.6).save();
            new RecordModel("E07000044", "South Hams", "SOUTH WEST", 84108, 40630, 43478, (double) 54.6, (double) 5.8, (double) 833.8).save();
            new RecordModel("E07000045", "Teignbridge", "SOUTH WEST", 127357, 61562, 65795, (double) 62.1, (double) 6.7, (double) 891.1).save();
            new RecordModel("E07000046", "Torridge", "SOUTH WEST", 65618, 32088, 33530, (double) 63.6, (double) 3.8, (double) 932.7).save();
            new RecordModel("E07000047", "West Devon", "SOUTH WEST", 54260, 26451, 27809, (double) 59.6, (double) 6.5, (double) 893.5).save();
            new RecordModel("E07000048", "Christchurch", "SOUTH WEST", 48895, 23488, 25407, (double) 60.3, (double) 5, (double) 804.2).save();
            new RecordModel("E07000049", "East Dorset", "SOUTH WEST", 88186, 42428, 45758, (double) 52, (double) 2.1, (double) 764.1).save();
            new RecordModel("E07000050", "North Dorset", "SOUTH WEST", 70043, 34891, 35152, (double) 60.7, (double) 3.9, (double) 809).save();
            new RecordModel("E07000051", "Purbeck", "SOUTH WEST", 45679, 22533, 23146, (double) 59.6, (double) 9.5, (double) 908.3).save();
            new RecordModel("E07000052", "West Dorset", "SOUTH WEST", 100474, 48364, 52110, (double) 55.6, (double) 6.5, (double) 857.5).save();
            new RecordModel("E07000053", "Weymouth and Portland", "SOUTH WEST", 64992, 32192, 32800, (double) 64, (double) 10.9, (double) 982.9).save();
            new RecordModel("E06000047", "County Durham", "NORTH EAST", 517773, 254211, 263562, (double) 56.3, (double) 12.2, (double) 1141.2).save();
            new RecordModel("E07000061", "Eastbourne", "SOUTH EAST", 101547, 48918, 52629, (double) 62.7, (double) 6.2, (double) 955.8).save();
            new RecordModel("E07000062", "Hastings", "SOUTH EAST", 91093, 44470, 46623, (double) 71.1, (double) 19.4, (double) 1070.2).save();
            new RecordModel("E07000063", "Lewes", "SOUTH EAST", 100229, 48701, 51528, (double) 58.1, (double) 6.6, (double) 839.1).save();
            new RecordModel("E07000064", "Rother", "SOUTH EAST", 92130, 43976, 48154, (double) 57.8, (double) 6.6, (double) 914.8).save();
            new RecordModel("E07000065", "Wealden", "SOUTH EAST", 154767, 74573, 80194, (double) 55.4, (double) 1.4, (double) 841.1).save();
            new RecordModel("E07000066", "Basildon", "EAST", 180521, 87694, 92827, (double) 69, (double) 11.3, (double) 970.3).save();
            new RecordModel("E07000067", "Braintree", "EAST", 149985, 73548, 76437, (double) 60.5, (double) 7.1, (double) 950.1).save();
            new RecordModel("E07000068", "Brentwood", "EAST", 75645, 36702, 38943, (double) 61.8, (double) 2, (double) 855.2).save();
            new RecordModel("E07000069", "Castle Point", "EAST", 88907, 43250, 45657, (double) 54.9, (double) 3.2, (double) 1045.1).save();
            new RecordModel("E07000070", "Chelmsford", "EAST", 171633, 84663, 86970, (double) 57.4, (double) 2.8, (double) 839.6).save();
            new RecordModel("E07000071", "Colchester", "EAST", 180420, 89321, 91099, (double) 61, (double) 8.4, (double) 920.3).save();
            new RecordModel("E07000072", "Epping Forest", "EAST", 128777, 62321, 66456, (double) 65.7, (double) 3.7, (double) 853.1).save();
            new RecordModel("E07000073", "Harlow", "EAST", 84564, 40945, 43619, (double) 72, (double) 12.1, (double) 939.4).save();
            new RecordModel("E07000074", "Maldon", "EAST", 62767, 31001, 31766, (double) 55.9, (double) 4.7, (double) 911.7).save();
            new RecordModel("E07000075", "Rochford", "EAST", 84776, 41464, 43312, (double) 49.5, (double) 4.3, (double) 871.7).save();
            new RecordModel("E07000076", "Tendring", "EAST", 139916, 67131, 72785, (double) 65.4, (double) 12.4, (double) 1039.8).save();
            new RecordModel("E07000077", "Uttlesford", "EAST", 84042, 41493, 42549, (double) 56.9, (double) 3.6, (double) 769).save();
            new RecordModel("E07000078", "Cheltenham", "SOUTH WEST", 116495, 57050, 59445, (double) 56.5, (double) 3.4, (double) 923.5).save();
            new RecordModel("E07000079", "Cotswold", "SOUTH WEST", 84637, 40798, 43839, (double) 54.2, (double) 2.8, (double) 863.2).save();
            new RecordModel("E07000080", "Forest of Dean", "SOUTH WEST", 83674, 41097, 42577, (double) 55.1, (double) 3.7, (double) 982.3).save();
            new RecordModel("E07000081", "Gloucester", "SOUTH WEST", 125649, 61933, 63716, (double) 70.1, (double) 11.3, (double) 1035.6).save();
            new RecordModel("E07000082", "Stroud", "SOUTH WEST", 115093, 56714, 58379, (double) 58.6, (double) 4.5, (double) 1091.2).save();
            new RecordModel("E07000083", "Tewkesbury", "SOUTH WEST", 85784, 42094, 43690, (double) 64.2, (double) 5.1, (double) 891).save();
            new RecordModel("E07000084", "Basingstoke and Deane", "SOUTH EAST", 172870, 85487, 87383, (double) 64.5, (double) 5.5, (double) 919.6).save();
            new RecordModel("E07000085", "East Hampshire", "SOUTH EAST", 117483, 57040, 60443, (double) 55.5, (double) 6.1, (double) 876).save();
            new RecordModel("E07000086", "Eastleigh", "SOUTH EAST", 128877, 63097, 65780, (double) 62.7, (double) 6.2, (double) 888).save();
            new RecordModel("E07000087", "Fareham", "SOUTH EAST", 114331, 56038, 58293, (double) 54.7, (double) 5.7, (double) 951.3).save();
            new RecordModel("E07000088", "Gosport", "SOUTH EAST", 84287, 41791, 42496, (double) 60.7, (double) 6.9, (double) 1068).save();
            new RecordModel("E07000089", "Hart", "SOUTH EAST", 93325, 46307, 47018, (double) 57.9, (double) 3, (double) 747.6).save();
            new RecordModel("E07000090", "Havant", "SOUTH EAST", 122210, 59323, 62887, (double) 60.7, (double) 11.5, (double) 978.9).save();
            new RecordModel("E07000091", "New Forest", "SOUTH EAST", 178907, 86015, 92892, (double) 54.7, (double) 2.7, (double) 800.3).save();
            new RecordModel("E07000092", "Rushmoor", "SOUTH EAST", 95296, 47678, 47618, (double) 65.7, (double) 7.7, (double) 1075.7).save();
            new RecordModel("E07000093", "Test Valley", "SOUTH EAST", 119332, 58174, 61158, (double) 60.8, (double) 4.8, (double) 886.5).save();
            new RecordModel("E07000094", "Winchester", "SOUTH EAST", 119218, 57753, 61465, (double) 57.6, (double) 4.2, (double) 802.1).save();
            new RecordModel("E07000095", "Broxbourne", "EAST", 95748, 46288, 49460, (double) 67.4, (double) 0, (double) 861.2).save();
            new RecordModel("E07000096", "Dacorum", "EAST", 149741, 73606, 76135, (double) 64.1, (double) 4.7, (double) 931.4).save();
            new RecordModel("E07000242", "East Hertfordshire", "EAST", 143021, 70154, 72867, (double) 59.3, (double) 2.2, (double) 829.3).save();
            new RecordModel("E07000098", "Hertsmere", "EAST", 102427, 49360, 53067, (double) 65.4, (double) 3.7, (double) 865.3).save();
            new RecordModel("E07000099", "North Hertfordshire", "EAST", 131046, 64343, 66703, (double) 65.9, (double) 4.7, (double) 967.9).save();
            new RecordModel("E07000240", "St Albans", "EAST", 144834, 70845, 73989, (double) 70.1, (double) 3.4, (double) 803.6).save();
            new RecordModel("E07000243", "Stevenage", "EAST", 85997, 42324, 43673, (double) 65.7, (double) 10.6, (double) 1018.6).save();
            new RecordModel("E07000102", "Three Rivers", "EAST", 90423, 43920, 46503, (double) 61.1, (double) 3.5, (double) 878.4).save();
            new RecordModel("E07000103", "Watford", "EAST", 95505, 47195, 48310, (double) 71.3, (double) 6.2, (double) 1035.7).save();
            new RecordModel("E07000241", "Welwyn Hatfield", "EAST", 116024, 57273, 58751, (double) 54.5, (double) 2.7, (double) 874.8).save();
            new RecordModel("E07000105", "Ashford", "SOUTH EAST", 123285, 59866, 63419, (double) 64, (double) 5, (double) 905.5).save();
            new RecordModel("E07000106", "Canterbury", "SOUTH EAST", 157649, 76666, 80983, (double) 43.2, (double) 7.5, (double) 894.7).save();
            new RecordModel("E07000107", "Dartford", "SOUTH EAST", 102234, 50376, 51858, (double) 68.7, (double) 6.5, (double) 1088.3).save();
            new RecordModel("E07000108", "Dover", "SOUTH EAST", 113066, 55646, 57420, (double) 61.6, (double) 12, (double) 961.5).save();
            new RecordModel("E07000109", "Gravesham", "SOUTH EAST", 105261, 51954, 53307, (double) 67.8, (double) 10.6, (double) 1012.7).save();
            new RecordModel("E07000110", "Maidstone", "SOUTH EAST", 161819, 79926, 81893, (double) 63, (double) 3.2, (double) 977.3).save();
            new RecordModel("E07000111", "Sevenoaks", "SOUTH EAST", 117811, 57046, 60765, (double) 60.1, (double) 5, (double) 868.2).save();
            new RecordModel("E07000112", "Shepway", "SOUTH EAST", 109452, 54068, 55384, (double) 58.9, (double) 10, (double) 923).save();
            new RecordModel("E07000113", "Swale", "SOUTH EAST", 140836, 69643, 71193, (double) 66, (double) 11.3, (double) 1048.2).save();
            new RecordModel("E07000114", "Thanet", "SOUTH EAST", 138410, 66755, 71655, (double) 67.7, (double) 11.7, (double) 1034.1).save();
            new RecordModel("E07000115", "Tonbridge and Malling", "SOUTH EAST", 124426, 60929, 63497, (double) 62.2, (double) 7.2, (double) 872.4).save();
            new RecordModel("E07000116", "Tunbridge Wells", "SOUTH EAST", 116105, 57182, 58923, (double) 59.6, (double) 3.6, (double) 842.6).save();
            new RecordModel("E07000117", "Burnley", "NORTH WEST", 87291, 42985, 44306, (double) 72.7, (double) 11.4, (double) 1187.5).save();
            new RecordModel("E07000118", "Chorley", "NORTH WEST", 111607, 55830, 55777, (double) 58.8, (double) 6.7, (double) 1029.1).save();
            new RecordModel("E07000119", "Fylde", "NORTH WEST", 77042, 37611, 39431, (double) 56.3, (double) 3.3, (double) 1021.3).save();
            new RecordModel("E07000120", "Hyndburn", "NORTH WEST", 80208, 39701, 40507, (double) 74, (double) 10.1, (double) 1228.4).save();
            new RecordModel("E07000121", "Lancaster", "NORTH WEST", 141277, 69263, 72014, (double) 51.7, (double) 9.4, (double) 1097.6).save();
            new RecordModel("E07000122", "Pendle", "NORTH WEST", 89840, 44316, 45524, (double) 74.5, (double) 8.1, (double) 1068.1).save();
            new RecordModel("E07000123", "Preston", "NORTH WEST", 140452, 70712, 69740, (double) 63.2, (double) 9.2, (double) 1134).save();
            new RecordModel("E07000124", "Ribble Valley", "NORTH WEST", 58091, 28505, 29586, (double) 45.8, (double) 2.6, (double) 879.9).save();
            new RecordModel("E07000125", "Rossendale", "NORTH WEST", 69168, 33932, 35236, (double) 62.9, (double) 10.5, (double) 1110.6).save();
            new RecordModel("E07000126", "South Ribble", "NORTH WEST", 109077, 53442, 55635, (double) 57.3, (double) 7.2, (double) 955.4).save();
            new RecordModel("E07000127", "West Lancashire", "NORTH WEST", 111940, 54451, 57489, (double) 56, (double) 5, (double) 1088.3).save();
            new RecordModel("E07000128", "Wyre", "NORTH WEST", 108742, 52952, 55790, (double) 55.5, (double) 6, (double) 1015.1).save();
            new RecordModel("E07000129", "Blaby", "EAST MIDLANDS", 95851, 47061, 48790, (double) 58.2, (double) 3.5, (double) 924.8).save();
            new RecordModel("E07000130", "Charnwood", "EAST MIDLANDS", 173545, 87249, 86296, (double) 50.2, (double) 10.9, (double) 921.7).save();
            new RecordModel("E07000131", "Harborough", "EAST MIDLANDS", 88008, 43627, 44381, (double) 53.3, (double) 3.8, (double) 870.7).save();
            new RecordModel("E07000132", "Hinckley and Bosworth", "EAST MIDLANDS", 107722, 53056, 54666, (double) 57.8, (double) 2.3, (double) 886.3).save();
            new RecordModel("E07000133", "Melton", "EAST MIDLANDS", 50969, 24982, 25987, (double) 57.2, (double) 9.5, (double) 921.5).save();
            new RecordModel("E07000134", "North West Leicestershire", "EAST MIDLANDS", 95882, 47456, 48426, (double) 56, (double) 5.5, (double) 992.9).save();
            new RecordModel("E07000135", "Oadby and Wigston", "EAST MIDLANDS", 55928, 27076, 28852, (double) 54.2, (double) 4.9, (double) 932.3).save();
            new RecordModel("E07000136", "Boston", "EAST MIDLANDS", 66458, 32615, 33843, (double) 71.2, (double) 6.3, (double) 1074.1).save();
            new RecordModel("E07000137", "East Lindsey", "EAST MIDLANDS", 137623, 67161, 70462, (double) 62.9, (double) 8.9, (double) 1033.3).save();
            new RecordModel("E07000138", "Lincoln", "EAST MIDLANDS", 96202, 47404, 48798, (double) 54.6, (double) 20.1, (double) 1135.6).save();
            new RecordModel("E07000139", "North Kesteven", "EAST MIDLANDS", 111046, 54324, 56722, (double) 57.7, (double) 7.1, (double) 874.7).save();
            new RecordModel("E07000140", "South Holland", "EAST MIDLANDS", 90419, 44243, 46176, (double) 61, (double) 10.2, (double) 970.2).save();
            new RecordModel("E07000141", "South Kesteven", "EAST MIDLANDS", 137981, 66644, 71337, (double) 57.6, (double) 9.8, (double) 908.7).save();
            new RecordModel("E07000142", "West Lindsey", "EAST MIDLANDS", 91787, 44843, 46944, (double) 57, (double) 7.8, (double) 998.9).save();
            new RecordModel("E07000143", "Breckland", "EAST", 133986, 66505, 67481, (double) 66.5, (double) 6.4, (double) 954.9).save();
            new RecordModel("E07000144", "Broadland", "EAST", 125961, 61437, 64524, (double) 52.8, (double) 2.2, (double) 915.5).save();
            new RecordModel("E07000145", "Great Yarmouth", "EAST", 98172, 48352, 49820, (double) 65.4, (double) 12.2, (double) 1068.6).save();
            new RecordModel("E07000146", "Kings Lynn and West Norfolk", "EAST", 150026, 73407, 76619, (double) 67.1, (double) 13.6, (double) 960.4).save();
            new RecordModel("E07000147", "North Norfolk", "EAST", 102867, 50034, 52833, (double) 60.2, (double) 3.6, (double) 875.6).save();
            new RecordModel("E07000148", "Norwich", "EAST", 137472, 68089, 69383, (double) 55.7, (double) 14, (double) 902.4).save();
            new RecordModel("E07000149", "South Norfolk", "EAST", 129226, 62998, 66228, (double) 59.3, (double) 5.2, (double) 827.5).save();
            new RecordModel("E07000150", "Corby", "EAST MIDLANDS", 65434, 32073, 33361, (double) 71.3, (double) 10.8, (double) 1181.3).save();
            new RecordModel("E07000151", "Daventry", "EAST MIDLANDS", 79036, 39429, 39607, (double) 62.6, (double) 4.3, (double) 973.6).save();
            new RecordModel("E07000152", "East Northamptonshire", "EAST MIDLANDS", 88872, 43942, 44930, (double) 64.9, (double) 8, (double) 915).save();
            new RecordModel("E07000153", "Kettering", "EAST MIDLANDS", 96945, 47643, 49302, (double) 65, (double) 4.7, (double) 982.4).save();
            new RecordModel("E07000154", "Northampton", "EAST MIDLANDS", 219495, 108317, 111178, (double) 70.2, (double) 12.9, (double) 1038.7).save();
            new RecordModel("E07000155", "South Northamptonshire", "EAST MIDLANDS", 88164, 43502, 44662, (double) 55.8, (double) 1.9, (double) 857.5).save();
            new RecordModel("E07000156", "Wellingborough", "EAST MIDLANDS", 76446, 37312, 39134, (double) 70.3, (double) 11.6, (double) 965.5).save();
            new RecordModel("E06000057", "Northumberland", "NORTH EAST", 315987, 154306, 161681, (double) 54, (double) 7.5, (double) 1017.3).save();
            new RecordModel("E07000163", "Craven", "YORKSHIRE AND THE HUMBER", 55696, 26866, 28830, (double) 54.8, (double) 7.6, (double) 879.2).save();
            new RecordModel("E07000164", "Hambleton", "YORKSHIRE AND THE HUMBER", 89828, 44156, 45672, (double) 58.7, (double) 4.6, (double) 862.6).save();
            new RecordModel("E07000165", "Harrogate", "YORKSHIRE AND THE HUMBER", 157267, 76732, 80535, (double) 55.7, (double) 5.4, (double) 833.1).save();
            new RecordModel("E07000166", "Richmondshire", "YORKSHIRE AND THE HUMBER", 52729, 28295, 24434, (double) 61, (double) 5.8, (double) 918.7).save();
            new RecordModel("E07000167", "Ryedale", "YORKSHIRE AND THE HUMBER", 52655, 25846, 26809, (double) 51.2, (double) 0, (double) 983.8).save();
            new RecordModel("E07000168", "Scarborough", "YORKSHIRE AND THE HUMBER", 108006, 52278, 55728, (double) 57.5, (double) 7.8, (double) 1061.1).save();
            new RecordModel("E07000169", "Selby", "YORKSHIRE AND THE HUMBER", 85355, 41861, 43494, (double) 59.6, (double) 5.1, (double) 964.2).save();
            new RecordModel("E07000170", "Ashfield", "EAST MIDLANDS", 122508, 60052, 62456, (double) 66.4, (double) 15.6, (double) 1048.8).save();
            new RecordModel("E07000171", "Bassetlaw", "EAST MIDLANDS", 114143, 56576, 57567, (double) 61.7, (double) 10.2, (double) 1072.2).save();
            new RecordModel("E07000172", "Broxtowe", "EAST MIDLANDS", 111780, 55336, 56444, (double) 59.6, (double) 4.8, (double) 935.8).save();
            new RecordModel("E07000173", "Gedling", "EAST MIDLANDS", 115638, 56423, 59215, (double) 60.9, (double) 5.4, (double) 947.8).save();
            new RecordModel("E07000174", "Mansfield", "EAST MIDLANDS", 105893, 52061, 53832, (double) 65.8, (double) 18.5, (double) 1092.3).save();
            new RecordModel("E07000175", "Newark and Sherwood", "EAST MIDLANDS", 117758, 57948, 59810, (double) 59.2, (double) 8.6, (double) 960.9).save();
            new RecordModel("E07000176", "Rushcliffe", "EAST MIDLANDS", 113670, 55986, 57684, (double) 54.6, (double) 2.6, (double) 862.4).save();
            new RecordModel("E07000177", "Cherwell", "SOUTH EAST", 144494, 71471, 73023, (double) 64.5, (double) 8, (double) 929.5).save();
            new RecordModel("E07000178", "Oxford", "SOUTH EAST", 157997, 79599, 78398, (double) 47, (double) 9.6, (double) 912.2).save();
            new RecordModel("E07000179", "South Oxfordshire", "SOUTH EAST", 137015, 67524, 69491, (double) 66.1, (double) 3.8, (double) 835.2).save();
            new RecordModel("E07000180", "Vale of White Horse", "SOUTH EAST", 124852, 62103, 62749, (double) 63.4, (double) 4.8, (double) 879.2).save();
            new RecordModel("E07000181", "West Oxfordshire", "SOUTH EAST", 108158, 53311, 54847, (double) 65.6, (double) 4.9, (double) 934.9).save();
            new RecordModel("E06000051", "Shropshire", "WEST MIDLANDS", 310121, 153763, 156358, (double) 56.7, (double) 4.8, (double) 957.2).save();
            new RecordModel("E07000187", "Mendip", "SOUTH WEST", 110844, 54013, 56831, (double) 56.6, (double) 6.5, (double) 906.1).save();
            new RecordModel("E07000188", "Sedgemoor", "SOUTH WEST", 119057, 58296, 60761, (double) 64.3, (double) 8.8, (double) 899.1).save();
            new RecordModel("E07000189", "South Somerset", "SOUTH WEST", 164569, 80783, 83786, (double) 66.6, (double) 8.4, (double) 863.6).save();
            new RecordModel("E07000190", "Taunton Deane", "SOUTH WEST", 112817, 54660, 58157, (double) 59, (double) 3.8, (double) 961.4).save();
            new RecordModel("E07000191", "West Somerset", "SOUTH WEST", 34322, 16447, 17875, (double) 60.7, (double) 6.3, (double) 893.4).save();
            new RecordModel("E07000192", "Cannock Chase", "WEST MIDLANDS", 98549, 48768, 49781, (double) 62.2, (double) 9.4, (double) 957.6).save();
            new RecordModel("E07000193", "East Staffordshire", "WEST MIDLANDS", 115663, 57598, 58065, (double) 65.8, (double) 7.1, (double) 1077.2).save();
            new RecordModel("E07000194", "Lichfield", "WEST MIDLANDS", 102093, 50541, 51552, (double) 55.3, (double) 7, (double) 1004).save();
            new RecordModel("E07000195", "Newcastle-under-Lyme", "WEST MIDLANDS", 126052, 62492, 63560, (double) 49.3, (double) 6.5, (double) 1089.7).save();
            new RecordModel("E07000196", "South Staffordshire", "WEST MIDLANDS", 110692, 55252, 55440, (double) 53.1, (double) 6.7, (double) 949.5).save();
            new RecordModel("E07000197", "Stafford", "WEST MIDLANDS", 132241, 66371, 65870, (double) 54.7, (double) 8.4, (double) 941.6).save();
            new RecordModel("E07000198", "Staffordshire Moorlands", "WEST MIDLANDS", 97763, 48200, 49563, (double) 51.4, (double) 10.8, (double) 1035.3).save();
            new RecordModel("E07000199", "Tamworth", "WEST MIDLANDS", 77112, 37800, 39312, (double) 61.2, (double) 13.7, (double) 1030.3).save();
            new RecordModel("E07000200", "Babergh", "EAST", 88845, 43264, 45581, (double) 53.5, (double) 2.5, (double) 874.5).save();
            new RecordModel("E07000201", "Forest Heath", "EAST", 62812, 31748, 31064, (double) 76.1, (double) 6.3, (double) 956).save();
            new RecordModel("E07000202", "Ipswich", "EAST", 134966, 67307, 67659, (double) 67.5, (double) 12, (double) 1001.9).save();
            new RecordModel("E07000203", "Mid Suffolk", "EAST", 99121, 49018, 50103, (double) 56, (double) 3.4, (double) 962.1).save();
            new RecordModel("E07000204", "St Edmundsbury", "EAST", 112073, 56155, 55918, (double) 61, (double) 4.2, (double) 843.4).save();
            new RecordModel("E07000205", "Suffolk Coastal", "EAST", 124776, 60799, 63977, (double) 51.6, (double) 4.3, (double) 873.9).save();
            new RecordModel("E07000206", "Waveney", "EAST", 115919, 56077, 59842, (double) 65.2, (double) 13.9, (double) 911.9).save();
            new RecordModel("E07000207", "Elmbridge", "SOUTH EAST", 132769, 64154, 68615, (double) 77.6, (double) 2.1, (double) 833.4).save();
            new RecordModel("E07000208", "Epsom and Ewell", "SOUTH EAST", 78318, 38000, 40318, (double) 61.1, (double) 3.4, (double) 811).save();
            new RecordModel("E07000209", "Guildford", "SOUTH EAST", 142958, 71200, 71758, (double) 52.7, (double) 4, (double) 865).save();
            new RecordModel("E07000210", "Mole Valley", "SOUTH EAST", 86234, 42049, 44185, (double) 58.7, (double) 2.6, (double) 798.7).save();
            new RecordModel("E07000211", "Reigate and Banstead", "SOUTH EAST", 143094, 70141, 72953, (double) 67.6, (double) 4.1, (double) 903.7).save();
            new RecordModel("E07000212", "Runnymede", "SOUTH EAST", 84584, 41322, 43262, (double) 51.9, (double) 6.4, (double) 908.2).save();
            new RecordModel("E07000213", "Spelthorne", "SOUTH EAST", 98106, 48455, 49651, (double) 71, (double) 5.1, (double) 914.9).save();
            new RecordModel("E07000214", "Surrey Heath", "SOUTH EAST", 87533, 43424, 44109, (double) 61.3, (double) 2.6, (double) 906.3).save();
            new RecordModel("E07000215", "Tandridge", "SOUTH EAST", 85374, 41503, 43871, (double) 59.9, (double) 3, (double) 934.9).save();
            new RecordModel("E07000216", "Waverley", "SOUTH EAST", 122860, 59989, 62871, (double) 60.5, (double) 1.7, (double) 781.8).save();
            new RecordModel("E07000217", "Woking", "SOUTH EAST", 99426, 49325, 50101, (double) 67.2, (double) 3, (double) 837.9).save();
            new RecordModel("E07000218", "North Warwickshire", "WEST MIDLANDS", 62468, 30816, 31652, (double) 62.7, (double) 8.9, (double) 1074.2).save();
            new RecordModel("E07000219", "Nuneaton and Bedworth", "WEST MIDLANDS", 126174, 61825, 64349, (double) 66.1, (double) 12.5, (double) 1083.8).save();
            new RecordModel("E07000220", "Rugby", "WEST MIDLANDS", 102500, 51038, 51462, (double) 65.9, (double) 7.4, (double) 891.5).save();
            new RecordModel("E07000221", "Stratford-on-Avon", "WEST MIDLANDS", 121056, 58862, 62194, (double) 56.2, (double) 3.9, (double) 821).save();
            new RecordModel("E07000222", "Warwick", "WEST MIDLANDS", 139396, 69893, 69503, (double) 55.3, (double) 2.8, (double) 875.2).save();
            new RecordModel("E07000223", "Adur", "SOUTH EAST", 63176, 30608, 32568, (double) 70, (double) 7, (double) 928.9).save();
            new RecordModel("E07000224", "Arun", "SOUTH EAST", 154414, 73869, 80545, (double) 61.9, (double) 10.6, (double) 899.1).save();
            new RecordModel("E07000225", "Chichester", "SOUTH EAST", 115527, 55310, 60217, (double) 57.6, (double) 6.2, (double) 841.6).save();
            new RecordModel("E07000226", "Crawley", "SOUTH EAST", 109883, 54652, 55231, (double) 69.6, (double) 9.7, (double) 905.9).save();
            new RecordModel("E07000227", "Horsham", "SOUTH EAST", 134158, 65171, 68987, (double) 57.6, (double) 2.8, (double) 854).save();
            new RecordModel("E07000228", "Mid Sussex", "SOUTH EAST", 144377, 70571, 73806, (double) 59.5, (double) 3.4, (double) 878.2).save();
            new RecordModel("E07000229", "Worthing", "SOUTH EAST", 106863, 51383, 55480, (double) 58.3, (double) 5.7, (double) 969.6).save();
            new RecordModel("E06000054", "Wiltshire", "SOUTH WEST", 483143, 238818, 244325, (double) 61.7, (double) 6.7, (double) 897.2).save();
            new RecordModel("E07000234", "Bromsgrove", "WEST MIDLANDS", 95485, 47208, 48277, (double) 55.3, (double) 4.7, (double) 995.6).save();
            new RecordModel("E07000235", "Malvern Hills", "WEST MIDLANDS", 75911, 37001, 38910, (double) 53.2, (double) 5.7, (double) 984.8).save();
            new RecordModel("E07000236", "Redditch", "WEST MIDLANDS", 84471, 41959, 42512, (double) 64, (double) 13.7, (double) 1022.6).save();
            new RecordModel("E07000237", "Worcester", "WEST MIDLANDS", 100842, 49778, 51064, (double) 62.6, (double) 12.6, (double) 954).save();
            new RecordModel("E07000238", "Wychavon", "WEST MIDLANDS", 119752, 58750, 61002, (double) 57.4, (double) 10.3, (double) 843).save();
            new RecordModel("E07000239", "Wyre Forest", "WEST MIDLANDS", 98960, 48813, 50147, (double) 65.8, (double) 8.1, (double) 1049.2).save();
            new RecordModel("E08000001", "Bolton", "NORTH WEST", 280439, 138964, 141475, (double) 69.8, (double) 8.4, (double) 1115.9).save();
            new RecordModel("E08000002", "Bury", "NORTH WEST", 187474, 91731, 95743, (double) 68.8, (double) 8.2, (double) 1108.2).save();
            new RecordModel("E08000003", "Manchester", "NORTH WEST", 520215, 263049, 257166, (double) 60, (double) 13.3, (double) 1271).save();
            new RecordModel("E08000004", "Oldham", "NORTH WEST", 228765, 112460, 116305, (double) 73.7, (double) 7.8, (double) 1094.6).save();
            new RecordModel("E08000005", "Rochdale", "NORTH WEST", 212962, 104664, 108298, (double) 72.1, (double) 10.4, (double) 1127.5).save();
            new RecordModel("E08000006", "Salford", "NORTH WEST", 242040, 121576, 120464, (double) 68.5, (double) 7.9, (double) 1210.2).save();
            new RecordModel("E08000007", "Stockport", "NORTH WEST", 286755, 140285, 146470, (double) 66, (double) 6.1, (double) 979.9).save();
            new RecordModel("E08000008", "Tameside", "NORTH WEST", 220771, 108477, 112294, (double) 68, (double) 11.7, (double) 1237.4).save();
            new RecordModel("E08000009", "Trafford", "NORTH WEST", 232458, 113944, 118514, (double) 64, (double) 5.4, (double) 964.2).save();
            new RecordModel("E08000010", "Wigan", "NORTH WEST", 320975, 159282, 161693, (double) 61.4, (double) 9.2, (double) 1170.6).save();
            new RecordModel("E08000011", "Knowsley", "NORTH WEST", 146407, 69595, 76812, (double) 62.4, (double) 7.9, (double) 1217).save();
            new RecordModel("E08000012", "Liverpool", "NORTH WEST", 473073, 234110, 238963, (double) 52.8, (double) 8.2, (double) 1250.1).save();
            new RecordModel("E08000013", "St. Helens", "NORTH WEST", 177188, 86981, 90207, (double) 62.7, (double) 13.2, (double) 1133.9).save();
            new RecordModel("E08000014", "Sefton", "NORTH WEST", 273531, 131225, 142306, (double) 61, (double) 8, (double) 1041.8).save();
            new RecordModel("E08000015", "Wirral", "NORTH WEST", 320914, 154687, 166227, (double) 62.3, (double) 7.5, (double) 1084.8).save();
            new RecordModel("E08000016", "Barnsley", "YORKSHIRE AND THE HUMBER", 237843, 117287, 120556, (double) 63.6, (double) 14.6, (double) 1131.2).save();
            new RecordModel("E08000017", "Doncaster", "YORKSHIRE AND THE HUMBER", 304185, 150582, 153603, (double) 65.2, (double) 13.3, (double) 1119.5).save();
            new RecordModel("E08000018", "Rotherham", "YORKSHIRE AND THE HUMBER", 260070, 127777, 132293, (double) 65.3, (double) 8.4, (double) 1092.8).save();
            new RecordModel("E08000019", "Sheffield", "YORKSHIRE AND THE HUMBER", 563749, 279545, 284204, (double) 53.5, (double) 10.7, (double) 1053).save();
            new RecordModel("E08000037", "Gateshead", "NORTH EAST", 200505, 98447, 102058, (double) 59.6, (double) 10.5, (double) 1122.3).save();
            new RecordModel("E08000021", "Newcastle upon Tyne", "NORTH EAST", 289835, 146097, 143738, (double) 51.5, (double) 10.7, (double) 1023.1).save();
            new RecordModel("E08000022", "North Tyneside", "NORTH EAST", 202744, 97956, 104788, (double) 59.3, (double) 12.6, (double) 1085.9).save();
            new RecordModel("E08000023", "South Tyneside", "NORTH EAST", 148740, 71848, 76892, (double) 59.3, (double) 10.7, (double) 1109.3).save();
            new RecordModel("E08000024", "Sunderland", "NORTH EAST", 276889, 134892, 141997, (double) 56.5, (double) 12.3, (double) 1211.6).save();
            new RecordModel("E08000025", "Birmingham", "WEST MIDLANDS", 1101360, 543580, 557780, (double) 71.2, (double) 8.7, (double) 1073.3).save();
            new RecordModel("E08000026", "Coventry", "WEST MIDLANDS", 337428, 169293, 168135, (double) 61.8, (double) 13.6, (double) 1042.3).save();
            new RecordModel("E08000027", "Dudley", "WEST MIDLANDS", 315799, 155106, 160693, (double) 66.2, (double) 10.8, (double) 989.6).save();
            new RecordModel("E08000028", "Sandwell", "WEST MIDLANDS", 316719, 156351, 160368, (double) 74.8, (double) 16, (double) 1165.8).save();
            new RecordModel("E08000029", "Solihull", "WEST MIDLANDS", 209890, 101936, 107954, (double) 60.1, (double) 5.8, (double) 879.5).save();
            new RecordModel("E08000030", "Walsall", "WEST MIDLANDS", 274173, 134626, 139547, (double) 70.7, (double) 15.7, (double) 1005.7).save();
            new RecordModel("E08000031", "Wolverhampton", "WEST MIDLANDS", 252987, 125103, 127884, (double) 68.2, (double) 13, (double) 1098.4).save();
            new RecordModel("E08000032", "Bradford", "YORKSHIRE AND THE HUMBER", 528155, 260255, 267900, (double) 75.1, (double) 8.4, (double) 1145.9).save();
            new RecordModel("E08000033", "Calderdale", "YORKSHIRE AND THE HUMBER", 207376, 101623, 105753, (double) 64.8, (double) 11.3, (double) 1059).save();
            new RecordModel("E08000034", "Kirklees", "YORKSHIRE AND THE HUMBER", 431020, 213163, 217857, (double) 67.1, (double) 8, (double) 1037.4).save();
            new RecordModel("E08000035", "Leeds", "YORKSHIRE AND THE HUMBER", 766399, 376004, 390395, (double) 59.6, (double) 12.3, (double) 1097.7).save();
            new RecordModel("E08000036", "Wakefield", "YORKSHIRE AND THE HUMBER", 331379, 162774, 168605, (double) 64.5, (double) 12.7, (double) 1077.4).save();
            new RecordModel("E09000001", "City of London", "LONDON", 8072, 4501, 3571, (double) 61.5, (double) 5.7, (double) 399.8).save();
            new RecordModel("E09000002", "Barking and Dagenham", "LONDON", 198294, 96919, 101375, (double) 85.5, (double) 8.1, (double) 1052.3).save();
            new RecordModel("E09000003", "Barnet", "LONDON", 374915, 184057, 190858, (double) 63.4, (double) 1.8, (double) 819.9).save();
            new RecordModel("E09000004", "Bexley", "LONDON", 239865, 115573, 124292, (double) 62.1, (double) 3.7, (double) 914.3).save();
            new RecordModel("E09000005", "Brent", "LONDON", 320762, 162432, 158330, (double) 70.6, (double) 4.1, (double) 882.8).save();
            new RecordModel("E09000006", "Bromley", "LONDON", 321278, 154388, 166890, (double) 62.2, (double) 3.4, (double) 824.6).save();
            new RecordModel("E09000007", "Camden", "LONDON", 234846, 116791, 118055, (double) 45.5, (double) 1.8, (double) 791.9).save();
            new RecordModel("E09000008", "Croydon", "LONDON", 376040, 182519, 193521, (double) 69.5, (double) 7.8, (double) 882).save();
            new RecordModel("E09000009", "Ealing", "LONDON", 342118, 171685, 170433, (double) 68.8, (double) 3.5, (double) 896.7).save();
            new RecordModel("E09000010", "Enfield", "LONDON", 324574, 157759, 166815, (double) 69.5, (double) 8, (double) 903.4).save();
            new RecordModel("E09000011", "Greenwich", "LONDON", 268678, 134611, 134067, (double) 71.1, (double) 5.9, (double) 1001.3).save();
            new RecordModel("E09000012", "Hackney", "LONDON", 263150, 130713, 132437, (double) 61.5, (double) 5.7, (double) 955.1).save();
            new RecordModel("E09000013", "Hammersmith and Fulham", "LONDON", 178365, 87271, 91094, (double) 52.4, (double) 6.3, (double) 943.7).save();
            new RecordModel("E09000014", "Haringey", "LONDON", 267541, 134088, 133453, (double) 63.3, (double) 6.1, (double) 839.8).save();
            new RecordModel("E09000015", "Harrow", "LONDON", 246011, 122165, 123846, (double) 70, (double) 2.5, (double) 742.6).save();
            new RecordModel("E09000016", "Havering", "LONDON", 245974, 118195, 127779, (double) 63.8, (double) 3.3, (double) 942.1).save();
            new RecordModel("E09000017", "Hillingdon", "LONDON", 292690, 146018, 146672, (double) 68.8, (double) 5.4, (double) 879.2).save();
            new RecordModel("E09000018", "Hounslow", "LONDON", 265568, 134373, 131195, (double) 73.3, (double) 5.8, (double) 959.6).save();
            new RecordModel("E09000019", "Islington", "LONDON", 221030, 110053, 110977, (double) 46, (double) 5.4, (double) 1023.5).save();
            new RecordModel("E09000020", "Kensington and Chelsea", "LONDON", 156190, 77257, 78933, (double) 49.5, (double) 2.9, (double) 701.5).save();
            new RecordModel("E09000021", "Kingston upon Thames", "LONDON", 169958, 83413, 86545, (double) 54.4, (double) 3.4, (double) 914.4).save();
            new RecordModel("E09000022", "Lambeth", "LONDON", 318216, 159544, 158672, (double) 53, (double) 4.2, (double) 995.4).save();
            new RecordModel("E09000023", "Lewisham", "LONDON", 291933, 143521, 148412, (double) 68.1, (double) 10, (double) 974.1).save();
            new RecordModel("E09000024", "Merton", "LONDON", 203515, 99963, 103552, (double) 70.9, (double) 4.3, (double) 929.2).save();
            new RecordModel("E09000025", "Newham", "LONDON", 324322, 169184, 155138, (double) 80.1, (double) 7.3, (double) 1028.4).save();
            new RecordModel("E09000026", "Redbridge", "LONDON", 293055, 145177, 147878, (double) 71.4, (double) 2.9, (double) 915.4).save();
            new RecordModel("E09000027", "Richmond upon Thames", "LONDON", 193585, 94052, 99533, (double) 69.5, (double) 2.9, (double) 794.2).save();
            new RecordModel("E09000028", "Southwark", "LONDON", 302538, 149977, 152561, (double) 57.9, (double) 6.9, (double) 957.1).save();
            new RecordModel("E09000029", "Sutton", "LONDON", 198134, 96508, 101626, (double) 64, (double) 4.9, (double) 906).save();
            new RecordModel("E09000030", "Tower Hamlets", "LONDON", 284015, 146939, 137076, (double) 58.2, (double) 4.3, (double) 1018.5).save();
            new RecordModel("E09000031", "Waltham Forest", "LONDON", 268020, 133876, 134144, (double) 75.4, (double) 6.3, (double) 907).save();
            new RecordModel("E09000032", "Wandsworth", "LONDON", 312145, 150020, 162125, (double) 56.6, (double) 5.7, (double) 976.6).save();
            new RecordModel("E09000033", "Westminster", "LONDON", 233292, 120477, 112815, (double) 48.3, (double) 2, (double) 742.6).save();
            new RecordModel("W06000001", "Isle of Anglesey", "WALES", 70169, 34498, 35671, (double) 67.6, (double) 15.5, (double) 1035.6).save();
            new RecordModel("W06000002", "Gwynedd", "WALES", 122273, 60541, 61732, (double) 56.3, (double) 10.6, (double) 913.4).save();
            new RecordModel("W06000003", "Conwy", "WALES", 116287, 56437, 59850, (double) 59.6, (double) 13.1, (double) 993).save();
            new RecordModel("W06000004", "Denbighshire", "WALES", 94791, 46737, 48054, (double) 64.6, (double) 9.5, (double) 1143.5).save();
            new RecordModel("W06000005", "Flintshire", "WALES", 153804, 75753, 78051, (double) 60.1, (double) 5.8, (double) 1055.7).save();
            new RecordModel("W06000006", "Wrexham", "WALES", 136714, 68149, 68565, (double) 64.2, (double) 12.8, (double) 1052.8).save();
            new RecordModel("W06000023", "Powys", "WALES", 132675, 65641, 67034, (double) 61.5, (double) 6.3, (double) 916.8).save();
            new RecordModel("W06000008", "Ceredigion", "WALES", 75425, 37868, 37557, (double) 45.6, (double) 5.6, (double) 907.8).save();
            new RecordModel("W06000009", "Pembrokeshire", "WALES", 123666, 60679, 62987, (double) 57.2, (double) 10.5, (double) 979.2).save();
            new RecordModel("W06000010", "Carmarthenshire", "WALES", 184898, 90484, 94414, (double) 57.9, (double) 6.6, (double) 1054.5).save();
            new RecordModel("W06000011", "Swansea", "WALES", 241297, 120029, 121268, (double) 53.6, (double) 7.1, (double) 1079.3).save();
            new RecordModel("W06000012", "Neath Port Talbot", "WALES", 140490, 68903, 71587, (double) 59.2, (double) 12.7, (double) 1132.5).save();
            new RecordModel("W06000013", "Bridgend", "WALES", 141214, 69719, 71495, (double) 60.2, (double) 12.9, (double) 1117.3).save();
            new RecordModel("W06000014", "The Vale of Glamorgan", "WALES", 127685, 62070, 65615, (double) 60.8, (double) 10.5, (double) 993.7).save();
            new RecordModel("W06000015", "Cardiff", "WALES", 354294, 174278, 180016, (double) 56, (double) 11, (double) 1058.8).save();
            new RecordModel("W06000016", "Rhondda Cynon Taf", "WALES", 236888, 116076, 120812, (double) 61.4, (double) 13.2, (double) 1244.8).save();
            new RecordModel("W06000024", "Merthyr Tydfil", "WALES", 59065, 28978, 30087, (double) 58.6, (double) 7.1, (double) 1165.1).save();
            new RecordModel("W06000018", "Caerphilly", "WALES", 179941, 88097, 91844, (double) 57.7, (double) 8.3, (double) 1159.9).save();
            new RecordModel("W06000019", "Blaenau Gwent", "WALES", 69674, 34274, 35400, (double) 60.4, (double) 13.8, (double) 1272).save();
            new RecordModel("W06000020", "Torfaen", "WALES", 91609, 44673, 46936, (double) 60.8, (double) 10.8, (double) 1078.8).save();
            new RecordModel("W06000021", "Monmouthshire", "WALES", 92336, 45392, 46944, (double) 55.2, (double) 3.4, (double) 890.3).save();
            new RecordModel("W06000022", "Newport", "WALES", 146841, 72039, 74802, (double) 67.6, (double) 7.5, (double) 1095.1).save();

        } catch(Exception e){
            Log.d("EXCEPTION", e.getLocalizedMessage());
        }
    }


}
