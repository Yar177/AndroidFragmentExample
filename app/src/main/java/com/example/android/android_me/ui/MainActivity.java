package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener{

    private int headIndex;
    private int bodyIndex;
    private int legIndex;
    private boolean mTwoPain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.android_me_linear_layout) != null){
            mTwoPain =true;

            Button nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setVisibility(View.GONE);

            GridView gridView = (GridView) findViewById(R.id.image_grid_view);
            gridView.setNumColumns(2);



            if (savedInstanceState == null){

                BodyPartFragment headPartFragment = new BodyPartFragment();
                headPartFragment.setImageIds(AndroidImageAssets.getHeads());
                int headIndex = getIntent().getIntExtra("headIndex", 0);
                headPartFragment.setListIndex(headIndex);

                BodyPartFragment bodyPartFragment = new BodyPartFragment();
                bodyPartFragment.setImageIds(AndroidImageAssets.getBodies());
                int bodyIndex = getIntent().getIntExtra("bodyIndex", 0);
                bodyPartFragment.setListIndex(bodyIndex);

                BodyPartFragment legPartFragment = new BodyPartFragment();
                legPartFragment.setImageIds(AndroidImageAssets.getLegs());
                int legIndex = getIntent().getIntExtra("legIndex", 0);
                legPartFragment.setListIndex(legIndex);

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()

                        .add(R.id.head_container, headPartFragment)
                        .add(R.id.body_container, bodyPartFragment)
                        .add(R.id.leg_container, legPartFragment)
                        .commit();

            }

        }else {
            mTwoPain = false;
        }

    }

    @Override
    public void onImageSelected(int position) {
      //  Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_LONG).show();

        int bodyPartNumber = position/12;

        int listIndex = position - 12*bodyPartNumber;

        switch (bodyPartNumber){
            case 0: headIndex = listIndex;
                break;
            case 1: bodyIndex = listIndex;
                  break;
            case  2: legIndex = listIndex;
                 break;
            default: break;
        }

        Bundle bundle = new Bundle();
        bundle.putInt("headIndex", headIndex);
        bundle.putInt("bodyIndex", bodyIndex);
        bundle.putInt("legIndex", legIndex);

        final Intent intent = new Intent(this, AndroidMeActivity.class);
        intent.putExtras(bundle);

        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });



    }
}
