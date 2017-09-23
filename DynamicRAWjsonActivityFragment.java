package tecnest.manage.mytaskproject.AllFragments;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import tecnest.manage.mytaskproject.Pojo.Testpojo;
import tecnest.manage.mytaskproject.R;

import static tecnest.manage.mytaskproject.ConstantsJSON.RawJson.RAW_JSON;

/**
 * A placeholder fragment containing a simple view.
 */
public class DynamicRAWjsonActivityFragment extends Fragment implements View.OnClickListener {


    TextView tvData;
    List<Testpojo> td;
    Spinner spin;
    RadioButton rd;
    Testpojo tdShow;
    LinearLayout ll;
    TextView dtCreate;


    public DynamicRAWjsonActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_dynamic_rawjson, container, false);

        tvData = v.findViewById(R.id.textView);
        v.findViewById(R.id.createTextview).setOnClickListener(this);
        v.findViewById(R.id.radioButton).setOnClickListener(this);
        v.findViewById(R.id.button).setOnClickListener(this);
        loadRawJson();


        return v;
    }

    public void loadRawJson() {

        Resources rsFile = getResources();
        InputStream inputStreamFromjason = rsFile.openRawResource(R.raw.jsoncreated);
        Scanner sc = new Scanner(inputStreamFromjason);

        StringBuilder sBuilder = new StringBuilder();
        while (sc.hasNext()) {
            sBuilder.append(sc.nextLine());
        }

        parseFeed(sBuilder.toString());


    }

    public void parseFeed(String content) {

        Testpojo list;
        JSONObject obj1;
        StringBuilder sBuilder = new StringBuilder();
        try {

            JSONArray ar = new JSONArray(content);
            List<Testpojo> movieList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                sBuilder.append("id : ").append(obj.getString("id")).append("\n");
                sBuilder.append("Name : ").append(obj.getString("Name")).append("\n");

                obj1 = obj.getJSONObject("Fields");
                sBuilder.append("Attribute Type : ").append(obj1.getString("Attribute Type")).append("\n");
                sBuilder.append("TextBox Size : ").append(obj1.getString("Text Box Size")).append("\n");
                sBuilder.append("Captionfont : ").append(obj1.getString("Caption Font")).append("\n");

                sBuilder.append("\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
        tvData.setText(sBuilder.toString());
        tvData.setMovementMethod(new ScrollingMovementMethod());


    }

    public List<Testpojo> parseJson(String content) {

        Testpojo list;

        try {

            JSONArray ar = new JSONArray(content);
            List<Testpojo> movieList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                list = new Testpojo();

                JSONObject obj = ar.getJSONObject(i);
                JSONObject ohb = obj.getJSONObject("Field");
                list.setTextbox(obj.getString("Attribute Type"));
                list.setTextsize(obj.getString("Text Box Size"));
                list.setText(obj.getString("Text"));
                movieList.add(list);

            }
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public void onClick(View view) {
        Testpojo list;
        RadioButton rb;
        String iNamed;
        StringBuilder sBuilder = new StringBuilder();
        final LinearLayout lm = (LinearLayout) getView().findViewById(R.id.linearLayout1);


        int id = view.getId();
        switch (id) {
            case R.id.createTextview:

                EditText product = new EditText(getContext());
                lm.setOrientation(LinearLayout.VERTICAL);
                try {
                    JSONArray ja = new JSONArray(RAW_JSON);

                    JSONObject emp = ja.getJSONObject(0);

                    JSONObject oj = emp.getJSONObject("Fields");
                    String empname = oj.getString("Attribute Type");
                    String textviewwidth = oj.getString("Text Box Size");
                    String textfont = oj.getString("Caption Font");
                    String str = empname;

                    product.setText(str);
                    product.setWidth(Integer.parseInt(textviewwidth));
                    product.setGravity(Gravity.LEFT);
                    lm.addView(product);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Clicke", Toast.LENGTH_SHORT).show();


                break;
            case R.id.radioButton:
                RadioButton productrd = new RadioButton(getContext());
                lm.setOrientation(LinearLayout.HORIZONTAL);
                try {
                    JSONArray ja = new JSONArray(RAW_JSON);
                    JSONObject emp = ja.getJSONObject(2);

                    JSONObject oj = emp.getJSONObject("Fields");
                    String empname = oj.getString("Attribute Type");
                    String textviewwidth = oj.getString("Text Box Size");
                    String textfont = oj.getString("Caption Font");
                    String str = empname;

                    productrd.setText(str);
                    productrd.setGravity(Gravity.LEFT);
                    lm.addView(productrd);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Clicke", Toast.LENGTH_SHORT).show();

                break;

            case R.id.button:
                Button productbt = new Button(getContext());
                lm.setOrientation(LinearLayout.HORIZONTAL);
                try {
                    JSONArray ja = new JSONArray(RAW_JSON);

                    JSONObject emp = ja.getJSONObject(3);

                    JSONObject oj = emp.getJSONObject("Fields");
                    String empname = oj.getString("Attribute Type");
                    String textviewwidth = oj.getString("Text Box Size");
                    String textfont = oj.getString("Caption Font");
                    String str = empname;
                    productbt.setText(str);
                    productbt.setGravity(Gravity.CENTER);
                    lm.addView(productbt);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "Clicke", Toast.LENGTH_SHORT).show();

                break;


        }


    }
}
