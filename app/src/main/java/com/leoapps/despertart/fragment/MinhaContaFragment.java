package com.leoapps.despertart.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.irozon.sneaker.Sneaker;
import com.leoapps.despertart.R;
import com.vicmikhailau.maskededittext.MaskedEditText;

import java.util.HashMap;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressPie;

/**
 * A simple {@link Fragment} subclass.
 */
public class MinhaContaFragment extends Fragment {

    private EditText editText_MinhaConta_Nome, editText_MinhaConta_Estado, editText_MinhaConta_Cidade, editText_MinhaConta_Bairro,
            editText_MinhaConta_Rua, editText_MinhaConta_NumeroEndereco, editText_MinhaConta_ComplementoEndereco;

    private MaskedEditText maskedEditText_MinhaConta_Cpf, maskedEditText_MinhaConta_NumeroContato, maskedEditText_MinhaConta_Cep;

    private CardView cardView_MinhaConta_Salvar;

    private FirebaseFirestore firestore;

    public MinhaContaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_minha_conta, container, false);

        firestore = FirebaseFirestore.getInstance();

        //EditText
        editText_MinhaConta_Nome = rootView.findViewById(R.id.editText_MinhaConta_Nome);
        editText_MinhaConta_Estado = rootView.findViewById(R.id.editText_MinhaConta_Estado);
        editText_MinhaConta_Cidade = rootView.findViewById(R.id.editText_MinhaConta_Cidade);
        editText_MinhaConta_Bairro = rootView.findViewById(R.id.editText_MinhaConta_Bairro);
        editText_MinhaConta_Rua = rootView.findViewById(R.id.editText_MinhaConta_Rua);
        editText_MinhaConta_NumeroEndereco = rootView.findViewById(R.id.editText_MinhaConta_NumeroEndereco);
        editText_MinhaConta_ComplementoEndereco = rootView.findViewById(R.id.editText_MinhaConta_ComplementoEndereco);

        //MaskedEditText
        maskedEditText_MinhaConta_Cpf = rootView.findViewById(R.id.maskedEditText_MinhaConta_Cpf);
        maskedEditText_MinhaConta_NumeroContato = rootView.findViewById(R.id.maskedEditText_MinhaConta_NumeroContato);
        maskedEditText_MinhaConta_Cep = rootView.findViewById(R.id.maskedEditText_MinhaConta_Cep);

        //CardView
        cardView_MinhaConta_Salvar = rootView.findViewById(R.id.cardView_MinhaConta_Salvar);

        recuperarDadosUsuario();

        cardView_MinhaConta_Salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tratamentoDados();

            }
        });

        return rootView;
    }


    //------------------------------------------ RECUPERAR DADOS FIRESTORE -----------------------------------------
    private void recuperarDadosUsuario(){

        final ACProgressPie dialog = new ACProgressPie.Builder(getContext())
                .ringColor(Color.parseColor("#3d0044"))
                .pieColor(Color.parseColor("#6a2c70"))
                .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                .speed(70)
                .build();
        dialog.show();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firestore.collection("usuarios").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                dialog.dismiss();

                if (documentSnapshot.exists()){

                    String nomeCompleto = (String)documentSnapshot.getData().get("nomeCompleto");
                    String cpf = (String)documentSnapshot.getData().get("cpf");
                    String numeroContato = (String)documentSnapshot.getData().get("numeroContato");
                    String cep = (String)documentSnapshot.getData().get("cep");
                    String estado = (String)documentSnapshot.getData().get("estado");
                    String cidade = (String)documentSnapshot.getData().get("cidade");
                    String bairro = (String)documentSnapshot.getData().get("bairro");
                    String rua = (String)documentSnapshot.getData().get("rua");
                    String numeroEndereco = (String)documentSnapshot.getData().get("numeroEndereco");
                    String complemento = (String)documentSnapshot.getData().get("complemento");

                    editText_MinhaConta_Nome.setText(nomeCompleto);
                    maskedEditText_MinhaConta_Cpf.setText(cpf);
                    maskedEditText_MinhaConta_NumeroContato.setText(numeroContato);
                    maskedEditText_MinhaConta_Cep.setText(cep);
                    editText_MinhaConta_Estado.setText(estado);
                    editText_MinhaConta_Cidade.setText(cidade);
                    editText_MinhaConta_Bairro.setText(bairro);
                    editText_MinhaConta_Rua.setText(rua);
                    editText_MinhaConta_NumeroEndereco.setText(numeroEndereco);
                    editText_MinhaConta_ComplementoEndereco.setText(complemento);

                }else {

                    Sneaker.with(getActivity())
                            .setTitle("Ops!!!")
                            .setMessage("Você ainda não cadastrou os seus dados")
                            .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .sneakWarning();

                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                dialog.dismiss();

                Sneaker.with(getActivity())
                        .setTitle("Erro!!!")
                        .setMessage("Falha ao recuperar dados")
                        .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                        .sneakError();

            }
        });
    }


    //------------------------------------------ TRATAMENTO DOS DADOS -----------------------------------------
    private void tratamentoDados(){

        String nomeCompleto = editText_MinhaConta_Nome.getText().toString();
        String cpf = maskedEditText_MinhaConta_Cpf.getText().toString();
        String numeroContato = maskedEditText_MinhaConta_NumeroContato.getText().toString();
        String cep = maskedEditText_MinhaConta_Cep.getText().toString();
        String estado = editText_MinhaConta_Estado.getText().toString();
        String cidade = editText_MinhaConta_Cidade.getText().toString();
        String bairro = editText_MinhaConta_Bairro.getText().toString();
        String rua = editText_MinhaConta_Rua.getText().toString();
        String numeroEndereco = editText_MinhaConta_NumeroEndereco.getText().toString();
        String complemento = editText_MinhaConta_ComplementoEndereco.getText().toString();

        if (nomeCompleto.trim().isEmpty() || cpf.trim().isEmpty() || numeroContato.trim().isEmpty() ||
        cep.trim().isEmpty() || estado.trim().isEmpty() || cidade.trim().isEmpty() || bairro.trim().isEmpty() ||
        rua.trim().isEmpty() || numeroEndereco.trim().isEmpty()){

            Sneaker.with(getActivity())
                    .setTitle("Erro!!!")
                    .setMessage("Preencha todos o campos")
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .sneakError();

        }else if (cpf.length() < 14 || numeroContato.length() < 14 || cep.length() < 9){

            Sneaker.with(getActivity())
                    .setTitle("Ops!!!")
                    .setMessage("Digite os campos CPF, NÚMERO CONTATO, CEP, corretamente")
                    .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .sneakWarning();

        }else {

            if (complemento.trim().isEmpty()){

                salvarDadosUsuarioFirebase(nomeCompleto, cpf, numeroContato, cep, estado, cidade, bairro, rua, numeroEndereco, "Sem Complemento");

            }else {

                salvarDadosUsuarioFirebase(nomeCompleto, cpf, numeroContato, cep, estado, cidade, bairro, rua, numeroEndereco, complemento);

            }

        }

    }



    private void salvarDadosUsuarioFirebase(String nomeCompleto, String cpf, String numeroContato, String cep, String estado, String cidade, String bairro, String rua, String numeroEndereco, String complemento){

        final ACProgressPie dialog = new ACProgressPie.Builder(getContext())
                .ringColor(Color.parseColor("#3d0044"))
                .pieColor(Color.parseColor("#6a2c70"))
                .updateType(ACProgressConstant.PIE_AUTO_UPDATE)
                .speed(70)
                .build();
        dialog.show();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        HashMap<String, Object> dadosUsuarios = new HashMap<>();

        dadosUsuarios.put("nomeCompleto", nomeCompleto);
        dadosUsuarios.put("cpf", cpf);
        dadosUsuarios.put("numeroContato", numeroContato);
        dadosUsuarios.put("cep", cep);
        dadosUsuarios.put("estado", estado);
        dadosUsuarios.put("cidade", cidade);
        dadosUsuarios.put("bairro", bairro);
        dadosUsuarios.put("rua", rua);
        dadosUsuarios.put("numeroEndereco", numeroEndereco);
        dadosUsuarios.put("complemento", complemento);

        DocumentReference reference = firestore.collection("usuarios").document(uid);

        reference.set(dadosUsuarios).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                dialog.dismiss();

                if (task.isSuccessful()){

                    Sneaker.with(getActivity())
                            .setTitle("Sucesso!!!")
                            .setMessage("Dados salvos com sucesso")
                            .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .sneakSuccess();

                    recuperarDadosUsuario();

                }else {

                    Sneaker.with(getActivity())
                            .setTitle("Erro!!!")
                            .setMessage("Erro ao salvar dados Porfavor tente mais tarde")
                            .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .sneakError();

                }
            }
        });

    }
}
