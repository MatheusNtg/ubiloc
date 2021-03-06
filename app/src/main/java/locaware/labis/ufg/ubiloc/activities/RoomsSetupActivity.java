package locaware.labis.ufg.ubiloc.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import locaware.labis.ufg.ubiloc.R;
import locaware.labis.ufg.ubiloc.classes.House;
import locaware.labis.ufg.ubiloc.classes.Room;
import locaware.labis.ufg.ubiloc.classes.Utils;
import locaware.labis.ufg.ubiloc.innerDatabase.ActivityBuffer;
import locaware.labis.ufg.ubiloc.innerDatabase.HouseBuffer;
import locaware.labis.ufg.ubiloc.innerDatabase.UsernameBuffer;

public class RoomsSetupActivity extends AppCompatActivity {

    //Constants
    private final String TAG = "Debug";

    //Activity elements
    private EditText mRoomNameEditText;
    private TextView mRoomNumberTextView;
    private Button mConfirmButton;

    //Vars
    private int roomNumber = 1;
    private int roomNumberLimit = ActivityBuffer.getRoomsToCreate();
    private House workingHouse = HouseBuffer.getHouseBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_setup);
        //Context
        final Context context = this;

        //Setting up the activity elements
        mRoomNameEditText   = findViewById(R.id.roomNameEditText);
        mConfirmButton      = findViewById(R.id.confirmButtonRoom);
        mRoomNumberTextView = findViewById(R.id.roomNumber);

        mRoomNumberTextView.setText("Quarto " + roomNumber);

        //Listener
        //Confirm button
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check if all fields are filled
                if(!Utils.isTextFieldEmpty(mRoomNameEditText)){
                    // Take the info from edit text and put them on working house
                    String roomName = mRoomNameEditText.getText().toString();
                    Room actualRoom = new Room(null,roomName);
                    workingHouse.addRoomToArray(actualRoom);

                    roomNumber++;
                    //If all rooms have been registered start the collect activity
                    if(roomNumber > roomNumberLimit){
                        Intent intent = new Intent(context,collectActivity.class);
                        startActivity(intent);
                    }else{
                        mRoomNumberTextView.setText("Quarto " + roomNumber);
                        Utils.cleanEditText(mRoomNameEditText);
                    }
                }else{
                    Toast.makeText(context,"Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
