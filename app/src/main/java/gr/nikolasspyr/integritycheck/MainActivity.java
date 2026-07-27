package gr.nikolasspyr.integritycheck;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import gr.nikolasspyr.integritycheck.dialogs.AboutDialog;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btn;
    private ImageView deviceIntegrityIcon;
    private ImageView basicIntegrityIcon;
    private ImageView strongIntegrityIcon;
    private ImageView virtualIntegrityIcon;

    private Group virtualIntegrity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            ViewCompat.setOnApplyWindowInsetsListener(
                    findViewById(android.R.id.content),
                    (view, insets) -> {
                        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                        view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                        return insets;
                    }
            );
        }

        btn = findViewById(R.id.check_btn);

        basicIntegrityIcon = findViewById(R.id.basic_integrity_icon);
        deviceIntegrityIcon = findViewById(R.id.device_integrity_icon);
        strongIntegrityIcon = findViewById(R.id.strong_integrity_icon);
        virtualIntegrityIcon = findViewById(R.id.virtual_integrity_icon);

        virtualIntegrity = findViewById(R.id.virtual_integrity);

        btn.setOnClickListener(view -> showStrongestResult());
    }

    private void showStrongestResult() {
        Integer[] integrityState = {0, 0, 1, -1};
        setIcon(basicIntegrityIcon, integrityState[0]);
        setIcon(deviceIntegrityIcon, integrityState[1]);
        setIcon(strongIntegrityIcon, integrityState[2]);
        setIcon(virtualIntegrityIcon, integrityState[3]);
        virtualIntegrity.setVisibility(View.GONE);
    }

    private void setIcon(ImageView img, int state) {
        if (state == -1) {
            img.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_unknown));
            img.setContentDescription(getString(R.string.status_unknown));
        } else if (state == 0) {
            img.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_fail));
            img.setContentDescription(getString(R.string.status_fail));
        } else {
            img.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_pass));
            img.setContentDescription(getString(R.string.status_pass));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about) {
            new AboutDialog(MainActivity.this).show();
            return true;
        } else if (id == R.id.documentation) {
            Utils.openLink(getString(R.string.docs_link), this);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
