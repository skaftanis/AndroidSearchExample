package com.showmeyourcode.kaftanis.searchexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> listLabels;
    ArrayList<String> equivalentList; //this arraylist follows the changes in the listView
    ListView listView;
    EditText editText;

    ArrayAdapter adapter;

    String selectedName;


    String[] items;
    ArrayList<String> listItems;

    int length = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listLabels = new ArrayList<String>();

        equivalentList = new ArrayList<String>();

        listView = (ListView) findViewById(R.id.listview);

        editText = (EditText) findViewById(R.id.txtsearch);


        listLabels.add("Σπύρος");
        listLabels.add("Γιάννης");
        listLabels.add("Γιώργος");
        listLabels.add("τάκης");
        listLabels.add("μάκης");
        listLabels.add("σάκης");


        items = new String[listLabels.size()];
        initList();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                listView.setEnabled(false);


                selectedName = equivalentList.get(position);
                Toast.makeText(getApplicationContext(), "You clicked: " + selectedName, Toast.LENGTH_SHORT).show();



                listView.setEnabled(true);

            }

        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                length = s.toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    initList();
                } else {
                    searchItem(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() < length) {
                    initList();

                    for (int i=0; i<items.length; i++) {
                        if (!items[i].toLowerCase().contains(s.toString().toLowerCase())  &&  !removeTones(items[i]).contains(removeTones(s.toString()))  &&  !removeTones(items[i]).toLowerCase().contains(removeTones(s.toString()).toLowerCase())  ) {
                            listItems.remove(items[i]);
                            equivalentList.remove(items[i]);
                            //listPicture.remove(pictures[i]);


                        }
                    }

                }

            }
        });



    }

    public void initList() {
        //items = new String[]{"Canada", "China", "Japan", "USA"};
        items=listLabels.toArray(items);


        listItems=new ArrayList<>(Arrays.asList(items));

        equivalentList=listItems;
        adapter=new ArrayAdapter<String>(this,R.layout.listitem, R.id.txtitem, listItems);
        // adapter = new SearchListAdapter<String>(this,listItems, listPicture);
        listView.setAdapter(adapter);

    }

    public void searchItem (String txtToSearch) {
        for (int i=0; i<items.length;i++) {
            if (!items[i].toLowerCase().contains(txtToSearch.toString().toLowerCase())  &&  !removeTones(items[i]).contains(removeTones(txtToSearch.toString()))  &&  !removeTones(items[i]).toLowerCase().contains(removeTones(txtToSearch.toString()).toLowerCase())  ) {
                listItems.remove(items[i]);
                equivalentList.remove(items[i]);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private  String removeTones( String input) {

        int[][] cross = new int[7][2];

        cross[0][0] = 945;
        cross[0][1] = 940;

        cross[1][0] = 949;
        cross[1][1] = 941;

        cross[2][0] = 951;
        cross[2][1] = 942;

        cross[3][0] = 953;
        cross[3][1] = 943;

        cross[4][0] = 959;
        cross[4][1] = 972;

        cross[5][0] = 965;
        cross[5][1] = 973;

        cross[6][0]= 969;
        cross[6][1] = 974;

        String test = input;

        System.out.println("StartString: " + test);
        int flag=0;


        String finalString="";
        for (int i=0; i<test.length(); i++) {
            flag=0;
            //System.out.print(test.charAt(i));
            int ascii_test = (int) test.charAt(i);
            for (int j=0; j<7; j++) {
                if ( ascii_test == cross[j][1]) {
                    ascii_test = cross[j][0];
                    finalString+=Character.toString( (char) ascii_test ) ;
                    flag=1;
                }
            }
            if (flag == 0) {
                finalString+=Character.toString( (char) ascii_test ) ;
            }

        }

        return finalString;


    }


}
