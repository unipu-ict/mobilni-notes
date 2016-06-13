package hr.unipu.mobilne.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UredjivanjeActivity extends AppCompatActivity {

    boolean nova = false;
    int odabrana;
    private Baza db;

    EditText naslovEdit;
    EditText tekstEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uredjivanje);

        //dohvat elemenata sučelja

        naslovEdit = (EditText) findViewById(R.id.naslov_biljeske);
        tekstEdit = (EditText) findViewById(R.id.tekst_biljeske);

        if (getIntent().getBooleanExtra("nova", false))
            nova = true;//stvaramo novu bilješku, ne uređujemo postojeću
        db = new Baza(this);
        odabrana = getIntent().getIntExtra("odabrana", 0);
        if (odabrana != 0) {
            //ne stvaramo novu bilješku nego čitamo postojeću
            Biljeska biljeska = db.nadjiBiljesku(odabrana);
            naslovEdit.setText(biljeska.getNaslov());
            tekstEdit.setText(biljeska.getTekst());
        }

    }

    public void spremi_izmjene(View view) {
        if (nova) {
            String naslov = naslovEdit.getText().toString();
            if (naslov.isEmpty()) {
                Toast.makeText(UredjivanjeActivity.this, "Naslov ne smije biti prazan", Toast.LENGTH_SHORT).show();
                return;
            }
            String tekst = tekstEdit.getText().toString();
            if (tekst.isEmpty()) {
                Toast.makeText(UredjivanjeActivity.this, "Tekst ne smije biti prazan", Toast.LENGTH_SHORT).show();
                return;
            }
            Biljeska biljeska = new Biljeska(naslov, tekst);
            if (db.novaBiljeska(biljeska)) {
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(UredjivanjeActivity.this, "Bilješka spremljena", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(UredjivanjeActivity.this, "Greška prilikom spremanja", Toast.LENGTH_SHORT).show();
        }
    }
}
