package br.com.stanev.cookbook.view;

import br.com.stanev.cookbook.domain.Receita;

public class EditReceitaView {
    private Receita receita;

    public EditReceitaView(Receita receita) {
        this.receita = new Receita(receita);
    }

    public Receita edit() {
        boolean confirm = false;
        if (confirm) {
            return receita;
        } else {
            return null;
        }
    }
}
