package id.adidharmawati.tugasreadwritefile;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ReadAndWrite extends AppCompatActivity {
    Button buttonAdd, buttonOpen, buttonSave;
    EditText editContent;
    EditText editTitle;
    File path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_and_write);
        buttonAdd=(Button) findViewById(R.id.add_file);
        buttonOpen=(Button) findViewById(R.id.open_file);
        buttonSave=(Button) findViewById(R.id.save_file);
        editTitle=(EditText) findViewById(R.id.edit_title) ;
        editContent=(EditText) findViewById(R.id.edit_content) ;
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addFile();
            }
        });
        buttonOpen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openFile();
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                saveFile();
            }
        });
        path = getFilesDir();
    }
    public void addFile() {

        editTitle.setText("");
        editContent.setText("");

        Toast.makeText(this, "Masukkan Judul dan Isi File", Toast.LENGTH_SHORT).show();
    }
    public void saveFile() {
        if (editTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Mohon Lengkapi Kolom Judul File", Toast.LENGTH_SHORT).show();
        } else {
            String title = editTitle.getText().toString();
            String text = editContent.getText().toString();
            FileHelper.writeToFile(title, text, this);
            Toast.makeText(this, editTitle.getText().toString()+ "Berhasil Disimpan", Toast.LENGTH_SHORT).show();
        }
    }
    private void loadData(String title) {
        String text = FileHelper.readFromFile(this, title);
        editTitle.setText(title);
        editContent.setText(text);
        Toast.makeText(this, "Menampilkan file " + title, Toast.LENGTH_SHORT).show();
    }

    public void openFile() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()) {
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih File");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}