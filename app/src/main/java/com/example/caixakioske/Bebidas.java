package com.example.caixakioske;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.caixakioske.Adaptadores.FirebaseDAO;
import com.example.caixakioske.Adaptadores.ListenerGavetaProduto;
import com.example.caixakioske.Adaptadores.NumberPickerDialog;
import com.example.caixakioske.Modelos.GavetaProduto;
import com.example.caixakioske.Modelos.Produto;
import com.example.caixakioske.TelasCadastros.EditarProduto;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class Bebidas extends AppCompatActivity implements NumberPicker.OnValueChangeListener{


    RecyclerView rvBebidas;
    FirebaseRecyclerAdapter adapter;
    Intent intent;
    int quantidade;
    ArrayList<Produto> produtos = new ArrayList<>();
    ArrayList<Integer> quantidades = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);

        intent = getIntent();

        // Cria Querry Pegando Todos os Produtos de Tipo "Bebidas" do DB
        FirebaseDAO dao = new FirebaseDAO();
        Query query = dao.read("produtos", "tipo", "bebidas");

        // Constroi a Configuracao do AdaptadorProdutosRealm
        FirebaseRecyclerOptions<Produto> options =
                new FirebaseRecyclerOptions.Builder<Produto>()
                        .setQuery(query, Produto.class)
                        .build();

        // Cria o Objeto Adaptador Com Gaveta Customizada GavetaProduto
        adapter = new FirebaseRecyclerAdapter<Produto, GavetaProduto>(options) {
            @NonNull
            @Override
            public GavetaProduto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.gaveta_produtos, parent, false);

                return new GavetaProduto(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull GavetaProduto holder, int position, @NonNull Produto model) {

                holder.itemView.setBackgroundColor(255000000);
                holder.tvNomeProduto.setText(model.getNome());
                holder.tvPrecoProduto.setText(String.valueOf(model.getPreco()));

            }
        };

        rvBebidas = findViewById(R.id.rvBebidas);
        rvBebidas.setHasFixedSize(true);

        // add a listener to click and long click
        rvBebidas.addOnItemTouchListener(new ListenerGavetaProduto(this, rvBebidas, new ListenerGavetaProduto.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Produto produto = getItemPosition(position);

                if(produto != null) {
                    if(intent != null && intent.getStringExtra("caminho") != null) {
                        String caminho = intent.getStringExtra("caminho");
                        assert caminho != null;
                        if(caminho.equals("produtos")) {
                            //Toast.makeText(Bebidas.this, "Vindo de produtos", Toast.LENGTH_SHORT).show();
                            Intent editarProduto = new Intent(Bebidas.this, EditarProduto.class);
                            editarProduto.putExtra("produto", produto);
                            editarProduto.putExtra("caminho", "bebidas");
                            startActivity(editarProduto);

                        } else if (caminho.equals("categorias")){
                            Toast.makeText(Bebidas.this, "Vindo de categorias", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onLongClick(View view, int position) {

                final Produto produto = getItemPosition(position);

                if(produto != null) {

                    if(intent != null && intent.getStringExtra("caminho") != null) {
                        String caminho = intent.getStringExtra("caminho");
                        assert caminho != null;
                        if(caminho.equals("produtos")) {
                            //Toast.makeText(Bebidas.this, "Vindo de produtos", Toast.LENGTH_SHORT).show();
                            Intent editarProduto = new Intent(Bebidas.this, EditarProduto.class);
                            editarProduto.putExtra("produto", produto);
                            editarProduto.putExtra("caminho", "bebidas");
                            startActivity(editarProduto);

                        } else if (caminho.equals("categorias")){

                            NumberPicker numberPicker = new NumberPicker(Bebidas.this);
                            numberPicker.setMinValue(0);
                            numberPicker.setMaxValue(20);

                            NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() {
                                @Override
                                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                    quantidade = newVal;
                                }
                            };

                            numberPicker.setOnValueChangedListener(onValueChangeListener);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(Bebidas.this).setView(numberPicker);
                            builder.setTitle("Quantidade");
                            builder.setPositiveButton(R.string.Adicionar, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    produtos.add(produto);
                                    quantidades.add(quantidade);
                                }
                            });

                            builder.setNegativeButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.show();
                        }
                    }
                }
            }
        }));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvBebidas.setLayoutManager(layoutManager);

        rvBebidas.setAdapter(adapter);
        adapter.startListening();



    }

    private void setQuantidade(int which) {

    }


    public Produto getItemPosition(int position) {
        if(adapter != null) {
            return (Produto) adapter.getItem(position);
        } else {
            return null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {

        Intent intentAnterior = getIntent();

        if(intentAnterior != null) {
            String caminho = intentAnterior.getStringExtra("caminho");

            if(caminho != null) {
                if(caminho.equals("produtos")) {
                    Intent back = new Intent(this, Produtos.class);
                    startActivity(back);
                } else if (caminho.equals("categorias")) {
                    Intent back = new Intent(this, Categorias.class);
                    startActivity(back);
                }
            }
        } else {
            Intent back = new Intent(this, Login.class);
            startActivity(back);
        }
    }

    private String getContexto() {
        return getIntent().getStringExtra("caminho");
    }

    private void retornaActivityAnterior() {
        switch (getContexto()) {
            case "bebidas": {
                Intent intent = new Intent(this, Bebidas.class);
                intent.putExtra("caminho", "produtos");
                startActivity(intent);
                break;
            }
            case "comidas": {
                Intent intent = new Intent(this, Comidas.class);
                intent.putExtra("caminho", "produtos");
                startActivity(intent);
                break;
            }
            case "outros": {
                Intent intent = new Intent(this, Kioske.class);
                intent.putExtra("caminho", "produtos");
                startActivity(intent);
                break;
            }
            case "produtos": {
                Intent intent = new Intent(this, Produtos.class);
                intent.putExtra("caminho", "produtos");
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Toast.makeText(this,
                "selected number " + picker.getValue(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_voltar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_voltar) {
            retornaActivityAnterior();
        }
        return super.onOptionsItemSelected(item);
    }
}
