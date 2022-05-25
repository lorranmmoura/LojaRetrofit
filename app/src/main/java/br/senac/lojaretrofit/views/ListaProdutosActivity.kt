package br.senac.lojaretrofit.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.senac.lojaretrofit.databinding.ActivityListaProdutosBinding
import br.senac.lojaretrofit.databinding.CardItemBinding
import br.senac.lojaretrofit.model.Produto
import br.senac.lojaretrofit.services.API
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListaProdutosActivity : AppCompatActivity() {
    lateinit var binding: ActivityListaProdutosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onResume(){
        super.onResume()

        listarProdutos()
    }


    fun listarProdutos() {

        val callback = object : Callback<List<Produto>> {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if (response.isSuccessful) {
                    val listaProdutos = response.body()
                    atualizarTela(listaProdutos)
                } else {
                    Snackbar.make(
                        binding.container,
                        "Servidor indisponivel",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                Snackbar.make(
                    binding.container,
                    "Nao foi possivel se conectar",
                    Snackbar.LENGTH_LONG
                )
                    .show()

            }



        }

        API.produto.listar().enqueue(callback)

    }

    fun atualizarTela(listaProdutos: List<Produto>?) {
        binding.container.removeAllViews()

        listaProdutos?.forEach {

            val cardBinding = CardItemBinding.inflate(layoutInflater)

            cardBinding.textNome.text = it.nomeProduto
            cardBinding.textPreco.text = it.precProduto.toString()

            Picasso.get().load("https://oficinacordova.azurewebsites.net/android/rest/produto/image/${it.idProduto}")
                .into(cardBinding.imagem)

            binding.container.addView(cardBinding.root)

        }
    }
}