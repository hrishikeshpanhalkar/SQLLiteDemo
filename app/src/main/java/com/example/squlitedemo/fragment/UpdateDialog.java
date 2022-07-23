package com.example.squlitedemo.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.squlitedemo.R;
import com.example.squlitedemo.activities.MyPostsActivity;
import com.example.squlitedemo.adapter.MyPostAdapter;
import com.example.squlitedemo.database.DBHelper;
import com.google.android.material.textfield.TextInputLayout;

public class UpdateDialog extends DialogFragment{
    private TextInputLayout post;
    private Button updateBtn;
    private ImageButton closeBtn;
    private DBHelper dbHelper;
    private int postNo, position;
    private String postValue;

    public UpdateDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.update_dialog, container, false);
        getDialog().setCanceledOnTouchOutside(false);

        dbHelper = new DBHelper(getContext());
        postNo = Integer.parseInt(getArguments().getString("postNo"));
        postValue = getArguments().getString("postValue");
        position = Integer.parseInt(getArguments().getString("position"));
        post = rootView.findViewById(R.id.post);
        post.getEditText().setText(postValue);
        updateBtn = rootView.findViewById(R.id.updateBtn);
        closeBtn = rootView.findViewById(R.id.closeBtn);


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                String postValue = post.getEditText().getText().toString();
                if(postValue.isEmpty()){
                    Toast.makeText(getContext(), "Fill All Details!!", Toast.LENGTH_SHORT).show();
                }else {
                    boolean isSuccessful = dbHelper.updatePostData(postNo, postValue);
                    if(isSuccessful){
                        MyPostsActivity.arrayList.get(position).setPost(postValue);
                        MyPostsActivity.myPostAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Update Successfully!!", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }else {
                        Toast.makeText(getContext(), "Unable to Update!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return rootView;
    }
}
