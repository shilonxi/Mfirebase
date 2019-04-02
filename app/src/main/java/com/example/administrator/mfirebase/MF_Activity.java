package com.example.administrator.mfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MF_Activity extends AppCompatActivity
{
    private EditText input;
    private TextView output;
    private Button inbt;
    private Button outbt;
    //建立变量
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mf_layout);
        firebaseFirestore=FirebaseFirestore.getInstance();
        //初始化Firestore的实例
        input=findViewById(R.id.input);
        output=findViewById(R.id.output);
        inbt=findViewById(R.id.inbt);
        outbt=findViewById(R.id.outbt);
        //获取实例
        inbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string=input.getText().toString();
                Map<String,Object>car=new HashMap<>();
                car.put("time","Now");
                car.put("address","Here");
                car.put("number",string);
                //得到要添加的数据
                firebaseFirestore.collection("numbers")
                                    .add(car)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(MF_Activity.this,"DocumentSnapshot added with ID: "+documentReference.getId(),Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MF_Activity.this,"Error adding document",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    //创建集合和文档
            }
        });
        //监听
        outbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("numbers")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()){
                                                output.setText(null);
                                                for(QueryDocumentSnapshot document:task.getResult())
                                                {
                                                    output.append(document.getId()+"=>"+document.getData());
                                                    output.append("\n");
                                                }
                                            }else
                                                output.setText("wrong");
                                        }
                                    });
                                    //读取数据并显示
                }
        });
        //监听
    }
}
