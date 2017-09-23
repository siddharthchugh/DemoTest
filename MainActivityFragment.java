package tecnest.manage.mytaskproject.AllFragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import tecnest.manage.mytaskproject.AllActivities.CameraActivity;
import tecnest.manage.mytaskproject.AllActivities.DynamicRAWjsonActivity;
import tecnest.manage.mytaskproject.AllActivities.VoiceActivity;
import tecnest.manage.mytaskproject.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener{




    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_main, container, false);

        v.findViewById(R.id.buttonCamera).setOnClickListener(this);
        v.findViewById(R.id.buttonCallRecorder).setOnClickListener(this);
        v.findViewById(R.id.buttonJson).setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.buttonCamera:

                startActivity(new Intent(getActivity(),CameraActivity.class));

                Toast.makeText(getContext(), "Camera", Toast.LENGTH_SHORT).show();
                break;


            case R.id.buttonCallRecorder:
                startActivity(new Intent(getActivity(),VoiceActivity.class));

                Toast.makeText(getContext(), "Call Recorder", Toast.LENGTH_SHORT).show();

                break;
            case R.id.buttonJson:
                startActivity(new Intent(getActivity(),DynamicRAWjsonActivity.class));

                Toast.makeText(getContext(), "Dynamic", Toast.LENGTH_SHORT).show();

                break;



        }



    }
}
