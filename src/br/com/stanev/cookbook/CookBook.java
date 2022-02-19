package br.com.stanev.cookbook;

import br.com.stanev.cookbook.controller.Catalogo;
import br.com.stanev.cookbook.domain.Receita;
import br.com.stanev.cookbook.enums.Categoria;
import br.com.stanev.cookbook.view.CatalogoView;

public class CookBook {
    public static void main(String[] args) {
        Catalogo catalogo = new Catalogo();
        CatalogoView view = new CatalogoView(catalogo);
        view.view();
    }
}
