package hr.unipu.mobilne.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
// import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Baza db;
    List<Biljeska> sveBiljeske;
    ListView listaBiljeski;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UredjivanjeActivity.class);
                intent.putExtra("nova", true);
                startActivity(intent);

            }
        });

        db = new Baza(this);
        listaBiljeski = (ListView) findViewById(R.id.listabiljeski);
        napuniListu();


        listaBiljeski.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, UredjivanjeActivity.class);
                intent.putExtra("nova", false);
                //proslijedi drugoj aktivnosti info koja je bilješka odabrana
                int odabrana = sveBiljeske.get(position).getId();
                intent.putExtra("odabrana", odabrana);
                startActivity(intent);
            }
        });

    }

    private void napuniListu() {
        //spremanje bilješki u listu
        sveBiljeske = db.listaBiljeski();
        //povezivanje liste u memoriji s listom na ekranu
        ArrayAdapter<Biljeska> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sveBiljeske);
        listaBiljeski.setAdapter(adapter);
    }

    private void izbrisiSveBiljeske() {
        db.izbrisiSveBiljeske();

        Toast.makeText(MainActivity.this, "Sve bilješke izbrisane", Toast.LENGTH_SHORT).show();

        napuniListu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_settings:
                izbrisiSveBiljeske();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        napuniListu();
    }
}
