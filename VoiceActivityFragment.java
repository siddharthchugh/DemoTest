package tecnest.manage.mytaskproject.AllFragments;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import tecnest.manage.mytaskproject.*;
import tecnest.manage.mytaskproject.R;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * A placeholder fragment containing a simple view.
 */
public class VoiceActivityFragment extends Fragment implements View.OnClickListener {

    Button buttonStart, buttonStop, buttonPlayLastRecordAudio,
            buttonStopPlayingRecording;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder;
    Random random;
    String RandomAudioFileName = "My File for audio";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer;

    public VoiceActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(tecnest.manage.mytaskproject.R.layout.fragment_voice, container, false);
        v.findViewById(R.id.button).setOnClickListener(this);
        v.findViewById(R.id.button2).setOnClickListener(this);
        v.findViewById(R.id.button3).setOnClickListener(this);
        v.findViewById(R.id.button4).setOnClickListener(this);


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE
        ) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }
        TelephonyManager telephonyManager =
                (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);

        PhoneStateListener callStateListener = new PhoneStateListener() {
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    Toast.makeText(getContext().getApplicationContext(), "Phone Is Riging",
                            Toast.LENGTH_LONG).show();
                }
                if (state == TelephonyManager.CALL_STATE_OFFHOOK) {

                    if (checkPermission()) {

                        AudioSavePathInDevice =
                                Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                        CreateRandomAudioFileName(5) + "AudioRecording.3gp";

                        MediaRecorderReady();

                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                        } catch (IllegalStateException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        buttonStart.setEnabled(false);
                        buttonStop.setEnabled(true);

                        Toast.makeText(getActivity(), "Recording started",
                                Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getContext().getApplicationContext(), "Phone is Currently in A call",
                            Toast.LENGTH_LONG).show();
                }

                if (state == TelephonyManager.CALL_STATE_IDLE) {

                    Toast.makeText(getContext().getApplicationContext(), "phone is neither ringing nor in a call",
                            Toast.LENGTH_LONG).show();
                }
            }
        };
        telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);



        buttonStart = (Button) v.findViewById(R.id.button);
        buttonStop = (Button) v.findViewById(R.id.button2);
        buttonPlayLastRecordAudio = (Button) v.findViewById(R.id.button3);
        buttonStopPlayingRecording = (Button) v.findViewById(R.id.button4);

        buttonStop.setEnabled(false);
        buttonPlayLastRecordAudio.setEnabled(false);
        buttonStopPlayingRecording.setEnabled(false);

        random = new Random();


        return v;


    }


    public void MediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        int i = 0;
        while (i < string) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new
                String[]{RECORD_AUDIO, READ_PHONE_STATE, CALL_PHONE}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {

                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    boolean ReadyPhone = grantResults[2] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean Call_Phone = grantResults[3] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (ReadyPhone && RecordPermission && Call_Phone) {
                        Toast.makeText(getActivity(), "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result1 = ContextCompat.checkSelfPermission(getContext(),
                RECORD_AUDIO);
        int resultPhonestate = ContextCompat.checkSelfPermission(getContext(),
                READ_PHONE_STATE);
        int resultCall = ContextCompat.checkSelfPermission(getContext(),
                CALL_PHONE);

        return result1 == PackageManager.PERMISSION_GRANTED && resultPhonestate == PackageManager.PERMISSION_GRANTED
                && resultCall == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {


            case R.id.button:
                if (checkPermission()) {

                    AudioSavePathInDevice =
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                    CreateRandomAudioFileName(5) + "AudioRecording.3gp";

                    MediaRecorderReady();

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    buttonStart.setEnabled(false);
                    buttonStop.setEnabled(true);

                    Toast.makeText(getActivity(), "Recording started",
                            Toast.LENGTH_LONG).show();
                } else {
                    requestPermission();
                }


                break;
            case R.id.button2:
                mediaRecorder.stop();
                buttonStop.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);
                buttonStart.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);

                Toast.makeText(getActivity(), "Recording Completed",
                        Toast.LENGTH_LONG).show();

                break;
            case R.id.button3:
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(false);
                buttonStopPlayingRecording.setEnabled(true);

                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(getActivity(), "Recording Playing",
                        Toast.LENGTH_LONG).show();

                break;
            case R.id.button4:
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    MediaRecorderReady();
                }


                break;


        }


    }
}


