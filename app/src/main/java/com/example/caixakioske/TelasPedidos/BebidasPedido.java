package com.example.caixakioske.TelasPedidos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.caixakioske.Adaptadores.FirebaseDAO;
import com.example.caixakioske.Adaptadores.ListenerGavetas;
import com.example.caixakioske.Bebidas;
import com.example.caixakioske.Categorias;
import com.example.caixakioske.Login;
import com.example.caixakioske.Modelos.GavetaProduto;
import com.example.caixakioske.Modelos.Ordem;
import com.example.caixakioske.Modelos.Pedido;
import com.example.caixakioske.Modelos.Produto;
import com.example.caixakioske.Produtos;
import com.example.caixakioske.R;
import com.example.caixakioske.TelasCadastros.CadastroPedido;
import com.example.caixakioske.TelasCadastros.EditarProduto;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Date;

public class BebidasPedido extends AppCompatActivity {

    private FirebaseAuth mAuth;

    FirebaseUser usuario;
    FirebaseRecyclerAdapter adapter;
    private DatabaseReference reff;

    String email;
    String uid;
    String caminho;
    ArrayList<Ordem> ordems = new ArrayList<>();
    int quantidade;
    Produto produto;
    String opcional;


    RecyclerView rvBebidasPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas_pedido);

        // Instancia FireBase Auth
        mAuth = FirebaseAuth.getInstance();

        // Pega Intent
        Intent intent = getIntent();

        // Verifica o Caminho
        if(intent.getStringExtra("caminho") != null) {
            caminho = getIntent().getStringExtra("caminho");
        }

        // Pega Usuario
        usuario = mAuth.getCurrentUser();

        if(usuario == null) {
            Toast.makeText(this, "Necessário Logar", Toast.LENGTH_SHORT).show();
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        }

        email = usuario.getEmail();
        uid = usuario.getUid();

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

        rvBebidasPedido = findViewById(R.id.rvBebidasPedido);

        rvBebidasPedido.addOnItemTouchListener(new ListenerGavetas(this, rvBebidasPedido, new ListenerGavetas.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                produto = getItemPosition(position);

                Ordem ordem = new Ordem(produto, 1, produto.getPreco());
                ordem.setOpcional("");
                Toast.makeText(BebidasPedido.this, "Adicionado 1 "+produto.getNome(), Toast.LENGTH_SHORT).show();
                adicionarProdutoPedido(ordem);

            }

            @Override
            public void onLongClick(View view, int position) {

                produto = getItemPosition(position);


                NumberPicker numberPicker = new NumberPicker(BebidasPedido.this);
                numberPicker.setMinValue(0);
                numberPicker.setMaxValue(20);

                NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                       quantidade  = newVal;
                    }
                };

                numberPicker.setOnValueChangedListener(onValueChangeListener);

                final AlertDialog.Builder builder = new AlertDialog.Builder(BebidasPedido.this).setView(numberPicker);

                builder.setTitle("Quantidade");
                builder.setPositiveButton(R.string.Adicionar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Ordem ordem = new Ordem(produto, quantidade, produto.getPreco());
                        if(opcional == null) {
                            ordem.setOpcional("Normal");
                            Log.d("ALCM", "Opcional sem Opcional"+opcional);
                        } else {
                            Log.d("ALCM", "Opcional: "+opcional);
                            ordem.setOpcional(opcional);
                        }

                        if(quantidade > 0) {
                            adicionarProdutoPedido(ordem);
                        } else {
                            Toast.makeText(BebidasPedido.this, "Adicione ao menos 1 produto",
                                    Toast.LENGTH_SHORT).show();
                        }



                    }
                });

                builder.setNeutralButton(R.string.Opcional, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BebidasPedido.this);
                        builder.setTitle(R.string.Opcional);

                        final EditText input = new EditText(BebidasPedido.this);

                        builder.setView(input);

                        // Set up the buttons
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                opcional = input.getText().toString();
                                Ordem ordem = new Ordem(produto, quantidade, produto.getPreco());
                                ordem.setOpcional(opcional);
                                adicionarProdutoPedido(ordem);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder.show();

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
        }));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvBebidasPedido.setLayoutManager(layoutManager);

        rvBebidasPedido.setAdapter(adapter);
        adapter.startListening();

    }

    private void adicionarProdutoPedido(Ordem ordem) {

        Log.d("ALCM", "Adicionando ordem: "+ordem.getProduto().getNome());

        // Percorre todas as ordems já feitas
        if(ordems.isEmpty()) {
            Log.d("ALCM", "Iniciando ordem: "+ordem.getProduto().getNome());
            ordems.add(ordem);
        } else {
            for (Ordem aux : ordems) {

                if(aux.getOpcional() == null) {
                    if(aux == null) {
                        Log.d("ALCM", "Uma ordem NULL foi inserida");
                    } else {
                        Log.d("ALCM", "Opcional dado como NULL");
                    }

                } else {
                    if(ordem.getOpcional() != null
                            && aux.getOpcional() != null
                            && ordem.getOpcional().equals(aux.getOpcional())){

                        // Caso a nova ordem for de um produto já inserido...
                        if(ordem.getProduto().getNome().equals(aux.getProduto().getNome())) {

                            // adiciona a quantidade da nova ordem no valor de quantidade da ordem já existente
                            Log.d("ALCM", "Achou ordem semelhante: "+aux.getProduto().getNome());
                            aux.setQuantidade(aux.getQuantidade() + ordem.getQuantidade());
                            break;
                        } else {
                            // Senão, cadastra essa nova ordem
                            Log.d("ALCM", "Cadastrou nova ordem: "+ordem.getProduto().getNome());
                            ordems.add(ordem);
                            break;
                        }
                    } else {
                        Log.d("ALCM", "Opcional: "+ordem.getOpcional());
                        Log.d("ALCM", "Opcional anterior: "+aux.getOpcional());
                    }
                }

            }
        }


    }

    private void verPedido() {

        if(ordems.isEmpty()) {
            Toast.makeText(this, "Pedido Vazio", Toast.LENGTH_SHORT).show();
        } else {

            reff = FirebaseDatabase.getInstance().getReference().child("pedidos");
            String key = reff.push().getKey();

            Pedido pedido = new Pedido(key, uid, new Date(), ordems);


            // Adiciona Pedido no DB
            FirebaseDAO dao = new FirebaseDAO();
            assert key != null;
            dao.create("pedidos", key, pedido);

            Toast.makeText(this, "Pedido Aberto", Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(this, CadastroPedido.class);
            intent.putExtra("caminho", "produtosPedido");
            intent.putExtra("key", key);
            startActivity(intent);
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_produto_pedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_ver_pedido) {
            verPedido();
        } else if(item.getItemId() == R.id.menu_voltar_pedido) {
            Intent intent = new Intent(this, Categorias.class);
            intent.putExtra("camingo", "cadastroMesa");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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

}
